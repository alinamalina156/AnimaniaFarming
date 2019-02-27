package com.alinamalina.animaniafarming.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.alinamalina.animaniafarming.Fluids.FluidBase;
import com.alinamalina.animaniafarming.util.Reference;
import com.google.common.collect.ImmutableSet;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids 
{
	

	public static final FluidBase MANURE = (FluidBase) new FluidBase(

			"manure", 

			  new ResourceLocation(Reference.MOD_ID,"fluids/manure_still"), 
		
			  new ResourceLocation(Reference.MOD_ID, "fluids/manure_flow")
		
			  ) 
		
			  .setHasBucket(true)
		
			  .setDensity(10000)
		
			  .setGaseous(false)
		
			  .setLuminosity(0)
		
			  .setViscosity(25000)

	  .setTemperature(300);
	
	
	
	public static final FluidBase BIOGAS = (FluidBase) new FluidBase(

			  "biogas", 

			  new ResourceLocation(Reference.MOD_ID,"fluids/biogas_still"), 

			  new ResourceLocation(Reference.MOD_ID, "fluids/biogas_flow")

			  ) 

			  .setHasBucket(true)

			  .setDensity(-100)

			  .setGaseous(true)

			  .setLuminosity(1)

			  .setViscosity(10000)

			  .setTemperature(400);

	   
	public static final Set<FluidBase> SET_FLUIDS = ImmutableSet.of(

            BIOGAS, MANURE);
	 

	 

	  public static void registerFluids()

	    {

	        // DEBUG

	        System.out.println("Registering fluids");

	        for (final FluidBase fluid : SET_FLUIDS)

	        {

	            FluidRegistry.registerFluid(fluid);

	            if (fluid.isBucketEnabled())

	            {

	                FluidRegistry.addBucketForFluid(fluid);

	            }

	            // DEBUG

	            System.out.println("Registering fluid: " + fluid.getName()+" with bucket = "+fluid.isBucketEnabled());

	        }

	    }

	}