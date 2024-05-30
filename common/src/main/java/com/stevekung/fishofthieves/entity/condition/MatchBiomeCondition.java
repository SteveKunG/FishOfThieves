package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

public record MatchBiomeCondition(HolderSet<Biome> biomes) implements SpawnCondition
{
    public static final MapCodec<MatchBiomeCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(MatchBiomeCondition::biomes)).apply(instance, MatchBiomeCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.MATCH_BIOME;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        var holder = context.level().getBiome(context.blockPos());
        return context.registryAccess().registryOrThrow(Registries.BIOME).holders().anyMatch(biomes -> biomes.is(holder));
    }

    public static Builder biomes(HolderSet<Biome> biomes)
    {
        return () -> new MatchBiomeCondition(biomes);
    }
}