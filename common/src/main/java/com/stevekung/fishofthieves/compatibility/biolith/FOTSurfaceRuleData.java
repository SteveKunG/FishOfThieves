package com.stevekung.fishofthieves.compatibility.biolith;

import com.stevekung.fishofthieves.feature.surfacerules.BlockStateConditionSource;
import com.stevekung.fishofthieves.feature.surfacerules.WaterSurroundedConditionSource;
import com.stevekung.fishofthieves.registry.FOTBiomes;
import com.stevekung.fishofthieves.registry.FOTNoises;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.material.VanillaMaterialConditions;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class FOTSurfaceRuleData
{
    private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
    private static final SurfaceRules.RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);

    public static SurfaceRules.RuleSource overworld(HolderGetter<SurfaceRules.ConditionSource> conditionSource, HolderGetter<Biome> biomes)
    {
        return SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), makeRules(conditionSource, biomes)));
    }

    private static SurfaceRules.RuleSource makeRules(HolderGetter<SurfaceRules.ConditionSource> conditionSource, HolderGetter<Biome> biomes)
    {
        var waterAboveCheck = SurfaceRules.waterBlockCheck(1, 0);
        var y62 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(62), 0);
        var airAboveCheck = blockStateCheck(Blocks.AIR, 1);
        var sixBelowWater = SurfaceRules.waterStartCheck(-6, -1);
        var sandWithSandstone = SurfaceRules.sequence(SurfaceRules.ifTrue(conditionSource.getOrThrow(VanillaMaterialConditions.ON_CEILING).value(), SANDSTONE), SAND);

        var surfaceBelow64 = SurfaceRules.not(
                SurfaceRules.yStartCheck(
                        VerticalAnchor.absolute(64), 0));

        return SurfaceRules.sequence(

                SurfaceRules.ifTrue(
                        sixBelowWater,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        conditionSource.getOrThrow(VanillaMaterialConditions.UNDER_FLOOR).value(),
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(SurfaceRules.isBiome(biomes, FOTBiomes.TROPICAL_ISLAND),
                                                        SurfaceRules.sequence(
                                                                SurfaceRules.ifTrue(surfaceBelow64,
                                                                        SurfaceRules.sequence(
                                                                                SurfaceRules.ifTrue(SurfaceRules.not(waterAboveCheck), sandWithSandstone),

                                                                                SurfaceRules.ifTrue(y62, SurfaceRules.ifTrue(waterSurrounded(), sandWithSandstone))
                                                                        ))
                                                        )
                                                )
                                        ))
                        )
                ),

                SurfaceRules.ifTrue(
                        conditionSource.getOrThrow(VanillaMaterialConditions.ON_FLOOR).value(),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.isBiome(biomes, FOTBiomes.TROPICAL_ISLAND),
                                        SurfaceRules.ifTrue(airAboveCheck, SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.noiseCondition2d(FOTNoises.SAND_PATCHES, -0.6, -0.45), SAND),

                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.noiseCondition2d(FOTNoises.SAND_PATCHES, 0.1, 0.2), SAND)
                                        )))
                        )
                )

        );
    }

    private static SurfaceRules.ConditionSource waterSurrounded()
    {
        return new WaterSurroundedConditionSource();
    }

    private static SurfaceRules.ConditionSource blockStateCheck(Block block, int offset)
    {
        return new BlockStateConditionSource(block.defaultBlockState(), offset);
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block)
    {
        return SurfaceRules.state(block.defaultBlockState());
    }
}