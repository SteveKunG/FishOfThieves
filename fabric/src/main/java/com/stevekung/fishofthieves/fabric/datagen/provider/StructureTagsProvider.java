package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;

public class StructureTagsProvider extends FabricTagProvider<Structure>
{
    public StructureTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, Registries.STRUCTURE, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.getOrCreateTagBuilder(FOTTags.Structures.BONE_ANCIENTSCALES_SPAWN_IN).add(BuiltinStructures.STRONGHOLD).forceAddTag(StructureTags.MINESHAFT);
        this.getOrCreateTagBuilder(FOTTags.Structures.BONEDUST_PLENTIFINS_SPAWN_IN).add(BuiltinStructures.STRONGHOLD).forceAddTag(StructureTags.MINESHAFT);
        this.getOrCreateTagBuilder(FOTTags.Structures.BATTLEGILLS_SPAWN_IN).add(BuiltinStructures.OCEAN_MONUMENT, BuiltinStructures.PILLAGER_OUTPOST, BuiltinStructures.TRIAL_CHAMBERS);
        this.getOrCreateTagBuilder(FOTTags.Structures.ANCIENTSCALES_SPAWN_IN).forceAddTag(StructureTags.OCEAN_RUIN).add(BuiltinStructures.STRONGHOLD).forceAddTag(StructureTags.MINESHAFT);
        this.getOrCreateTagBuilder(FOTTags.Structures.PLENTIFINS_SPAWN_IN).add(BuiltinStructures.STRONGHOLD).forceAddTag(StructureTags.MINESHAFT);
        this.getOrCreateTagBuilder(FOTTags.Structures.WRECKERS_SPAWN_IN).add(BuiltinStructures.SHIPWRECK, BuiltinStructures.RUINED_PORTAL_OCEAN);
        this.getOrCreateTagBuilder(FOTTags.Structures.WRECKERS_LOCATED).add(BuiltinStructures.SHIPWRECK, BuiltinStructures.RUINED_PORTAL_OCEAN);
    }
}