package com.alinamalina.animaniafarming;


import java.util.Random;

import com.alinamalina.animaniafarming.init.ModFluids;
import com.alinamalina.animaniafarming.init.ModRecipes;
import com.alinamalina.animaniafarming.network.NetworkHandler;
import com.alinamalina.animaniafarming.proxy.CommonProxy;
import com.alinamalina.animaniafarming.util.Reference;
import com.alinamalina.animaniafarming.util.handler.RegistryHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main 
{
	public static final CreativeTabs AnimaniaFarmingTab = new com.alinamalina.animaniafarming.tabs.AnimaniaFarmingTab("AnimaniaFarmingTab");

	// instantiate the mod
	@Instance
	public static Main instance;
	public static SimpleNetworkWrapper network;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	static
    {
        FluidRegistry.enableUniversalBucket();
    }
	
	/**
     * Pre-Initialization FML Life Cycle event handling method which is automatically
     * called by Forge. It must be annotated as an event handler.
     *
     * @param event the event
     */
	
	// preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry."
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		
		ModFluids.registerFluids();
		RegistryHandler.preInitRegistries();
		NetworkHandler.init();
	}
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		ModRecipes.init();
		RegistryHandler.initRegistries();
		
	}
	
	@EventHandler
	public static void Postinit(FMLPostInitializationEvent event)
	{
		RegistryHandler.postInitRegistries();
	}
	
    static 
    {
        if (!FluidRegistry.isUniversalBucketEnabled()) FluidRegistry.enableUniversalBucket();
    }
}
