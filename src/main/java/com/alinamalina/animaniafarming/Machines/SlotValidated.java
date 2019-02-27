package com.alinamalina.animaniafarming.Machines;

import javax.swing.plaf.basic.BasicComboBoxUI.ItemHandler;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotValidated extends SlotItemHandler {

	ISlotValidator validator;

	public SlotValidated(ISlotValidator validator, IItemHandler handler, int index, int x, int y) 
	{

		super(handler, index, x, y);
		this.validator = validator;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {

		return validator.isItemValid(stack);
	}

}
