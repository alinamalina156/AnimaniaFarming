//Smelting Recipes
package com.alinamalina.animaniafarming.init;



import java.util.ArrayList;
import java.util.List;

import com.alinamalina.animaniafarming.Machines.BiogasGenerator.BiogasGeneratorRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
	
	public static void init() 
	{
		GameRegistry.addSmelting(ModItems.RUBY, new ItemStack(ModBlocks.RUBY_BLOCK, 1), 1.5f);//Smelting one ruby from ModItemsto get one ruby block and get xp
		GameRegistry.addSmelting(ModItems.STRAW_CLAY, new ItemStack(ModItems.STRAW_CLAY_BRICK, 1), 1.5f);//Smelting one ruby from ModItemsto get one ruby block and get xp
		GameRegistry.addSmelting(ModItems.COMPOST, new ItemStack(ModItems.CRUDE_CARBIDE, 1), 1.5f);
		
		
	}
	
	public static final List<BiogasGeneratorRecipe> BIOGAS_GENERATOR_RECIPES = new ArrayList<BiogasGeneratorRecipe>();

}
