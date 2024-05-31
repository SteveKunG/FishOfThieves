package com.stevekung.fishofthieves.fabric.datagen;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.variant.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.data.registries.RegistryPatchGenerator;
import net.minecraft.network.chat.Component;

public class SimpleSpawningConditionPackGenerator implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator)
    {
        var pack = dataGenerator.createBuiltinResourcePack(FishOfThieves.id("simple_spawning_condition_pack"));
        //@formatter:off
        var extraProvider = RegistryPatchGenerator.createLookup(dataGenerator.getRegistries(), new RegistrySetBuilder()
                .add(FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants::bootstrapSimple)
                .add(FOTRegistries.PONDIE_VARIANT, PondieVariants::bootstrapSimple)
                .add(FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants::bootstrapSimple)
                .add(FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants::bootstrapSimple)
                .add(FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants::bootstrapSimple)
                .add(FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants::bootstrapSimple)
                .add(FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants::bootstrapSimple)
                .add(FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants::bootstrapSimple)
                .add(FOTRegistries.WRECKER_VARIANT, WreckerVariants::bootstrapSimple)
                .add(FOTRegistries.STORMFISH_VARIANT, StormfishVariants::bootstrapSimple)
        ).thenApply(RegistrySetBuilder.PatchedRegistries::full);
        //@formatter:on

        pack.addProvider((output, provider) -> new DynamicRegistryProvider(output, extraProvider));
        pack.addProvider((output, provider) -> PackMetadataGenerator.forFeaturePack(output, Component.translatable("dataPack.simple_spawning_condition_pack.description")));
    }

    private static class DynamicRegistryProvider extends FabricDynamicRegistryProvider
    {
        public DynamicRegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
        {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(HolderLookup.Provider registries, Entries entries)
        {
            entries.addAll(registries.lookupOrThrow(FOTRegistries.SPLASHTAIL_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.PONDIE_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.ISLEHOPPER_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.ANCIENTSCALE_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.PLENTIFIN_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.WILDSPLASH_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.DEVILFISH_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.BATTLEGILL_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.WRECKER_VARIANT));
            entries.addAll(registries.lookupOrThrow(FOTRegistries.STORMFISH_VARIANT));
        }

        @Override
        public String getName()
        {
            return "Fish of Thieves Dynamic Registries EP2";
        }
    }
}