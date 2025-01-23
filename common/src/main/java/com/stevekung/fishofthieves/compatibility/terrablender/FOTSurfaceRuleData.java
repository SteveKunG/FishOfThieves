package com.stevekung.fishofthieves.compatibility.terrablender;

import com.stevekung.fishofthieves.registry.FOTBiomes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class FOTSurfaceRuleData
{
    private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
    private static final SurfaceRules.RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);

    public static SurfaceRules.RuleSource overworld()
    {
        var oceanGravelCheck = SurfaceRules.waterStartCheck(-6, -1);
        var sandWithSandstone = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SANDSTONE), SAND);

        var underSurfaceNoTop = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(FOTBiomes.TROPICAL_ISLAND), SurfaceRules.ifTrue(
                        SurfaceRules.not(
                                SurfaceRules.yStartCheck(
                                        VerticalAnchor.absolute(64), 0)), sandWithSandstone))
        );

        var buildSurface = SurfaceRules.sequence(
                SurfaceRules.ifTrue(oceanGravelCheck, SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, underSurfaceNoTop)
                        )
                ));

        var surfaceBlocks = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), buildSurface);
        return SurfaceRules.sequence(surfaceBlocks);
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block)
    {
        return SurfaceRules.state(block.defaultBlockState());
    }
}