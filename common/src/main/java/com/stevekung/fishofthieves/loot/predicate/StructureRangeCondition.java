package com.stevekung.fishofthieves.loot.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.structure.Structure;

public record StructureRangeCondition(HolderSet<Structure> structures, ConstantInt range, ConstantInt chunkRadius)
{
    public static final Codec<StructureRangeCondition> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    RegistryCodecs.homogeneousList(Registries.STRUCTURE).fieldOf("structures").forGetter(StructureRangeCondition::structures),
                    ConstantInt.CODEC.fieldOf("range").forGetter(StructureRangeCondition::range),
                    ConstantInt.CODEC.fieldOf("chunk_radius").forGetter(StructureRangeCondition::chunkRadius)
            )
            .apply(instance, StructureRangeCondition::new));
}