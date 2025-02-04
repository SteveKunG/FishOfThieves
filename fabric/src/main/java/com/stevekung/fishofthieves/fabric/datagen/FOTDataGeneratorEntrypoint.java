package com.stevekung.fishofthieves.fabric.datagen;

import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.fabric.datagen.provider.*;
import com.stevekung.fishofthieves.item.FishPlaqueInteractions;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.*;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.data.structures.SnbtToNbt;
import net.minecraft.data.structures.StructureUpdater;

@SuppressWarnings("unused")
public class FOTDataGeneratorEntrypoint implements DataGeneratorEntrypoint
{
    @Override
    public void buildRegistry(RegistrySetBuilder builder)
    {
        builder.add(Registries.STRUCTURE, FOTStructures::bootstrap);
        builder.add(Registries.STRUCTURE_SET, FOTStructures.Sets::bootstrap);
        builder.add(Registries.CONFIGURED_FEATURE, FOTFeatures::bootstrap);
        builder.add(Registries.PLACED_FEATURE, FOTPlacements::bootstrap);
        builder.add(Registries.DAMAGE_TYPE, FOTDamageTypes::bootstrap);
        builder.add(Registries.BIOME, FOTBiomes::bootstrap);
        builder.add(Registries.NOISE, FOTNoises::bootstrap);
        builder.add(FOTRegistries.SPLASHTAIL_VARIANT, SplashtailVariants::bootstrap);
        builder.add(FOTRegistries.PONDIE_VARIANT, PondieVariants::bootstrap);
        builder.add(FOTRegistries.ISLEHOPPER_VARIANT, IslehopperVariants::bootstrap);
        builder.add(FOTRegistries.ANCIENTSCALE_VARIANT, AncientscaleVariants::bootstrap);
        builder.add(FOTRegistries.PLENTIFIN_VARIANT, PlentifinVariants::bootstrap);
        builder.add(FOTRegistries.WILDSPLASH_VARIANT, WildsplashVariants::bootstrap);
        builder.add(FOTRegistries.DEVILFISH_VARIANT, DevilfishVariants::bootstrap);
        builder.add(FOTRegistries.BATTLEGILL_VARIANT, BattlegillVariants::bootstrap);
        builder.add(FOTRegistries.WRECKER_VARIANT, WreckerVariants::bootstrap);
        builder.add(FOTRegistries.STORMFISH_VARIANT, StormfishVariants::bootstrap);
        builder.add(FOTRegistries.FISH_PLAQUE_INTERACTION, FishPlaqueInteractions::bootstrap);
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator)
    {
        var pack = dataGenerator.createPack();
        pack.addProvider(ModelProvider::new);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(BlockLootProvider::new);
        pack.addProvider(CustomBlockLootProvider::new);
        pack.addProvider(EntityLootProvider::new);
        pack.addProvider(ChestLootProvider::new);
        pack.addProvider(AdvancementRewardProvider::new);
        var blockTagsProvider = pack.addProvider(BlockTagsProvider::new);
        pack.addProvider((dataOutput, provider) -> new ItemTagsProvider(dataOutput, provider, blockTagsProvider));
        pack.addProvider(EntityTagsProvider::new);
        pack.addProvider(BiomeTagsProvider::new);
        pack.addProvider(StructureTagsProvider::new);
        pack.addProvider(DamageTagsProvider::new);
        pack.addProvider(EnchantmentTagsProvider::new);
        pack.addProvider(AdvancementProvider::new);
        pack.addProvider(ModFishingRealProvider::new);
        pack.addProvider(DynamicRegistryProvider::new);
        pack.addProvider((dataOutput, provider) -> new LanguageSyncProvider(provider));

        new SimpleSpawningConditionPackGenerator().onInitializeDataGenerator(dataGenerator);

        var basePath = Paths.get("").toAbsolutePath().getParent().getParent().getParent();
        var inputPath = basePath.resolve("common/src/main/resources/data/").resolve(FishOfThieves.MOD_ID).resolve("structures");
        var snbtInputPath = basePath.resolve("common/src/generated/resources");

        pack.addProvider((dataOutput, provider) -> new NbtToSnbt(dataOutput, List.of(inputPath)));
        pack.addProvider((dataOutput, provider) -> new SnbtToNbt(new PackOutput(inputPath), List.of(snbtInputPath))
                .addFilter((structureLocationPath, tag) -> structureLocationPath.startsWith("data/" + FishOfThieves.MOD_ID + "/structures/") ? StructureUpdater.update(structureLocationPath, tag) : tag));
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
            entries.addAll(registries.lookupOrThrow(Registries.STRUCTURE));
            entries.addAll(registries.lookupOrThrow(Registries.STRUCTURE_SET));
            entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE));
            entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));
            entries.addAll(registries.lookupOrThrow(Registries.DAMAGE_TYPE));
            entries.addAll(registries.lookupOrThrow(Registries.BIOME));
            entries.addAll(registries.lookupOrThrow(Registries.NOISE));
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
            entries.addAll(registries.lookupOrThrow(FOTRegistries.FISH_PLAQUE_INTERACTION));
        }

        @Override
        public String getName()
        {
            return "Fish of Thieves Dynamic Registries";
        }
    }
}