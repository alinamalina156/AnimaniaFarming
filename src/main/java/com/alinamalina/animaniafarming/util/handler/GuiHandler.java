package com.alinamalina.animaniafarming.util.handler;


import com.alinamalina.animaniafarming.util.Reference;
import com.alinamalina.animaniafarming.Machines.GuiIDs;
import com.alinamalina.animaniafarming.Machines.BiogasFermenter.ContainerBiogasFermenter;
import com.alinamalina.animaniafarming.Machines.BiogasFermenter.TileEntityBiogasFermenter;
import com.alinamalina.animaniafarming.Machines.BiogasFermenter.Slots.GuiBiogasFermenter;
import com.alinamalina.animaniafarming.Machines.BiogasGenerator.ContainerBiogasGenerator;
import com.alinamalina.animaniafarming.Machines.BiogasGenerator.TileEntityBiogasGenerator;
import com.alinamalina.animaniafarming.Machines.CompostGenerator.ContainerCompostGenerator;
import com.alinamalina.animaniafarming.Machines.CompostGenerator.TileEntityCompostGenerator;
import com.alinamalina.animaniafarming.Machines.CompostGenerator.Slots.GuiCompostGenerator;
import com.alinamalina.animaniafarming.Machines.Composter.ContainerComposter;
import com.alinamalina.animaniafarming.Machines.Composter.TileEntityComposter;
import com.alinamalina.animaniafarming.Machines.Composter.Slots.GuiComposter;
import com.alinamalina.animaniafarming.Machines.ManureCollector.ContainerManureCollector;
import com.alinamalina.animaniafarming.Machines.ManureCollector.TileEntityManureCollector;
import com.alinamalina.animaniafarming.Machines.ManureCollector.Slots.GuiManureCollector;
import com.alinamalina.animaniafarming.Machines.BiogasGenerator.ContainerBiogasGenerator;
import com.alinamalina.animaniafarming.Machines.BiogasGenerator.TileEntityBiogasGenerator;
import com.alinamalina.animaniafarming.Machines.BiogasGenerator.Slots.GuiBiogasGenerator;
import com.alinamalina.animaniafarming.Machines.Sinterer.ContainerSinteringFurnace;
import com.alinamalina.animaniafarming.Machines.Sinterer.TileEntitySinteringFurnace;
import com.alinamalina.animaniafarming.Machines.Sinterer.Slots.GuiSinteringFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler


{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == GuiIDs.GUI_BIOGAS_GENERATOR) 
			return new ContainerBiogasGenerator(player.inventory, (TileEntityBiogasGenerator)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == GuiIDs.GUI_SINTERING_FURNACE) 
			return new ContainerSinteringFurnace(player.inventory, (TileEntitySinteringFurnace)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == GuiIDs.GUI_COMPOST_GENERATOR) 
			return new ContainerCompostGenerator(player.inventory, (TileEntityCompostGenerator)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == GuiIDs.GUI_BIOGAS_FERMENTER) 
			return new ContainerBiogasFermenter(player.inventory, (TileEntityBiogasFermenter)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == GuiIDs.GUI_MANURE_COLLECTOR) 
			return new ContainerManureCollector(player.inventory, (TileEntityManureCollector)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == GuiIDs.GUI_COMPOSTER) 
			return new ContainerComposter(player.inventory, (TileEntityComposter)world.getTileEntity(new BlockPos(x,y,z)));
		{
		
		return null;
		}
	}
	

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == GuiIDs.GUI_BIOGAS_GENERATOR) 
			return new GuiBiogasGenerator(player.inventory, (TileEntityBiogasGenerator)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == GuiIDs.GUI_SINTERING_FURNACE) 
			return new GuiSinteringFurnace(player.inventory, (TileEntitySinteringFurnace)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == GuiIDs.GUI_COMPOST_GENERATOR) 
			return new GuiCompostGenerator(player.inventory, (TileEntityCompostGenerator)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == GuiIDs.GUI_BIOGAS_FERMENTER) 
			return new GuiBiogasFermenter(player.inventory, (TileEntityBiogasFermenter)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == GuiIDs.GUI_MANURE_COLLECTOR) 
			return new GuiManureCollector(player.inventory, (TileEntityManureCollector)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == GuiIDs.GUI_COMPOSTER) 
			return new GuiComposter(player.inventory, (TileEntityComposter)world.getTileEntity(new BlockPos(x,y,z)));
		{
		
		return null;
		}
		
	}
	
}



