package com.stevekung.fishofthieves.mixin.level;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.stevekung.fishofthieves.entity.animal.Stormfish;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel extends Level
{
    MixinServerLevel()
    {
        super(null, null, null, null, false, false, 0);
    }

    @Inject(method = "tickChunk", cancellable = true, at = @At(value = "INVOKE", target = "net/minecraft/server/level/ServerLevel.isThundering()Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void fishofthieves$specialThunderTick(LevelChunk chunk, int randomTickSpeed, CallbackInfo info, ChunkPos chunkPos, boolean isRaining, int x, int z, ProfilerFiller profilerFiller)
    {
        var blockPos = this.findNearestStormfish(this.getBlockRandomPos(x, 0, z, 15));

        if (blockPos != null && isRaining && this.isThundering() && this.random.nextInt(5000) == 0 && this.isRainingAtFromBelowWater(blockPos))
        {
            var lightningBolt = EntityType.LIGHTNING_BOLT.create(this);
            lightningBolt.moveTo(Vec3.atBottomCenterOf(blockPos));
            this.addFreshEntity(lightningBolt);
        }
    }

    private boolean isRainingAtFromBelowWater(BlockPos blockPos)
    {
        if (!this.isRaining() || !this.canSeeSkyFromBelowWater(blockPos) || this.getBrightness(LightLayer.SKY, blockPos) < 12)
        {
            return false;
        }
        var biome = this.getBiome(blockPos);
        return biome.getPrecipitation() == Biome.Precipitation.RAIN && biome.warmEnoughToRain(blockPos);
    }

    @Nullable
    private BlockPos findNearestStormfish(BlockPos blockPos)
    {
        var blockPos2 = this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos);
        var aabb = new AABB(blockPos2, new BlockPos(blockPos2.getX(), this.getMaxBuildHeight(), blockPos2.getZ())).inflate(16.0D);
        var stormfishes = this.getEntitiesOfClass(Stormfish.class, aabb, living -> living != null && living.isAlive() && this.canSeeSkyFromBelowWater(blockPos2));

        if (!stormfishes.isEmpty())
        {
            return stormfishes.get(this.random.nextInt(stormfishes.size())).blockPosition();
        }
        return null;
    }
}