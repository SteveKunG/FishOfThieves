package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;

public class StructureTagsProvider extends FabricTagsProvider<Structure>
{
    public StructureTagsProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, Registries.STRUCTURE, provider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.builder(FOTTags.Structures.BATTLEGILLS_SPAWN_IN).add(BuiltinStructures.OCEAN_MONUMENT, BuiltinStructures.PILLAGER_OUTPOST, BuiltinStructures.TRIAL_CHAMBERS);
        this.builder(FOTTags.Structures.ANCIENTSCALES_SPAWN_IN).forceAddTag(StructureTags.OCEAN_RUIN);
        this.builder(FOTTags.Structures.WRECKERS_SPAWN_IN).add(BuiltinStructures.SHIPWRECK, BuiltinStructures.RUINED_PORTAL_OCEAN);
        this.builder(FOTTags.Structures.WRECKERS_LOCATED).add(BuiltinStructures.SHIPWRECK, BuiltinStructures.RUINED_PORTAL_OCEAN);
    }
}