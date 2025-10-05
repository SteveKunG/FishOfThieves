package com.stevekung.fishofthieves.neoforge.datagen;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.neoforge.CompostableList;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

@EventBusSubscriber(modid = FishOfThieves.MOD_ID)
public class FOTDataMapGenerator
{
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Server event)
    {
        FishOfThieves.initCompostables();
        event.getGenerator().addProvider(true, (DataProvider.Factory<FOTDataMapProvider>) output -> new FOTDataMapProvider(output, event.getLookupProvider()));
    }

    private static class FOTDataMapProvider extends DataMapProvider
    {
        public FOTDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider)
        {
            super(packOutput, lookupProvider);
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void gather(HolderLookup.Provider provider)
        {
            var compostable = this.builder(NeoForgeDataMaps.COMPOSTABLES);
            CompostableList.COMPOSTABLES.forEach((itemLike, value) -> compostable.add(itemLike.asItem().builtInRegistryHolder(), new Compostable(value, false), false));
        }
    }
}