package com.stevekung.fishofthieves.loot.predicate;

import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.structure.Structure;

public record StructureRangeCondition(TagKey<Structure> structure, ConstantInt range, ConstantInt chunkRadius)
{}