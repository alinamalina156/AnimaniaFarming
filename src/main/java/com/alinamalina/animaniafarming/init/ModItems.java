package com.alinamalina.animaniafarming.init;

import java.util.ArrayList;
import java.util.List;

import com.alinamalina.animaniafarming.items.ItemBase;
import com.alinamalina.animaniafarming.items.tools.ToolAxe;
import com.alinamalina.animaniafarming.items.tools.ToolHoe;
import com.alinamalina.animaniafarming.items.tools.ToolPickaxe;
import com.alinamalina.animaniafarming.items.tools.ToolSpade;
import com.alinamalina.animaniafarming.items.tools.ToolSword;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems 
{
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Materials
	public static final ToolMaterial MATERIAL_RUBY = EnumHelper.addToolMaterial("matirial_ruby", 3, 250, 8.0F, 3.0F, 10);
	
	//Items
	public static final Item RUBY = new ItemBase("ruby");
	public static final Item OBSIDIAN_INGOT = new ItemBase("obsidian_ingot");
	public static final Item COMPOST = new ItemBase("compost");
	public static final Item STRAW_CLAY_BRICK = new ItemBase("straw_clay_brick");
	public static final Item STRAW_CLAY = new ItemBase("straw_clay");
	public static final Item CRUDE_CARBIDE = new ItemBase("crude_carbide");
	public static final Item IRON_CARBIDE_INGOT = new ItemBase("iron_carbide_ingot");
	public static final Item HEATING_COMPONENT = new ItemBase("heating_component");
	public static final Item SILICON_STEEL_INGOT = new ItemBase("silicon_steel_ingot");
	
	//Tools
	public static final ItemSword RUBY_SWORD = new ToolSword("ruby_sword", MATERIAL_RUBY);
	public static final ItemSpade RUBY_SHOVEL  = new ToolSpade("ruby_shovel", MATERIAL_RUBY);
	public static final ItemPickaxe RUBY_PICKAXE  = new ToolPickaxe("ruby_pickaxe", MATERIAL_RUBY);
	public static final ItemAxe RUBY_AXE  = new ToolAxe("ruby_Axe", MATERIAL_RUBY);
	public static final ItemHoe RUBY_HOE  = new ToolHoe("ruby_Hoe", MATERIAL_RUBY);

}
