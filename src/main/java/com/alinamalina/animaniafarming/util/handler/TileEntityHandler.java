package com.alinamalina.animaniafarming.util.handler;


import com.alinamalina.animaniafarming.Machines.BiogasFermenter.TileEntityBiogasFermenter;
import com.alinamalina.animaniafarming.Machines.BiogasGenerator.TileEntityBiogasGenerator;
import com.alinamalina.animaniafarming.Machines.CompostGenerator.TileEntityCompostGenerator;
import com.alinamalina.animaniafarming.Machines.Composter.TileEntityComposter;
import com.alinamalina.animaniafarming.Machines.ManureCollector.TileEntityManureCollector;
import com.alinamalina.animaniafarming.Machines.Sinterer.TileEntitySinteringFurnace;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler 
{
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntitySinteringFurnace.class, "sintering_furnace");
		GameRegistry.registerTileEntity(TileEntityBiogasGenerator.class, "biogas_generator");
		GameRegistry.registerTileEntity(TileEntityCompostGenerator.class, "compost_generator");
		GameRegistry.registerTileEntity(TileEntityBiogasFermenter.class, "biogas_fermenter");
		GameRegistry.registerTileEntity(TileEntityManureCollector.class, "manure_collector");
		GameRegistry.registerTileEntity(TileEntityComposter.class, "composter");
	}
}
