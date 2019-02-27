package com.alinamalina.animaniafarming.network;

import com.alinamalina.animaniafarming.Main;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler
{
	

	public static void init()
	{
		
		Main.network = NetworkRegistry.INSTANCE.newSimpleChannel("af");
		Main.network.registerMessage(TileEntitySyncPacketHandler.class, TileEntitySyncPacket.class, 0, Side.CLIENT);
	}
	
	
}
