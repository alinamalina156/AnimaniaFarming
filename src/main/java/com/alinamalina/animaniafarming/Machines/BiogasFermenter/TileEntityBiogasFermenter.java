package com.alinamalina.animaniafarming.Machines.BiogasFermenter;

import javax.annotation.Nonnull;

import com.alinamalina.animaniafarming.Energy.CustomEnergyStorage;
import com.alinamalina.animaniafarming.Machines.ISharingEnergyProvider;
import com.alinamalina.animaniafarming.Machines.ISharingFluidHandler;
import com.alinamalina.animaniafarming.Machines.TileEntityBase;
import com.alinamalina.animaniafarming.Machines.BiogasGenerator.BlockBiogasGenerator;
import com.alinamalina.animaniafarming.init.ModFluids;
import com.alinamalina.animaniafarming.init.ModItems;

import com.alinamalina.animaniafarming.util.Helper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBiogasFermenter extends TileEntityBase implements ISharingFluidHandler
{
	private static final int PRODUCE = 200;
	public ItemStackHandler handler = new ItemStackHandler(1);
	public FluidTank biogasTank = new FluidTank(ModFluids.BIOGAS, 0, 1000);
	public CustomEnergyStorage storage = new CustomEnergyStorage(1000, 100, 0);
	//public FluidTank waterTank = new FluidTank(1000);
	private String customName;
	public int maxBurnTime = 100;
	public int burnTime;
	
	@Override
	public void updateEntity() 
	{
		if(this.isBurning())
		{
			BlockBiogasFermenter.setState(true, world, pos);
		}
		
		ItemStack stack = handler.getStackInSlot(0);

		super.updateEntity();
		if(!handler.getStackInSlot(0).isEmpty() && isItemFuel(handler.getStackInSlot(0)))
		{		
			if(this.biogasTank.getFluidAmount() + getFuelValue(stack) <= this.biogasTank.getCapacity() && this.storage.getEnergyStored() >= 100)
			{
				burnTime++;
				if(burnTime == maxBurnTime)
				{
					this.biogasTank.fill(new FluidStack(ModFluids.BIOGAS, getFuelValue(stack)), true);
					this.storage.extractEnergyInternal(50, false);
					//this.waterTank.drain(100, true);
					this.handler.getStackInSlot(0).shrink(1);
					burnTime = 0;
					markDirty();
				}
			}
		}
		
		else BlockBiogasFermenter.setState(false, world, pos);
	}
	
	public boolean isBurning() 
	{
		return this.burnTime > 0;
	}
	
	public void setCustomName(String customName) 
	{
		this.customName = customName;
	}
	
	@Override
	public void markDirty() 
	{
		super.markDirty();

	}
	
	private boolean isItemFuel(ItemStack stack) 
	{
		return getFuelValue(stack) > 0;
	}
	
	private int getFuelValue(ItemStack stack) 
	{
		if(stack.getItem() == ModItems.COMPOST) return 50;
		else return 0;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		//if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return (T) this.waterTank;	
		if(capability == CapabilityEnergy.ENERGY) return (T)this.storage;
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return (T) this.biogasTank;	
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T)this.handler;
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityEnergy.ENERGY) return true;
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		NBTTagCompound tag = super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		tag.setInteger("burnTime", this.burnTime);
		this.storage.writeToNBT(compound);
		this.biogasTank.writeToNBT(compound);
		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.burnTime = compound.getInteger("burnTime");
		this.storage.readFromNBT(compound);
		this.biogasTank.readFromNBT(compound);
	}

	
	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation("Biogas Fermenter");
	}
	

	public int getField(int id)
	{
		switch(id)
		{
		case 1:
			return this.storage.getEnergyStored();
		case 2:
			return this.storage.getMaxEnergyStored();
		case 3:
			return this.biogasTank.getFluidAmount();
		case 4:
			return this.biogasTank.getCapacity();
		case 5:
			return this.burnTime;
		case 6:
			return this.maxBurnTime;
		default:
			return 0;
		}
	}
	
	public void setField(int id, int value)
	{
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
    public FluidTank getFluidHandler(EnumFacing facing) {
        return this.biogasTank;
    }

    @Override
    public int getMaxFluidAmountToSplitShare() {
        return this.biogasTank.getFluidAmount();
    }

    @Override
    public boolean doesShareFluid() {
        return true;
    }

    @Override
    public EnumFacing[] getFluidShareSides() {
        return EnumFacing.values();
    }
    
	private IBlockState getState() 
	{
		return world.getBlockState(pos);
	}
}