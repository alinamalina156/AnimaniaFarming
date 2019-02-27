package com.alinamalina.animaniafarming.items;

import com.alinamalina.animaniafarming.Main;
import com.alinamalina.animaniafarming.init.ModItems;
import com.alinamalina.animaniafarming.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {
	
	public ItemBase(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.AnimaniaFarmingTab);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
		
	}

}
