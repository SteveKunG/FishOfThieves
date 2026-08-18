package com.stevekung.fishofthieves.fabric.datagen.provider;

import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.packs.VanillaBrewingProvider;
import net.minecraft.world.item.alchemy.Potions;

public class ModBrewingProvider extends VanillaBrewingProvider
{
    protected ModBrewingProvider(RecipeOutput recipeOutput)
    {
        super(recipeOutput);
    }

    @Override
    protected void buildMixes()
    {
        this.buildStartMix(FOTItems.PLENTIFIN, Potions.LUCK);
        this.buildStartMix(FOTItems.ISLEHOPPER, Potions.WATER_BREATHING);
    }

    @Override
    protected void addContainerTransformations() {}
}