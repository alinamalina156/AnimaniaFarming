package com.alinamalina.animaniafarming.Machines.Composter.Slots;

import com.alinamalina.animaniafarming.Machines.BiogasFermenter.ContainerBiogasFermenter;
import com.alinamalina.animaniafarming.Machines.Composter.ContainerComposter;
import com.alinamalina.animaniafarming.Machines.Composter.TileEntityComposter;
import com.alinamalina.animaniafarming.util.Reference;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiComposter extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/composter.png");
	private final InventoryPlayer player;
	private final TileEntityComposter tileentity;
	
	public GuiComposter(InventoryPlayer player, TileEntityComposter tileentity) 
	{
		super(new ContainerComposter(player, tileentity));
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
		
		int b = this.getFluidAmount(44);
		this.drawTexturedModalRect(this.guiLeft + 22, this.guiTop + 20 + 44 - b, 177, 79, 15 , b);
		
		int k = this.getBurnLeftScaled(24);//The progress arrow is 24 pixel tall
		this.drawTexturedModalRect(this.guiLeft + 76, this.guiTop + 36, 176, 14, k, 17);

	}
	
	
	private int getFluidAmount(int pixels)
	{
		int i = this.tileentity.getField(1);
		int j = this.tileentity.getField(2);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
	private int getBurnLeftScaled(int pixels)
	{
		int i = this.tileentity.getField(3);
		int j = this.tileentity.getField(4);
		return i != 0 ? i * pixels / j : 0;

	}

}
