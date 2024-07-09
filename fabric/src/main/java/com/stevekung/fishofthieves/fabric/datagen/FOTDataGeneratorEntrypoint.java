package com.stevekung.fishofthieves.fabric.datagen;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.fabric.datagen.provider.*;
import com.stevekung.fishofthieves.fabric.datagen.variant.*;
import com.stevekung.fishofthieves.registry.FOTFeatures;
import com.stevekung.fishofthieves.registry.FOTPlacements;
import com.stevekung.fishofthieves.registry.FOTStructures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

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
        pack.addProvider(AdvancementProvider::new);
        pack.addProvider(ModFishingRealProvider::new);
        pack.addProvider(DynamicRegistryProvider::new);

        pack.addProvider(SplashtailVariantTagsProvider::new);
        pack.addProvider(PondieVariantTagsProvider::new);
        pack.addProvider(IslehopperVariantTagsProvider::new);
        pack.addProvider(AncientscaleVariantTagsProvider::new);
        pack.addProvider(PlentifinVariantTagsProvider::new);
        pack.addProvider(WildsplashVariantTagsProvider::new);
        pack.addProvider(DevilfishVariantTagsProvider::new);
        pack.addProvider(BattlegillVariantTagsProvider::new);
        pack.addProvider(WreckerVariantTagsProvider::new);
        pack.addProvider(StormfishVariantTagsProvider::new);
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
        }

        @Override
        public String getName()
        {
            return "Fish of Thieves Dynamic Registries";
        }
    }
}