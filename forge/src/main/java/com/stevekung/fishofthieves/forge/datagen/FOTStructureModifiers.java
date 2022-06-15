package com.stevekung.fishofthieves.forge.datagen;

import java.util.Map;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.forge.core.FishOfThievesForge;
import com.stevekung.fishofthieves.registry.FOTEntities;
import net.minecraft.core.*;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.ModifiableStructureInfo;
import net.minecraftforge.common.world.StructureModifier;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FOTStructureModifiers
{
    private static final Codec<HolderSet<Structure>> STRUCTURE_LIST_CODEC = RegistryCodecs.homogeneousList(Registry.STRUCTURE_REGISTRY, Structure.DIRECT_CODEC);

    public static void generateStructureModifiers(GatherDataEvent event)
    {
        var generator = event.getGenerator();
        var ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get());
        var structureRegistry = ops.registry(Registry.STRUCTURE_REGISTRY).get();

        generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(generator, event.getExistingFileHelper(), FishOfThieves.MOD_ID, ops, ForgeRegistries.Keys.STRUCTURE_MODIFIERS, Map.of(
                new ResourceLocation(FishOfThieves.MOD_ID, "modify_ocean_ruins"), addStructureSpawns(FOTEntities.ANCIENTSCALES.unwrap().get(0), structureRegistry.getHolder(BuiltinStructures.OCEAN_RUIN_COLD).orElseThrow(), structureRegistry.getHolder(BuiltinStructures.OCEAN_RUIN_WARM).orElseThrow()),
                new ResourceLocation(FishOfThieves.MOD_ID, "modify_mineshafts"), addStructureSpawns(FOTEntities.PLENTIFINS.unwrap().get(0), structureRegistry.getHolder(BuiltinStructures.MINESHAFT).orElseThrow(), structureRegistry.getHolder(BuiltinStructures.MINESHAFT_MESA).orElseThrow()),
                new ResourceLocation(FishOfThieves.MOD_ID, "modify_strongholds"), addStructureSpawns(FOTEntities.PLENTIFINS.unwrap().get(0), structureRegistry, BuiltinStructures.STRONGHOLD),
                new ResourceLocation(FishOfThieves.MOD_ID, "modify_shipwrecks"), addStructureSpawns(FOTEntities.WRECKERS.unwrap().get(0), structureRegistry, BuiltinStructures.SHIPWRECK),
                new ResourceLocation(FishOfThieves.MOD_ID, "modify_ocean_monuments"), addStructureSpawns(FOTEntities.BATTLEGILLS.unwrap().get(0), structureRegistry, BuiltinStructures.OCEAN_MONUMENT),
                new ResourceLocation(FishOfThieves.MOD_ID, "modify_pillager_outposts"), addStructureSpawns(FOTEntities.BATTLEGILLS.unwrap().get(0), structureRegistry, BuiltinStructures.PILLAGER_OUTPOST)
        )));
    }

    private static StructureModifier addStructureSpawns(MobSpawnSettings.SpawnerData spawnerData, Registry<Structure> structureRegistry, ResourceKey<Structure> structure)
    {
        return new Modifier(HolderSet.direct(structureRegistry.getHolder(structure).orElseThrow()), spawnerData);
    }

    @SafeVarargs
    private static StructureModifier addStructureSpawns(MobSpawnSettings.SpawnerData spawnerData, Holder<Structure>... structures)
    {
        return new Modifier(HolderSet.direct(structures), spawnerData);
    }

    public record Modifier(HolderSet<Structure> structures, MobSpawnSettings.SpawnerData spawnerData) implements StructureModifier
    {
        private static final RegistryObject<Codec<? extends StructureModifier>> SERIALIZER = RegistryObject.create(FishOfThievesForge.ADD_THIEVES_FISH_SPAWNS_IN_STRUCTURE_RL, ForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, FishOfThieves.MOD_ID);

        @Override
        public void modify(Holder<Structure> structure, Phase phase, ModifiableStructureInfo.StructureInfo.Builder builder)
        {
            if (phase == Phase.ADD && this.structures.contains(structure))
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
            return RecordCodecBuilder.create(builder -> builder.group(
                            STRUCTURE_LIST_CODEC.fieldOf("structures").forGetter(Modifier::structures),
                            MobSpawnSettings.SpawnerData.CODEC.fieldOf("spawnerData").forGetter(Modifier::spawnerData))
                    .apply(builder, Modifier::new));
        }
    }
}