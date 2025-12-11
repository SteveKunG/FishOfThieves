package com.stevekung.fishofthieves.utils;

import com.stevekung.fishofthieves.client.AngledLeavesComponent;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ParticleUtils
{
    /**
     * Huge thanks to <a href="https://github.com/RottenKiwi2004">RottenKiwi</a> with help to understand "basic" math concept for spawning water dripping particles.
     *
     * @param level          world level
     * @param direction      block direction
     * @param pos            block position
     * @param random         random
     * @param uniformInt     random particle count
     * @param yOffset        Y offset to spawn
     * @param particleSpread spread radius
     * @param flipAxis       flip axis
     * @param flipY          flip Y
     * @param component      leaves block model data component
     */
    public static void spawnDrippingWaterParticlesForLeaves(Level level, Direction direction, BlockPos pos, RandomSource random, UniformInt uniformInt, double yOffset, int particleSpread, boolean flipAxis, boolean flipY, AngledLeavesComponent component)
    {
        var vec3 = Vec3.atCenterOf(pos);
        var isAxisX = direction.getAxis() == Direction.Axis.X;
        var isAxisZ = direction.getAxis() == Direction.Axis.X;
        var isPositiveDir = direction.getAxisDirection() == Direction.AxisDirection.POSITIVE;
        var count = uniformInt.sample(random);

        for (var i = 0; i < count; i++)
        {
            var leavesModelAngle = component.leavesModelAngle();
            var leavesDistance = component.leavesDistance();
            var leavesAngle = component.leavesAngle();
            var tan = Math.tan(leavesModelAngle * Math.PI / 180.0f);
            var distance = Math.pow(Mth.nextDouble(random, 0, 1), particleSpread); // Use Math.pow to spread water drop particles
            var leavesArea = isPositiveDir ^ flipAxis ? leavesDistance : -leavesDistance;

            // (distance - 0.5d) = offset particles position from center pos
            // (isPositiveDir ? 1 : -1) = flip adjacent
            // (isAxisX ? 1 : 0) = stop multiplying to z-axis
            // (isAxisZ ? 0 : 1) = stop multiplying to x-axis
            var x = vec3.x + (distance - 0.5d) * leavesArea * (isAxisX ? 1 : 0)
                    + Math.cos(leavesAngle / 180d * Math.PI) * (1 - distance) * (isAxisZ ? 0 : 1) * (random.nextFloat() - 0.5);
            var y = vec3.y + yOffset + distance * (flipAxis ^ flipY ? -tan : tan);
            var z = vec3.z + (distance - 0.5d) * leavesArea * (isAxisZ ? 0 : 1)
                    + Math.cos(leavesAngle / 180d * Math.PI) * (1 - distance) * (isAxisX ? 1 : 0) * (random.nextFloat() - 0.5);

            level.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0, 0, 0);
        }
    }
}