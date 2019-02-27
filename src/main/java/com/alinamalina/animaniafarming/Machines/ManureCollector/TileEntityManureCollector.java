package com.alinamalina.animaniafarming.Machines.ManureCollector;

import javax.annotation.Nullable;

import com.alinamalina.animaniafarming.Machines.TileEntityBase;
import com.alinamalina.animaniafarming.init.ModFluids;
import com.alinamalina.animaniafarming.init.ModItems;
import com.alinamalina.animaniafarming.util.Helper;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class TileEntityManureCollector extends TileEntityBase
{

	private String customName;

	public FluidTank tank = new FluidTank(ModFluids.MANURE, 0, 1000);
	public ItemStackHandler handler = new ItemStackHandler(1);
	public int manureUsed;
	public int maxBurnTime = 200;
	public int burnTime;

	public void setCustomName(String customName) 
	{
		this.customName = customName;
	}


	@SideOnly(Side.CLIENT)
	public int getBurningScaled(int j){
		return this.burnTime*j/this.maxBurnTime;
	}

	public boolean isBurning() 
	{
		return this.burnTime > 0;

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		NBTTagCompound tag = super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		tag.setInteger("burnTime", this.burnTime);
		this.tank.writeToNBT(compound);
		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.burnTime = compound.getInteger("burnTime");
		this.tank.readFromNBT(compound);
	}


	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation("Manure Collector");
	}


	@Override
	public void updateEntity() 
	{
		super.updateEntity();

		ItemStack stack = handler.getStackInSlot(0);
		AxisAlignedBB area = getWorkingArea();
		List<EntityAgeable> animals = this.world.getEntitiesWithinAABB(EntityAgeable.class, area);

		if(this.tank.getFluidAmount() + 50 <= 1000)
			burnTime++;
		if (burnTime == maxBurnTime)
		{
			burnTime = 0;
			for (EntityAgeable animal : animals)
			{
				this.tank.fill(new FluidStack(ModFluids.MANURE, 50), true);		
				markDirty();
			}
			
		}

	}


	public AxisAlignedBB getWorkingArea() {
		return new AxisAlignedBB(this.pos.getX() - 8, this.pos.getY() -8 , this.pos.getZ(), this.pos.getX() + 8, this.pos.getY() + 8, this.pos.getZ() + 3);
	}

	@Override
	public void markDirty() 
	{
		super.markDirty();

	}

	private IBlockState getState() 
	{
		return world.getBlockState(pos);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return (T) this.tank;	
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T)this.handler;
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
	}


	public int getField(int id) {
		switch(id) 
		{
		case 1:
			return this.tank.getFluidAmount();
		case 2:
			return this.tank.getCapacity();
		case 3:
			return this.burnTime;
		case 4:
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


}

