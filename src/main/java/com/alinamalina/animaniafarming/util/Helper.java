package com.alinamalina.animaniafarming.util;

import com.alinamalina.animaniafarming.Main;
import com.alinamalina.animaniafarming.network.NetworkHandler;
import com.alinamalina.animaniafarming.network.TileEntitySyncPacket;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class Helper 
{
	public static boolean hasFluid(ItemStack stack, Fluid fluid)
	{
		return FluidUtil.getFluidContained(stack) != null && FluidUtil.getFluidContained(stack).amount >= 1000 && FluidUtil.getFluidContained(stack).getFluid() == fluid;
	}
	public static void sendTileEntityUpdate(TileEntity tile)
	{
		if (tile != null && tile.getWorld() != null && !tile.getWorld().isRemote)
		{
			NBTTagCompound compound = new NBTTagCompound();
			compound = tile.writeToNBT(compound);

			NBTTagCompound data = new NBTTagCompound();
			data.setTag("data", compound);
			data.setInteger("x", tile.getPos().getX());
			data.setInteger("y", tile.getPos().getY());
			data.setInteger("z", tile.getPos().getZ());
			Main.network.sendToAllAround(new TileEntitySyncPacket(data), new NetworkRegistry.TargetPoint(tile.getWorld().provider.getDimension(), tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), 64));
		}

	}
	
	public static void doEnergyInteraction(TileEntity tileFrom, TileEntity tileTo, EnumFacing sideTo, int maxTransfer) {
		if (maxTransfer > 0) {
			EnumFacing opp = sideTo == null ? null : sideTo.getOpposite();
			IEnergyStorage handlerFrom =  tileFrom.getCapability(CapabilityEnergy.ENERGY, sideTo);
			IEnergyStorage handlerTo = tileTo.getCapability(CapabilityEnergy.ENERGY, opp);
			if (handlerFrom != null && handlerTo != null) {
				int drain = handlerFrom.extractEnergy(maxTransfer, true);
				if (drain > 0) {
					int filled = handlerTo.receiveEnergy(drain, false);
					handlerFrom.extractEnergy(filled, false);
					return;
				}
			}
		}
	}
	
	public static void doFluidInteraction(TileEntity tileFrom, TileEntity tileTo, EnumFacing sideTo, int maxTransfer) {
        if (maxTransfer > 0) {
            if (tileFrom.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo) && tileTo.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo.getOpposite())) {
                IFluidHandler handlerFrom = tileFrom.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo);
                IFluidHandler handlerTo = tileTo.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, sideTo.getOpposite());
                FluidStack drain = handlerFrom.drain(maxTransfer, false);
                if (drain != null) {
                    int filled = handlerTo.fill(drain.copy(), true);
                    handlerFrom.drain(filled, true);
                }
            }
        }
    }

}

