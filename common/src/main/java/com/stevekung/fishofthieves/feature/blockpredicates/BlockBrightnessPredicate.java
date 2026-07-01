package com.stevekung.fishofthieves.feature.blockpredicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTBlockPredicateTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;

public record BlockBrightnessPredicate(int rawBrightness) implements BlockPredicate
{
    public static final MapCodec<BlockBrightnessPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.intRange(0, 15).fieldOf("raw_brightness").forGetter(predicate -> predicate.rawBrightness))
            .apply(instance, BlockBrightnessPredicate::new));

    public static BlockBrightnessPredicate value(int rawBrightness)
    {
        return new BlockBrightnessPredicate(rawBrightness);
    }

    @Override
    public boolean test(LevelAccessor level, BlockPos blockPos)
    {
        return level.getRawBrightness(blockPos, 0) >= this.rawBrightness;
    }

    @Override
    public BlockPredicateType<?> type()
    {
        return FOTBlockPredicateTypes.BLOCK_BRIGHTNESS;
    }
}
