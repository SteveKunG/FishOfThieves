package com.stevekung.fishofthieves.neoforge.datagen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.ModifiableStructureInfo;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid = FishOfThieves.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class FOTStructureModifiers
{
    private static final String THIEVES_FISH_SPAWNS_IN_STRUCTURE = "thieves_fish_spawns_in_structure";
    private static final ResourceLocation ADD_THIEVES_FISH_SPAWNS_IN_STRUCTURE_RL = FishOfThieves.id(THIEVES_FISH_SPAWNS_IN_STRUCTURE);

    private static final DeferredRegister<MapCodec<? extends StructureModifier>> STRUCTURE_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.STRUCTURE_MODIFIER_SERIALIZERS, FishOfThieves.MOD_ID);
    private static final Codec<TagKey<Structure>> STRUCTURE_LIST_CODEC = TagKey.hashedCodec(Registries.STRUCTURE);
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(NeoForgeRegistries.Keys.STRUCTURE_MODIFIERS, context ->
    {
        context.register(key("ancientscales_spawn_in"), addStructureSpawns(FOTEntities.SpawnData.ANCIENTSCALE.unwrap().getFirst(), FOTTags.Structures.ANCIENTSCALES_SPAWN_IN));
        context.register(key("plentifins_spawn_in"), addStructureSpawns(FOTEntities.SpawnData.PLENTIFIN.unwrap().getFirst(), FOTTags.Structures.PLENTIFINS_SPAWN_IN));
        context.register(key("wreckers_spawn_in"), addStructureSpawns(FOTEntities.SpawnData.WRECKER.unwrap().getFirst(), FOTTags.Structures.WRECKERS_SPAWN_IN));
        context.register(key("battlegills_spawn_in"), addStructureSpawns(FOTEntities.SpawnData.BATTLEGILL.unwrap().getFirst(), FOTTags.Structures.BATTLEGILLS_SPAWN_IN));
    });

    public FOTStructureModifiers()
    {
        var modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        STRUCTURE_MODIFIERS.register(modEventBus);
    }

    @SubscribeEvent
    public static void onRegister(RegisterEvent event)
    {
        event.register(NeoForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, helper -> helper.register(ADD_THIEVES_FISH_SPAWNS_IN_STRUCTURE_RL, FOTStructureModifiers.Modifier.makeCodec()));
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event)
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
        return ResourceKey.create(NeoForgeRegistries.Keys.STRUCTURE_MODIFIERS, FishOfThieves.id(key));
    }

    public record Modifier(TagKey<Structure> structureTagKey, MobSpawnSettings.SpawnerData spawnerData) implements StructureModifier
    {
        private static final DeferredHolder<MapCodec<? extends StructureModifier>, MapCodec<? extends StructureModifier>> SERIALIZER = DeferredHolder.create(NeoForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, ADD_THIEVES_FISH_SPAWNS_IN_STRUCTURE_RL);

        @Override
        public void modify(Holder<Structure> structure, Phase phase, ModifiableStructureInfo.StructureInfo.Builder builder)
        {
            if (phase == Phase.ADD && structure.is(this.structureTagKey))
            {
                builder.getStructureSettings().getOrAddSpawnOverrides(this.spawnerData.type.getCategory()).addSpawn(this.spawnerData);
            }
        }

        @Override
        public MapCodec<? extends StructureModifier> codec()
        {
            return SERIALIZER.get();
        }

        public static MapCodec<Modifier> makeCodec()
        {
            //@formatter:off
            return RecordCodecBuilder.mapCodec(builder -> builder.group(
                            STRUCTURE_LIST_CODEC.fieldOf("structure").forGetter(Modifier::structureTagKey),
                            MobSpawnSettings.SpawnerData.CODEC.fieldOf("spawnerData").forGetter(Modifier::spawnerData))
                    .apply(builder, Modifier::new));
            //@formatter:on
        }
    }
}