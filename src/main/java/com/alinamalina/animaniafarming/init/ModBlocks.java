package com.alinamalina.animaniafarming.init;

import java.util.ArrayList;
import java.util.List;

import com.alinamalina.animaniafarming.Blocks.BlockBase;
import com.alinamalina.animaniafarming.Blocks.FluidBlockBase;
import com.alinamalina.animaniafarming.Blocks.RubyBlock;
import com.alinamalina.animaniafarming.Machines.BiogasFermenter.BlockBiogasFermenter;

import com.alinamalina.animaniafarming.Machines.BiogasGenerator.BlockBiogasGenerator;
import com.alinamalina.animaniafarming.Machines.CompostGenerator.BlockCompostGenerator;
import com.alinamalina.animaniafarming.Machines.Composter.BlockComposter;
import com.alinamalina.animaniafarming.Machines.ManureCollector.BlockManureCollector;
import com.alinamalina.animaniafarming.Machines.BiogasGenerator.BlockBiogasGenerator;
import com.alinamalina.animaniafarming.Machines.Sinterer.BlockSinteringFurnace;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public class ModBlocks 
{
		
		public static final List<Block> BLOCKS = new ArrayList<Block>();

	//Blocks
	public static final Block DIRTY_BLOCK = new BlockBase("dirty_block", Material.IRON);
	public static final Block RUBY_BLOCK = new RubyBlock("ruby_block", Material.IRON);
	
	//Machines
	public static final Block SINTERING_FURNACE = new BlockSinteringFurnace("sintering_furnace", Material.IRON);
	public static final Block BIOGAS_GENERATOR = new BlockBiogasGenerator("biogas_generator", Material.IRON);
	public static final Block COMPOST_GENERATOR = new BlockCompostGenerator("compost_generator", Material.IRON);
	public static final Block MANURE_COLLECTOR = new BlockManureCollector("manure_collector", Material.IRON);
	public static final Block BIOGAS_FERMENTER = new BlockBiogasFermenter("biogas_fermenter", Material.IRON);
	public static final Block COMPOSTER = new BlockComposter("composter");
	
	//Fluids
	public static final Block MANURE = new FluidBlockBase("manure", ModFluids.MANURE, Material.WATER);
	public static final Block BIOGAS = new FluidBlockBase("biogas", ModFluids.BIOGAS, Material.WATER);
	
}
