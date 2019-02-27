package com.alinamalina.animaniafarming.Machines.BiogasFermenter.Slots;


import com.alinamalina.animaniafarming.Machines.BiogasFermenter.ContainerBiogasFermenter;
import com.alinamalina.animaniafarming.Machines.BiogasFermenter.ContainerBiogasFermenter;
import com.alinamalina.animaniafarming.Machines.BiogasFermenter.TileEntityBiogasFermenter;
import com.alinamalina.animaniafarming.util.Reference;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiBiogasFermenter extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/biogas_fermenter.png");
	private final InventoryPlayer player;
	private final TileEntityBiogasFermenter tileentity;
	
	public GuiBiogasFermenter(InventoryPlayer player, TileEntityBiogasFermenter tileentity) 
	{
		super(new ContainerBiogasFermenter(player, tileentity));
		this.player = player;
		this.tileentity = tileentity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		String tileName = this.tileentity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) -5, 6, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 7, this.ySize - 96 + 2, 4210752);
		this.fontRenderer.drawString(Integer.toString(this.tileentity.getField(1)), 144, 38, 4210752);
		//this.fontRenderer.drawString(Integer.toString(this.tileentity.getField(3)), 17, 38, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int l = this.getWaterScaled(44);
		this.drawTexturedModalRect(this.guiLeft + 22, this.guiTop + 20 + 44 -l, 177, 33, 14 , l);
		
		int b = this.getFluidAmount(44);
		this.drawTexturedModalRect(this.guiLeft + 121, this.guiTop + 20, 177, 79, 15 , b);
		
		int k = this.getBurnLeftScaled(24);//The progress arrow is 24 pixel tall
		this.drawTexturedModalRect(this.guiLeft + 76, this.guiTop + 36, 176, 14, k, 17);

		if(this.getIsBurning(13))//The fire animation is 13 pixel tall
		this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 54, 176, 0, 14 ,14);
	}
	
	private int getWaterScaled(int pixels)
	{
		int i = this.tileentity.getField(1);
		int j = this.tileentity.getField(2);
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
	
	private int getFluidAmount(int pixels)
	{
		int i = this.tileentity.getField(3);
		int j = this.tileentity.getField(4);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
	private int getBurnLeftScaled(int pixels)
	{
		int i = this.tileentity.getField(5);
		int j = this.tileentity.getField(6);
		return i != 0 ? i * pixels / j : 0;

	}

	private boolean getIsBurning(int pixels)
	{
		int i = this.tileentity.getField(5);
		if(i>0)
			return true;
		return false;
	}
}