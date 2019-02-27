//Axes are special they need to be defined as a weapon as well
package com.alinamalina.animaniafarming.items.tools;

import com.alinamalina.animaniafarming.Main;
import com.alinamalina.animaniafarming.init.ModItems;
import com.alinamalina.animaniafarming.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.Item.ToolMaterial;

public class ToolAxe extends ItemAxe implements IHasModel {
	
	public ToolAxe(String name, ToolMaterial material)
	{
		super(material, 6.0F, -3.2F);//Inherit material, damage, speed
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
		
	}


}
