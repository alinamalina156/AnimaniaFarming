package com.alinamalina.animaniafarming.Machines.CompostGenerator;

import javax.annotation.Nonnull;

import com.alinamalina.animaniafarming.Energy.CustomEnergyStorage;
import com.alinamalina.animaniafarming.Machines.ISharingEnergyProvider;
import com.alinamalina.animaniafarming.Machines.TileEntityBase;
import com.alinamalina.animaniafarming.init.ModItems;

import com.alinamalina.animaniafarming.util.Helper;

import cjminecraft.core.energy.EnergyUnit;
import cjminecraft.core.energy.EnergyUtils;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCompostGenerator extends TileEntityBase implements ISharingEnergyProvider
{
	private static final int PRODUCE = 50;
	public ItemStackHandler handler = new ItemStackHandler(1);
	private CustomEnergyStorage storage = new CustomEnergyStorage(5000, 0, 50);
	private String customName;
	public int maxBurnTime = 100;
	public int burnTime;
	
	@Override
	public void updateEntity() 
	{
		super.updateEntity();
		
		if(this.isBurning())
		{
			BlockCompostGenerator.setState(true, world, pos);
		}
		
		if(!handler.getStackInSlot(0).isEmpty() && isItemFuel(handler.getStackInSlot(0)) && this.storage.getEnergyStored() < this.storage.getMaxEnergyStored())
			{		
					burnTime++;
					if(burnTime == maxBurnTime)
					{
						this.storage.addEnergyRaw(PRODUCE);
						handler.getStackInSlot(0).shrink(1);
						burnTime = 0;
						markDirty();
					}
			}
		else BlockCompostGenerator.setState(false, world, pos);
	}
	
	
	public boolean isBurning() 
	{
		return this.burnTime > 0;
	}
	
	private boolean isItemFuel(ItemStack stack) 
	{
		return getFuelValue(stack) > 0;
	}
	
	private int getFuelValue(ItemStack stack) 
	{
		if(stack.getItem() == ModItems.COMPOST) return 100;
		else return 0;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if(capability == CapabilityEnergy.ENERGY) return (T)this.storage;
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T)this.handler;
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityEnergy.ENERGY) return true;
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("CookTime", this.burnTime);
		this.storage.writeToNBT(compound);
		compound.setString("Name", getDisplayName().toString());
		this.storage.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.burnTime = compound.getInteger("CookTime");
		this.storage.readFromNBT(compound);
		this.customName = compound.getString("Name");
		this.storage.readFromNBT(compound);
	}
	
	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation("container.compost_generator");
	}
	
	public int getEnergyStored()
	{
		return this.storage.getEnergyStored();
	}
	
	public int getMaxEnergyStored()
	{
		return this.storage.getMaxEnergyStored();
	}
	
	public int getField(int id)
	{
		switch(id)
		{
		case 0:
			return this.storage.getEnergyStored();
		case 1:
			return this.storage.getMaxEnergyStored();
		case 2:
			return this.burnTime;
		case 3:
			return this.maxBurnTime;
		default:
			return 0;
		}
	}
	
	public void setField(int id, int value)
	{
	}
	
	@Override
	public int getEnergyToSplitShare() {
		return this.storage.getEnergyStored();
	}

	@Override
	public boolean doesShareEnergy() {
		return true;
	}

	@Override
	public EnumFacing[] getEnergyShareSides() {
		return EnumFacing.values();
	}

	@Override
	public boolean canShareTo(TileEntity tile) {
		return true;
	}

	@Override
	public IEnergyStorage getEnergyStorage(EnumFacing facing) {
		return this.storage;
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}	
}