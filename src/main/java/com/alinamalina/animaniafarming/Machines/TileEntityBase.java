package com.alinamalina.animaniafarming.Machines;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.alinamalina.animaniafarming.Machines.BiogasGenerator.TileEntityBiogasGenerator;
import com.alinamalina.animaniafarming.util.Helper;
import com.alinamalina.animaniafarming.util.PacketDispatcher;

import cjminecraft.core.energy.EnergyUnit;
import cjminecraft.core.energy.EnergyUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityBase  extends TileEntity implements ITickable
{
	private boolean shareEnergy = this instanceof ISharingEnergyProvider;
	private boolean shareFluid = this instanceof ISharingFluidHandler;

	protected int ticks;


	@Override
	public final void update()
	{
		this.updateEntity();
	}

	public void updateEntity()
	{
		
		if(shareEnergy){
			ISharingEnergyProvider provider = (ISharingEnergyProvider)this;
			if(provider.doesShareEnergy()){
				int total = provider.getEnergyToSplitShare();
				if(total > 0){
					EnumFacing[] sides = provider.getEnergyShareSides();

					int amount = total/sides.length;
					if(amount <= 0){
						amount = total;
					}

					for(EnumFacing side : sides){
						TileEntity tile = world.getTileEntity(pos.offset(side));
						if(tile != null && provider.canShareTo(tile)){
							Helper.doEnergyInteraction(this, tile, side, amount);
						}
					}
				}
			}
		}
		if(shareFluid){
            ISharingFluidHandler handler = (ISharingFluidHandler)this;
            if(handler.doesShareFluid()){
                int total = handler.getMaxFluidAmountToSplitShare();
                if(total > 0){
                    EnumFacing[] sides = handler.getFluidShareSides();

                    int amount = total/sides.length;
                    if(amount <= 0){
                        amount = total;
                    }

                    for(EnumFacing side : sides){
                        TileEntity tile = world.getTileEntity(pos.offset(side));
                        if(tile != null){
                            Helper.doFluidInteraction(this, tile, side, amount);
                        }
                    }
                }
            }
        }
	}
	
    
    
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing){
		return this.getCapability(capability, facing) != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing){
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			IItemHandler handler = this.getItemHandler(facing);
			if(handler != null){
				return (T)handler;
			}
		}
		else if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			IFluidHandler tank = this.getFluidHandler(facing);
			if(tank != null){
				return (T)tank;
			}
		}
		else if(capability == CapabilityEnergy.ENERGY){
			IEnergyStorage storage = this.getEnergyStorage(facing);
			if(storage != null){
				return (T)storage;
			}
		}
		return super.getCapability(capability, facing);
	}

	public IFluidHandler getFluidHandler(EnumFacing facing){
		return null;
	}

	public IEnergyStorage getEnergyStorage(EnumFacing facing){
		return null;
	}

	public IItemHandler getItemHandler(EnumFacing facing){
		return null;
	}
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return this.writeToNBT(new NBTTagCompound());
	}


	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

}
