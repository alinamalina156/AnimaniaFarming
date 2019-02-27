package com.alinamalina.animaniafarming.tabs;

import com.alinamalina.animaniafarming.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AnimaniaFarmingTab extends CreativeTabs 
{
	public AnimaniaFarmingTab(String lable) { super("AnimaniaFarmingTab");}
	public ItemStack getTabIconItem() { return new ItemStack(ModItems.COMPOST);}
}
