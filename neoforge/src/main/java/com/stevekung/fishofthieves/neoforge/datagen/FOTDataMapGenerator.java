package com.stevekung.fishofthieves.neoforge.datagen;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.neoforge.AxeStrippableDummy;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.component.BlockTransformers;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.CopyPropertiesProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Transformable;

@EventBusSubscriber(modid = FishOfThieves.MOD_ID)
public class FOTDataMapGenerator
{
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Server event)
    {
        event.getGenerator().addProvider(true, (DataProvider.Factory<FOTDataMapProvider>) output -> new FOTDataMapProvider(output, event.getWorldLookupProvider()));
    }

    private static class FOTDataMapProvider extends DataMapProvider
    {
        public FOTDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider)
        {
            super(packOutput, lookupProvider);
        }

        @Override
        protected void gather(HolderLookup.Provider provider)
        {
            var transformable = this.builder(NeoForgeDataMaps.TRANSFORMABLES);
            Stream.of(AxeStrippableDummy.STRIPPED_BLOCKS, AxeStrippableDummy.Small.CUSTOM_STRIPPABLES, AxeStrippableDummy.Medium.CUSTOM_STRIPPABLES)
                    .flatMap(map -> map.entrySet().stream())
                    .forEach(entry -> transformable
                            .add(BuiltInRegistries.BLOCK.getKey(entry.getKey()),
                                    new Transformable(BlockTransformers.AXE, BlockTransformer.BlockTransformData
                                            .builder(RuleBasedStateProvider.builder()
                                                    .ifTrueThenProvide(BlockPredicate.matchesBlocks(entry.getKey()), new CopyPropertiesProvider(entry.getValue()))
                                                    .build())
                                            .sound(SoundEvents.AXE_STRIP)
                                            .build()), false));
        }
    }
}