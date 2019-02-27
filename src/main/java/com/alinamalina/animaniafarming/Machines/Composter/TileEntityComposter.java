package com.alinamalina.animaniafarming.Machines.Composter;

import com.alinamalina.animaniafarming.Energy.CustomEnergyStorage;
import com.alinamalina.animaniafarming.Machines.ISharingFluidHandler;
import com.alinamalina.animaniafarming.Machines.TileEntityBase;
import com.alinamalina.animaniafarming.init.ModFluids;
import com.alinamalina.animaniafarming.init.ModItems;
import com.alinamalina.animaniafarming.util.Helper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityComposter extends TileEntityBase 

{
	private static final int PRODUCE = 100;
	public ItemStackHandler handler = new ItemStackHandler(2);
	public FluidTank tank = new FluidTank(ModFluids.MANURE, 0, 1000);
	private String customName;
	public int burnTime;
	public int maxBurnTime = 100;

	@Override
	public void updateEntity() 
	{
		ItemStack input = handler.getStackInSlot(0);
		Item compost = handler.getStackInSlot(1).getItem();

		super.updateEntity();
		if(!input.isEmpty() && isItemFuel(input))
		{		
			if(this.tank.getFluidAmount() >=  getFuelValue(input))
			{
				if(handler.getStackInSlot(1).isEmpty() || compost == ModItems.COMPOST)
				{
					burnTime++;
					if(burnTime == maxBurnTime)
					{
						this.tank.drain(getFuelValue(input), true);
						this.handler.getStackInSlot(0).shrink(1);
						this.handler.insertItem(1, new ItemStack(ModItems.COMPOST, 1), false);
						burnTime = 0;
						markDirty();
					}
				}
			}
		}
	}

	public void setCustomName(String customName) 
	{
		this.customName = customName;
	}

	

	public boolean isItemFuel(ItemStack stack) 
	{
		return getFuelValue(stack) > 0;
	}

	private int getFuelValue(ItemStack stack) 
	{
		if(stack.getItem() == Items.WHEAT) return 50;
		if(stack.getItem() == Items.POTATO) return 50;
		if(stack.getItem() == Items.CARROT) return 50;
		if(stack.getItem() == Items.WHEAT_SEEDS) return 50;
		if(stack.getItem() == Items.POISONOUS_POTATO) return 50;
		if(stack.getItem() == Items.APPLE) return 50;
		if(stack.getItem() == Items.MELON) return 50;
		if(stack.getItem() == Items.MELON_SEEDS) return 50;
		if(stack.getItem() == Items.PUMPKIN_SEEDS) return 50;
		if(stack.getItem() == Item.getItemFromBlock(Blocks.BROWN_MUSHROOM)) return 50;
		if(stack.getItem() == Item.getItemFromBlock(Blocks.RED_MUSHROOM)) return 50;
		if(stack.getItem() == Item.getItemFromBlock(Blocks.SAPLING)) return 50;
		if(stack.getItem() == Item.getItemFromBlock(Blocks.CACTUS)) return 50;
		if(stack.getItem() == Item.getItemFromBlock(Blocks.REEDS)) return 50;
		if(stack.getItem() == Item.getItemFromBlock(Blocks.VINE)) return 50;
		else return 0;
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
		return new TextComponentTranslation("Composter");
	}


	public int getField(int id)
	{
		
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

	@Override
	public FluidTank getFluidHandler(EnumFacing facing) {
		return this.tank;
	}
}
