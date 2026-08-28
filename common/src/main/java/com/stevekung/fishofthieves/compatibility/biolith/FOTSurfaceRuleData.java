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
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.material.MaterialRules;
import net.minecraft.world.level.levelgen.material.condition.MaterialCondition;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;

public class FOTSurfaceRuleData
{
    private static final MaterialRule SAND = makeStateRule(Blocks.SAND);
    private static final MaterialRule SANDSTONE = makeStateRule(Blocks.SANDSTONE);

    private FOTSurfaceRuleData() {}

    public static MaterialRule overworld(HolderGetter<MaterialCondition> conditionSource, HolderGetter<Biome> biomes)
    {
        return MaterialRules.sequence(MaterialRules.ifTrue(MaterialRules.abovePreliminarySurface(), makeRules(conditionSource, biomes)));
    }

    private static MaterialRule makeRules(HolderGetter<MaterialCondition> conditionSource, HolderGetter<Biome> biomes)
    {
        var waterAboveCheck = MaterialRules.waterBlockCheck(1, 0);
        var y62 = MaterialRules.yBlockCheck(VerticalAnchor.absolute(62), 0);
        var airAboveCheck = blockStateCheck(Blocks.AIR, 1);
        var sixBelowWater = MaterialRules.waterStartCheck(-6, -1);
        var sandWithSandstone = MaterialRules.sequence(MaterialRules.ifTrue(conditionSource.getOrThrow(VanillaMaterialConditions.ON_CEILING).value(), SANDSTONE), SAND);

        var surfaceBelow64 = MaterialRules.not(
                MaterialRules.yStartCheck(
                        VerticalAnchor.absolute(64), 0));

        return MaterialRules.sequence(

                MaterialRules.ifTrue(
                        sixBelowWater,
                        MaterialRules.sequence(
                                MaterialRules.ifTrue(
                                        conditionSource.getOrThrow(VanillaMaterialConditions.UNDER_FLOOR).value(),
                                        MaterialRules.sequence(
                                                MaterialRules.ifTrue(MaterialRules.isBiome(biomes, FOTBiomes.TROPICAL_ISLAND),
                                                        MaterialRules.sequence(
                                                                MaterialRules.ifTrue(surfaceBelow64,
                                                                        MaterialRules.sequence(
                                                                                MaterialRules.ifTrue(MaterialRules.not(waterAboveCheck), sandWithSandstone),

                                                                                MaterialRules.ifTrue(y62, MaterialRules.ifTrue(waterSurrounded(), sandWithSandstone))
                                                                        ))
                                                        )
                                                )
                                        ))
                        )
                ),

                MaterialRules.ifTrue(
                        conditionSource.getOrThrow(VanillaMaterialConditions.ON_FLOOR).value(),
                        MaterialRules.sequence(
                                MaterialRules.ifTrue(MaterialRules.isBiome(biomes, FOTBiomes.TROPICAL_ISLAND),
                                        MaterialRules.ifTrue(airAboveCheck, MaterialRules.sequence(
                                                MaterialRules.ifTrue(
                                                        MaterialRules.noiseCondition2d(FOTNoises.SAND_PATCHES, -0.6, -0.45), SAND),

                                                MaterialRules.ifTrue(
                                                        MaterialRules.noiseCondition2d(FOTNoises.SAND_PATCHES, 0.1, 0.2), SAND)
                                        )))
                        )
                )

        );
    }

    private static MaterialCondition waterSurrounded()
    {
        return new WaterSurroundedConditionSource();
    }

    private static MaterialCondition blockStateCheck(Block block, int offset)
    {
        return new BlockStateConditionSource(block.defaultBlockState(), offset);
    }

    private static MaterialRule makeStateRule(Block block)
    {
        return MaterialRules.state(block.defaultBlockState());
    }
}