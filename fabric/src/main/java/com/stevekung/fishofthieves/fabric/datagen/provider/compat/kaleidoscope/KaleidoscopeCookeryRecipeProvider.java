package com.stevekung.fishofthieves.fabric.datagen.provider.compat.kaleidoscope;

import java.util.function.Consumer;

import com.github.ysbbbbbb.kaleidoscopecookery.datagen.builder.ChoppingBoardBuilder;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class KaleidoscopeCookeryRecipeProvider extends FabricRecipeProvider
{
    public KaleidoscopeCookeryRecipeProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer)
    {
        ChoppingBoardBuilder.builder()
                .setIngredient(FOTItems.COOKED_ANCIENTSCALE)
                .setResult(ModItems.SASHIMI, 2)
                .setCutCount(4)
                .setModelId(BuiltInRegistries.ITEM.getKey(ModItems.SASHIMI))
                .save(consumer, fromTo(ModItems.SASHIMI, FOTItems.COOKED_ANCIENTSCALE));
    }

    @Override
    public String getName()
    {
        return "FOT Kaleidoscope Cookery Recipes";
    }

    private static String fromTo(Item fromItem, Item toItem)
    {
        var fromName = BuiltInRegistries.ITEM.getKey(fromItem).getPath();
        var toName = BuiltInRegistries.ITEM.getKey(toItem).getPath();
        return fromName + "_from_" + toName;
    }
}