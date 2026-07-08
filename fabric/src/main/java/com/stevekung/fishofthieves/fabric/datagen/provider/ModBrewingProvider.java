package com.stevekung.fishofthieves.fabric.datagen.provider;

import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.data.recipes.BrewingProvider;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.alchemy.Potions;

public class ModBrewingProvider extends BrewingProvider
{
    protected ModBrewingProvider(RecipeOutput recipeOutput)
    {
        super(recipeOutput);
    }

    @Override
    protected void buildMixes()
    {
        this.buildMix(Potions.AWKWARD, FOTItems.PLENTIFIN, Potions.LUCK);
        this.buildMix(Potions.AWKWARD, FOTItems.ISLEHOPPER, Potions.WATER_BREATHING);
    }

    @Override
    protected void addContainers() {}

    @Override
    protected void addContainerTransformations() {}
}