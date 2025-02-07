package com.stevekung.fishofthieves.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;

public record SimpleAgeBlockConfiguration(RandomizedIntStateProvider toPlace) implements FeatureConfiguration
{
    public static final Codec<SimpleAgeBlockConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    RandomizedIntStateProvider.CODEC.fieldOf("to_place").forGetter(config -> config.toPlace))
            .apply(instance, SimpleAgeBlockConfiguration::new));
}