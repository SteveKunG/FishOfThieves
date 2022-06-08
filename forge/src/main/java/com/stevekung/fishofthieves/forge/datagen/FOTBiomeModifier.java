package com.stevekung.fishofthieves.forge.datagen;

import java.io.IOException;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.forge.core.FishOfThievesForge;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FOTBiomeModifier implements BiomeModifier
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER = RegistryObject.create(FishOfThievesForge.ADD_THIEVES_FISH_SPAWN_RL, ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, FishOfThieves.MOD_ID);

    public static void generateBiomeModifiers(GatherDataEvent event)
    {
        var dataGenerator = event.getGenerator();
        var biomeModifiersRegistryID = ForgeRegistries.Keys.BIOME_MODIFIERS.location();
        var biomeModifierPathString = String.join("/", PackType.SERVER_DATA.getDirectory(), FishOfThieves.MOD_ID, biomeModifiersRegistryID.getNamespace(), biomeModifiersRegistryID.getPath(), "add_thieves_fish_spawn.json");
        var biomeModifierPath = dataGenerator.getOutputFolder().resolve(biomeModifierPathString);
        var ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get());
        var biomeModifier = new FOTBiomeModifier();

        dataGenerator.addProvider(event.includeServer(), new DataProvider()
        {
            @Override
            public void run(final CachedOutput cache)
            {
                BiomeModifier.DIRECT_CODEC.encodeStart(ops, biomeModifier).resultOrPartial(msg -> LOGGER.error("Failed to encode {}: {}", biomeModifierPathString, msg)).ifPresent(json ->
                {
                    try
                    {
                        DataProvider.saveStable(cache, json, biomeModifierPath);
                    }
                    catch (IOException e)
                    {
                        LOGGER.error("Failed to save " + biomeModifierPathString, e);
                    }
                });
            }

            @Override
            public String getName()
            {
                return "FOTBiomeModifier";
            }
        });
    }

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder)
    {
        var mobSpawnSettings = builder.getMobSpawnSettings();

        if (phase == Phase.MODIFY)
        {
            if (biome.is(BiomeTags.IS_OCEAN))
            {
                mobSpawnSettings.addSpawn(FOTEntities.SPLASHTAIL.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.SPLASHTAIL, 15, 4, 8));
            }
            if (biome.is(BiomeTags.IS_RIVER) || biome.is(BiomeTags.IS_FOREST))
            {
                mobSpawnSettings.addSpawn(FOTEntities.PONDIE.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.PONDIE, 15, 2, 4));
            }
            if (biome.is(BiomeTags.IS_OCEAN) || biome.is(BiomeTags.IS_BEACH) || biome.is(BiomeTags.IS_JUNGLE) || biome.is(BiomeTags.HAS_CLOSER_WATER_FOG) || biome.is(FOTTags.UNDERGROUND))
            {
                mobSpawnSettings.addSpawn(FOTEntities.ISLEHOPPER.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.ISLEHOPPER, 8, 2, 4));
            }
            if (biome.is(Biomes.LUKEWARM_OCEAN) || biome.is(Biomes.DEEP_LUKEWARM_OCEAN))
            {
                mobSpawnSettings.addSpawn(FOTEntities.ANCIENTSCALE.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.ANCIENTSCALE, 8, 4, 8));
            }
            if (biome.is(Biomes.WARM_OCEAN) || biome.is(Biomes.LUKEWARM_OCEAN) || biome.is(Biomes.DEEP_LUKEWARM_OCEAN) || biome.is(FOTTags.UNDERGROUND))
            {
                mobSpawnSettings.addSpawn(FOTEntities.PLENTIFIN.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.PLENTIFIN, 12, 4, 8));
            }
            if (biome.is(Biomes.LUSH_CAVES) || biome.is(Biomes.WARM_OCEAN) || biome.is(BiomeTags.IS_OCEAN) || biome.is(BiomeTags.IS_BEACH) || biome.is(BiomeTags.IS_JUNGLE) || biome.is(BiomeTags.HAS_CLOSER_WATER_FOG))
            {
                mobSpawnSettings.addSpawn(FOTEntities.WILDSPLASH.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.WILDSPLASH, 10, 2, 4));
            }
            if (!biome.is(Biomes.LUSH_CAVES))
            {
                mobSpawnSettings.addSpawn(FOTEntities.DEVILFISH.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.DEVILFISH, 4, 1, 2));
            }

            mobSpawnSettings.addSpawn(FOTEntities.BATTLEGILL.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.BATTLEGILL, 5, 2, 4));

            if (biome.is(BiomeTags.IS_OCEAN))
            {
                mobSpawnSettings.addSpawn(FOTEntities.WRECKER.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.WRECKER, 50, 4, 8));
            }
            if (biome.is(BiomeTags.IS_OCEAN) || biome.is(Biomes.SPARSE_JUNGLE))
            {
                mobSpawnSettings.addSpawn(FOTEntities.STORMFISH.getCategory(), new MobSpawnSettings.SpawnerData(FOTEntities.STORMFISH, 12, 4, 8));
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec()
    {
        return SERIALIZER.get();
    }

    public static Codec<FOTBiomeModifier> makeCodec()
    {
        return RecordCodecBuilder.create(builder -> builder.stable(new FOTBiomeModifier()));
    }
}