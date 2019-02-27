package com.alinamalina.animaniafarming.Fluids;

import com.alinamalina.animaniafarming.init.ModFluids;

import net.minecraft.block.material.Material;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.Fluid;

public class FluidBase extends Fluid
{
		    protected int mapColor = 0xFFFFFFFF;
		    protected float overlayAlpha = 0.2F;
		    protected SoundEvent emptySound = SoundEvents.ITEM_BUCKET_EMPTY;
		    protected SoundEvent fillSound = SoundEvents.ITEM_BUCKET_FILL;
		    protected boolean bucketEnabled = false;

	    
		    /**
		     * Instantiates a new mod fluid.
		     *
		     * @param fluidName
		     *            the fluid name
		     * @param still
		     *            the still
		     * @param flowing
		     *            the flowing
		     */
		    
	public FluidBase(String fluidName, ResourceLocation still, ResourceLocation flowing) 
		{
	        super(fluidName, still, flowing);
	        
	        
	        //overlayAlpha_ = overlayAlpha;
	        //mapColor_ = mapColor;
	    }
	
		/**
	     * Instantiates a new mod fluid.
	     *
	     * @param fluidName
	     *            the fluid name
	     * @param still
	     *            the still
	     * @param flowing
	     *            the flowing
	     * @param mapColor
	     *            the map color
	     */
	
	public  FluidBase(String fluidName, ResourceLocation still, ResourceLocation flowing, int mapColor)
	    {
	        this(fluidName, still, flowing);
	        setColor(mapColor);
		}
	
		/**
	     * Instantiates a new mod fluid.
	     *
	     * @param fluidName
	     *            the fluid name
	     * @param still
	     *            the still
	     * @param flowing
	     *            the flowing
	     * @param mapColor
	     *            the map color
	     * @param overlayAlpha
	     *            the overlay alpha
	     */
	
	    public FluidBase(String fluidName, ResourceLocation still, ResourceLocation flowing, int mapColor, float overlayAlpha)
	    {
	        this(fluidName, still, flowing, mapColor);
	        setAlpha(overlayAlpha);
	    }
	    
	    /*
	     * (non-Javadoc)
	     * 
	     * @see net.minecraftforge.fluids.Fluid#getColor()
	     */
	    @Override
	    public int getColor()
	    {
	        return mapColor;
	    }

	    /**
	     * Sets the color.
	     *
	     * @param parColor
	     *            the par color
	     * @return the fluid
	     */
	    @Override
	    public FluidBase setColor(int parColor)
	    {
	        mapColor = parColor;
	        return this;
	    }

	    /**
	     * Gets the alpha.
	     *
	     * @return the alpha
	     */
	    public float getAlpha()
	    {
	        return overlayAlpha;
	    }

	    /**
	     * Sets the alpha.
	     *
	     * @param parOverlayAlpha
	     *            the par overlay alpha
	     * @return the fluid
	     */
	    public FluidBase setAlpha(float parOverlayAlpha)
	    {
	        overlayAlpha = parOverlayAlpha;
	        return this;
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see net.minecraftforge.fluids.Fluid#setEmptySound(net.minecraft.util.SoundEvent)
	     */
	    @Override
	    public FluidBase setEmptySound(SoundEvent parSound)
	    {
	        emptySound = parSound;
	        return this;
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see net.minecraftforge.fluids.Fluid#getEmptySound()
	     */
	    @Override
	    public SoundEvent getEmptySound()
	    {
	        return emptySound;
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see net.minecraftforge.fluids.Fluid#setFillSound(net.minecraft.util.SoundEvent)
	     */
	    @Override
	    public FluidBase setFillSound(SoundEvent parSound)
	    {
	        fillSound = parSound;
	        return this;
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see net.minecraftforge.fluids.Fluid#getFillSound()
	     */
	    @Override
	    public SoundEvent getFillSound()
	    {
	        return fillSound;
	    }
	    
	    public FluidBase setHasBucket(boolean parEnableBucket)
	    {
	        bucketEnabled = parEnableBucket;
	        return this;
	    }
	    
	    public boolean isBucketEnabled()
	    {
	        return bucketEnabled;
	    }
	}