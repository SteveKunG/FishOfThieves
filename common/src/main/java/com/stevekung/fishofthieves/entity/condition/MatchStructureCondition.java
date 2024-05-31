package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public record MatchStructureCondition(TagKey<Structure> structures) implements SpawnCondition
{
    public static final MapCodec<MatchStructureCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(TagKey.codec(Registries.STRUCTURE).fieldOf("structures").forGetter(MatchStructureCondition::structures)).apply(instance, MatchStructureCondition::new));

    @Override
    public SpawnConditionType getType()
    {
        return FOTSpawnConditions.MATCH_STRUCTURE;
    }

    @Override
    public boolean test(SpawnConditionContext context)
    {
        return context.level().structureManager().getStructureWithPieceAt(context.blockPos(), this.structures).isValid();
    }

    public static Builder structures(TagKey<Structure> structures)
    {
        return () -> new MatchStructureCondition(structures);
    }
}