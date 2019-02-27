package com.alinamalina.animaniafarming.Machines.ManureCollector.Slots;

import com.alinamalina.animaniafarming.Machines.TileEntityBase;
import com.alinamalina.animaniafarming.Machines.CompostGenerator.ContainerCompostGenerator;
import com.alinamalina.animaniafarming.Machines.CompostGenerator.TileEntityCompostGenerator;
import com.alinamalina.animaniafarming.Machines.ManureCollector.ContainerManureCollector;
import com.alinamalina.animaniafarming.Machines.ManureCollector.TileEntityManureCollector;
import com.alinamalina.animaniafarming.util.Reference;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiManureCollector extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/manure_collector.png");
	private final InventoryPlayer player;
	private final TileEntityManureCollector tileentity;
	
	public GuiManureCollector(InventoryPlayer player, TileEntityManureCollector tileentity) 
	{
		super(new ContainerManureCollector(player, tileentity));
		this.player = player;
		this.tileentity = tileentity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		String tileName = this.tileentity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) -5, 6, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 7, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int b = this.getFluidAmount(44);
		this.drawTexturedModalRect(this.guiLeft + 81, this.guiTop + 20 + 44 - b, 177, 79, 15 , b);

	}

	
	private int getFluidAmount(int pixels)
	{
		int i = this.tileentity.getField(1);
		int j = this.tileentity.getField(2);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
	
}