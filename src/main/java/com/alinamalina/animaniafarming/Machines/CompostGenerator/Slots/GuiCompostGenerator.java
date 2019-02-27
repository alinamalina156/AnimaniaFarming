package com.alinamalina.animaniafarming.Machines.CompostGenerator.Slots;

import com.alinamalina.animaniafarming.Machines.CompostGenerator.ContainerCompostGenerator;
import com.alinamalina.animaniafarming.Machines.CompostGenerator.TileEntityCompostGenerator;
import com.alinamalina.animaniafarming.util.Reference;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCompostGenerator extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/compost_generator.png");
	private final InventoryPlayer player;
	private final TileEntityCompostGenerator tileentity;
	
	public GuiCompostGenerator(InventoryPlayer player, TileEntityCompostGenerator tileentity) 
	{
		super(new ContainerCompostGenerator(player, tileentity));
		this.player = player;
		this.tileentity = tileentity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		String tileName = this.tileentity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) -5, 6, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 7, this.ySize - 96 + 2, 4210752);
		this.fontRenderer.drawString(Integer.toString(this.tileentity.getField(0)), 134, 38, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int l = this.getEnergyScaled(44);
		this.drawTexturedModalRect(this.guiLeft + 112, this.guiTop + 20 + 44 -l, 177, 33, 14 , l);
		
		int k = this.getBurnLeftScaled(24);//The progress arrow is 24 pixel tall
		this.drawTexturedModalRect(this.guiLeft + 76, this.guiTop + 36, 176, 14, k, 17);
		//this.drawTexturedModalRect(this.guiLeft + 44, this.guiTop + 36, 176, 14, l + 1, 16);//Location from the left

		if(this.getIsBurning(13))//The fire animation is 13 pixel tall
		this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 54, 176, 0, 14 ,14);
	}
	
	private int getEnergyScaled(int pixels)
	{
		int i = this.tileentity.getField(0);
		int j = this.tileentity.getField(1);
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
	
	private int getBurnLeftScaled(int pixels)
	{
		int i = this.tileentity.getField(2);
		int j = this.tileentity.getField(3);
		return i != 0 ? i * pixels / j : 0;

	}

	private boolean getIsBurning(int pixels)
	{
		int i = this.tileentity.getField(2);
		if(i>0)
			return true;
		return false;
	}
}