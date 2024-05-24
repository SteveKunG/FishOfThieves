package com.stevekung.fishofthieves.registry.variant.muha.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;

public record MatchBiomeCondition(HolderSet<Biome> biomes) implements SpawnCondition
{
    public static final MapCodec<MatchBiomeCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(MatchBiomeCondition::biomes)).apply(instance, MatchBiomeCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return SpawnConditions.MATCH_BIOME;
    }

    @Override
    public boolean test(LivingEntity livingEntity)
    {
        var holder = livingEntity.level().getBiome(livingEntity.blockPosition());
        return livingEntity.registryAccess().registryOrThrow(Registries.BIOME).holders().anyMatch(biomes -> biomes.is(holder));
    }

    public static Builder hasValue(HolderSet<Biome> biomes)
    {
        return () -> new MatchBiomeCondition(biomes);
    }
}