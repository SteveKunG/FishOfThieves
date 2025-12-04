package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class FOTCreativeTabs
{
    public static final ResourceKey<CreativeModeTab> MAIN = ResourceKey.create(Registries.CREATIVE_MODE_TAB, FishOfThieves.id("main"));
    public static final ResourceKey<CreativeModeTab> FISH = ResourceKey.create(Registries.CREATIVE_MODE_TAB, FishOfThieves.id("fish"));

    public static void init()
    {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FOTCreativeTabs.MAIN, create("itemGroup.fishofthieves.main", new ItemStack(FOTBlocks.COCONUT_LOG), FOTDisplayItems::displayMainItems));
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FOTCreativeTabs.FISH, create("itemGroup.fishofthieves.fish", new ItemStack(FOTItems.SPLASHTAIL), FOTDisplayItems::displayFishItems));
    }

    private static CreativeModeTab create(String title, ItemStack itemStack, CreativeModeTab.DisplayItemsGenerator displayItemsGenerator)
    {
        return FOTPlatform.getCreativeTabBuilder().title(Component.translatable(title)).icon(() -> itemStack).displayItems(displayItemsGenerator).build();
    }
}