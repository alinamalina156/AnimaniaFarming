package com.alinamalina.animaniafarming.Machines.BiogasGenerator;



import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.alinamalina.animaniafarming.Main;
import com.alinamalina.animaniafarming.Energy.CustomEnergyStorage;
import com.alinamalina.animaniafarming.Machines.ISharingEnergyProvider;
import com.alinamalina.animaniafarming.Machines.TileEntityBase;
import com.alinamalina.animaniafarming.Machines.Sinterer.BlockSinteringFurnace;
import com.alinamalina.animaniafarming.init.ModFluids;

import com.alinamalina.animaniafarming.util.Helper;

import cjminecraft.core.energy.EnergyUnit;
import cjminecraft.core.energy.EnergyUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.TileFluidHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBiogasGenerator extends TileEntityBase implements ISharingEnergyProvider

{
	private String customName;



	private static final int PRODUCE = 400;
	int fuelUsed = 100;
	public CustomEnergyStorage storage = new CustomEnergyStorage(10000, 0, 100);
	public FluidTank tank = new FluidTank(ModFluids.BIOGAS, 0, 1000);
	public int maxBurnTime = 100;
	public int burnTime;

	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation("Biogas generator");
	}


	public void setCustomName(String customName) 
	{
		this.customName = customName;
	}


	@SideOnly(Side.CLIENT)
	public int getEnergyScaled(int i)
	{
		return this.storage.getEnergyStored()*i/this.storage.getMaxEnergyStored();
	}


	@SideOnly(Side.CLIENT)
	public int getBurningScaled(int j){
		return this.burnTime*j/this.maxBurnTime;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if(capability == CapabilityEnergy.ENERGY) return (T)this.storage;
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return (T) this.tank;	
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityEnergy.ENERGY) return true;
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
	} 
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		NBTTagCompound tag = super.writeToNBT(compound);
		tag.setInteger("burnTime", this.burnTime);
		this.storage.writeToNBT(compound);
		this.tank.writeToNBT(compound);
		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) 
	{
		super.readFromNBT(compound);
		this.burnTime = compound.getInteger("burnTime");
		this.storage.readFromNBT(compound);
		this.tank.readFromNBT(compound);
	}

	@Override
	public void updateEntity()
	{ 
		if(this.isBurning())
		{
			BlockBiogasGenerator.setState(true, world, pos);
		}

		super.updateEntity();
		
		if(this.tank.getFluidAmount() >= fuelUsed && this.storage.getEnergyStored() + PRODUCE <= this.storage.getMaxEnergyStored())	
		{
			this.burnTime++;
			if(burnTime == maxBurnTime)
			{
				this.tank.drain(fuelUsed, true);
				this.storage.addEnergyRaw(PRODUCE);
				burnTime = 0;
				markDirty();
			}
		}
		else BlockBiogasGenerator.setState(false, world, pos);

		
	}

	public boolean isBurning() 
	{
		return this.burnTime > 0 && this.tank.getFluidAmount() > 0;
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



	public int getField(int id) {
		switch(id) 
		{
		case 1:
			return this.storage.getEnergyStored();
		case 2:
			return this.storage.getMaxEnergyStored();
		case 3:
			return this.tank.getFluidAmount();
		case 4:
			return this.tank.getCapacity();
		case 5:
			return this.burnTime;
		case 6:
			return this.maxBurnTime;
		default:
			return 0;
		}

	}

	public void setField(int id, int value) {
		// TODO Auto-generated method stub
	}

	public int getFieldCount() 
	{
		return 6;
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


}
