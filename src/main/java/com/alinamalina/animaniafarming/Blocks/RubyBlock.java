package com.alinamalina.animaniafarming.Blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RubyBlock extends BlockBase 
{

	public RubyBlock(String name, Material material) 
	{
		super(name, material);
		
		setSoundType(SoundType.METAL);//Sound walking over the block
		setHardness(5.0F); //How long it takes to break the block
		setResistance(15.0F); //How resistant the block to explosion
		setHarvestLevel("pickaxe", 2); //The type of tool is needed to break the block, the value is harvest level - type of pickaxe
		setLightLevel(1.0F);//Level of light emitted by the block
		//setLightOpacity(1);//How much the block is transparent to light
		//setBlockUnbreakable();//In case the block is unbreakable
		

	}

}
