/*
 * This file ("OilGenRecipe.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package com.alinamalina.animaniafarming.Machines.BiogasGenerator;

public class BiogasGeneratorRecipe{

    public final String fluidName;
    public final int genAmount;
    public final int genTime;

    public BiogasGeneratorRecipe(String fluidName, int genAmount, int genTime){
        this.fluidName = fluidName;
        this.genAmount = genAmount;
        this.genTime = genTime;
    }

}
