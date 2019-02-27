package com.alinamalina.animaniafarming.Machines;

import net.minecraft.util.ITickable;

import javax.annotation.Nullable;

import com.alinamalina.animaniafarming.util.Helper;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityShareEnergy extends TileEntity implements ITickable
{
	private boolean shareEnergy = this instanceof ISharingEnergyProvider;
	
	protected int ticksElapsed;
	protected TileEntity[] tilesAround = new TileEntity[6];
	
	@Override
    public final void update()
	{
        this.updateEntity();
    }
	
	public void updateEntity()
	{
        this.ticksElapsed++;

        if(!this.world.isRemote){
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
                            TileEntity tile = this.tilesAround[side.ordinal()];
                            if(tile != null && provider.canShareTo(tile)){
                                doEnergyInteraction(this, tile, side, amount);
                                Helper.sendTileEntityUpdate(this);
                            }
                        }
                    }
                }
            }
        }}
	
	public static void doEnergyInteraction(TileEntity tileFrom, TileEntity tileTo, EnumFacing sideTo, int maxTransfer) {
        if (maxTransfer > 0) {
            EnumFacing opp = sideTo == null ? null : sideTo.getOpposite();
                IEnergyStorage handlerFrom = tileFrom.getCapability(CapabilityEnergy.ENERGY, sideTo);
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
	
	public IEnergyStorage getEnergyStorage(EnumFacing facing){
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

	
}
