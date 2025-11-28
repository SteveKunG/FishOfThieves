package com.stevekung.fishofthieves.mixin.level;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.shoal.ShoalSpawner;
import com.stevekung.fishofthieves.storage.BaitPreserveSavedData;
import com.stevekung.fishofthieves.storage.BaitStorageAccessor;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel extends Level implements BaitStorageAccessor
{
    @Unique
    private BaitPreserveSavedData bait;

    MixinServerLevel()
    {
        super(null, null, null, null, false, false, 0, 0);
    }

    @Inject(method = "tickThunder", cancellable = true, at = @At(value = "INVOKE", target = "net/minecraft/server/level/ServerLevel.isThundering()Z"))
    private void fishofthieves$specialThunderTick(LevelChunk chunk, CallbackInfo info, @Local(index = 4, ordinal = 0) int x, @Local(index = 5, ordinal = 1) int z)
    {
        if (this.isThundering() && this.random.nextInt(5000) == 0)
        {
            var blockPos = this.findNearestStormfish(this.getBlockRandomPos(x, 0, z, 15));

            if (blockPos.isPresent())
            {
                var lightningBolt = EntityType.LIGHTNING_BOLT.create(this, EntitySpawnReason.EVENT);
                lightningBolt.snapTo(Vec3.atBottomCenterOf(blockPos.get()));
                this.addFreshEntity(lightningBolt);
            }
        }
    }

    @Inject(method = "tickChunk", at = @At(value = "CONSTANT", args = "stringValue=iceandsnow"))
    private void fishofthieves$shoalTick(LevelChunk chunk, int randomTickSpeed, CallbackInfo info, @Local(index = 5, ordinal = 1) int x, @Local(index = 6, ordinal = 2) int z, @Local ProfilerFiller profilerFiller)
    {
        if (ServerLevel.class.cast(this).getGameRules().getBoolean(FishOfThieves.SHOAL_SPAWNING))
        {
            profilerFiller.push("fishofthieves_shoal");
            ShoalSpawner.spawn(ServerLevel.class.cast(this), x, z);
            profilerFiller.pop();
        }
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void fishofthieves$initBaitPreserve(CallbackInfo info)
    {
        this.bait = ServerLevel.class.cast(this).getDataStorage().computeIfAbsent(BaitPreserveSavedData.TYPE);
    }

    @Override
    public BaitPreserveSavedData getBaitPreserve()
    {
        return this.bait;
    }

    @Unique
    private boolean isRainingAtFromBelowWater(BlockPos blockPos)
    {
        if (!this.isRaining() || !this.canSeeSkyFromBelowWater(blockPos) || this.getBrightness(LightLayer.SKY, blockPos) < 12)
        {
            return false;
        }
        var biome = this.getBiome(blockPos).value();
        return biome.getPrecipitationAt(blockPos, this.getSeaLevel()) == Biome.Precipitation.RAIN;
    }

    @Unique
    private Optional<BlockPos> findNearestStormfish(BlockPos blockPos)
    {
        var blockPos2 = this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos);
        var aabb = AABB.encapsulatingFullBlocks(blockPos2, new BlockPos(blockPos2.getX(), this.getMaxY(), blockPos2.getZ())).inflate(8.0D);
        return Optional.of(this.getEntities(FOTEntities.STORMFISH, aabb, living -> living != null && living.isAlive() && this.isRainingAtFromBelowWater(blockPos2))).filter(stormfish -> !stormfish.isEmpty()).map(stormfish -> stormfish.get(this.random.nextInt(stormfish.size())).blockPosition());
    }
}