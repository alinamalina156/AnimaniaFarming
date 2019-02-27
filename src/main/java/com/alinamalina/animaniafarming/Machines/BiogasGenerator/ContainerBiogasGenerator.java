package com.alinamalina.animaniafarming.Machines.BiogasGenerator;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;


public class ContainerBiogasGenerator extends Container
{

	private final TileEntityBiogasGenerator tileentity;
	private int cookTime, totalCookTime, burnTime, currentBurnTime;

	public ContainerBiogasGenerator(InventoryPlayer player, TileEntityBiogasGenerator tileentity) 
	{
		this.tileentity = tileentity;


		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
			}
		}

		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) 
	{
		return this.tileentity.isUsableByPlayer(playerIn);
	}
}