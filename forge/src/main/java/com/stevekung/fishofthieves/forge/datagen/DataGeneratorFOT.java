package com.stevekung.fishofthieves.forge.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.VanillaBlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FishOfThieves.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneratorFOT
{
    private static final TagKey<Item> RAW_FISHES = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "raw_fishes"));
    private static final TagKey<Item> COOKED_FISHES = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "cooked_fishes"));

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event)
    {
        var dataGenerator = event.getGenerator();
        var packOutput = dataGenerator.getPackOutput();
        var provider = event.getLookupProvider();
        var helper = event.getExistingFileHelper();
        dataGenerator.addProvider(event.includeServer(), new ForgeItemTags(packOutput, provider, helper));
    }

    private static class ForgeItemTags extends ItemTagsProvider
    {
        public ForgeItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper)
        {
            super(output, provider, new VanillaBlockTagsProvider(output, provider).contentsGetter(), FishOfThieves.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider)
        {
            var rawFishes = new Item[] { FOTItems.SPLASHTAIL, FOTItems.PONDIE, FOTItems.ISLEHOPPER, FOTItems.ANCIENTSCALE, FOTItems.PLENTIFIN, FOTItems.WILDSPLASH, FOTItems.DEVILFISH, FOTItems.BATTLEGILL, FOTItems.WRECKER, FOTItems.STORMFISH };
            var cookedFishes = new Item[] { FOTItems.COOKED_SPLASHTAIL, FOTItems.COOKED_PONDIE, FOTItems.COOKED_ISLEHOPPER, FOTItems.COOKED_ANCIENTSCALE, FOTItems.COOKED_PLENTIFIN, FOTItems.COOKED_WILDSPLASH, FOTItems.COOKED_DEVILFISH, FOTItems.COOKED_BATTLEGILL, FOTItems.COOKED_WRECKER, FOTItems.COOKED_STORMFISH };

            this.tag(RAW_FISHES).add(rawFishes);
            this.tag(COOKED_FISHES).add(cookedFishes);
        }
    }
}