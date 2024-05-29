package com.stevekung.fishofthieves.forge.level;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.forge.FishOfThievesForge;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.world.ModifiableStructureInfo;
import net.minecraftforge.common.world.StructureModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FOTStructureModifiers
{
    private static final Codec<TagKey<Structure>> STRUCTURE_LIST_CODEC = TagKey.hashedCodec(Registries.STRUCTURE);
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(ForgeRegistries.Keys.STRUCTURE_MODIFIERS, context ->
    {
        context.register(key("ancientscales_spawn_in"), addStructureSpawns(FOTEntities.SpawnData.ANCIENTSCALE.unwrap().get(0), FOTTags.Structures.ANCIENTSCALES_SPAWN_IN));
        context.register(key("plentifins_spawn_in"), addStructureSpawns(FOTEntities.SpawnData.PLENTIFIN.unwrap().get(0), FOTTags.Structures.PLENTIFINS_SPAWN_IN));
        context.register(key("wreckers_spawn_in"), addStructureSpawns(FOTEntities.SpawnData.WRECKER.unwrap().get(0), FOTTags.Structures.WRECKERS_SPAWN_IN));
        context.register(key("battlegills_spawn_in"), addStructureSpawns(FOTEntities.SpawnData.BATTLEGILL.unwrap().get(0), FOTTags.Structures.BATTLEGILLS_SPAWN_IN));
    });

    public static void generateStructureModifiers(GatherDataEvent event)
    {
        event.getGenerator().addProvider(event.includeServer(), (DataProvider.Factory<StructureModifiers>) output -> new StructureModifiers(output, event.getLookupProvider()));
    }

    private static class StructureModifiers extends DatapackBuiltinEntriesProvider
    {
        public StructureModifiers(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
        {
            super(output, registries, BUILDER, Set.of(FishOfThieves.MOD_ID));
        }

        @Override
        public String getName()
        {
            return "Structure Modifier Registries: " + FishOfThieves.MOD_ID;
        }
    }

    private static StructureModifier addStructureSpawns(MobSpawnSettings.SpawnerData spawnerData, TagKey<Structure> structureTagKey)
    {
        return new Modifier(structureTagKey, spawnerData);
    }

    private static ResourceKey<StructureModifier> key(String key)
    {
        return ResourceKey.create(ForgeRegistries.Keys.STRUCTURE_MODIFIERS, FishOfThieves.id(key));
    }

    public record Modifier(TagKey<Structure> structureTagKey, MobSpawnSettings.SpawnerData spawnerData) implements StructureModifier
    {
        private static final RegistryObject<Codec<? extends StructureModifier>> SERIALIZER = RegistryObject.create(FishOfThievesForge.ADD_THIEVES_FISH_SPAWNS_IN_STRUCTURE_RL, ForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, FishOfThieves.MOD_ID);

        @Override
        public void modify(Holder<Structure> structure, Phase phase, ModifiableStructureInfo.StructureInfo.Builder builder)
        {
            if (phase == Phase.ADD && structure.is(this.structureTagKey))
            {
                builder.getStructureSettings().getOrAddSpawnOverrides(this.spawnerData.type.getCategory()).addSpawn(this.spawnerData);
            }
        }

        @Override
        public Codec<? extends StructureModifier> codec()
        {
            return SERIALIZER.get();
        }

        public static Codec<Modifier> makeCodec()
        {
            //@formatter:off
            return RecordCodecBuilder.create(builder -> builder.group(
                            STRUCTURE_LIST_CODEC.fieldOf("structure").forGetter(Modifier::structureTagKey),
                            MobSpawnSettings.SpawnerData.CODEC.fieldOf("spawnerData").forGetter(Modifier::spawnerData))
                    .apply(builder, Modifier::new));
            //@formatter:on
        }
    }
}