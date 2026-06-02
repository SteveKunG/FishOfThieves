package com.stevekung.fishofthieves.mixin.entity;

import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.stevekung.fishofthieves.entity.BucketableEntityType;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(EntityType.class)
public abstract class MixinEntityType<T extends Entity> implements BucketableEntityType<T>
{
    @Shadow
    @Nullable
    abstract T create(Level level, EntitySpawnReason entitySpawnReason);

    @Override
    @Nullable
    public T fishofthieves$spawnByBucket(ServerLevel serverLevel, @Nullable ItemStack stack, @Nullable Player player, EntitySpawnReason entitySpawnReason)
    {
        PostSpawnProcessor<T> postSpawnConfig;

        if (stack != null)
        {
            postSpawnConfig = EntityType.createDefaultStackConfig(serverLevel, stack, player);
        }
        else
        {
            postSpawnConfig = PostSpawnProcessor.nop();
        }
        return this.fishofthieves$spawnByBucket(serverLevel, postSpawnConfig, entitySpawnReason);
    }

    @Override
    @Nullable
    public T fishofthieves$spawnByBucket(ServerLevel level, @Nullable PostSpawnProcessor<T> postSpawnConfig, EntitySpawnReason entitySpawnReason)
    {
        var entity = this.fishofthieves$createByBucket(level, postSpawnConfig, entitySpawnReason);

        if (entity != null)
        {
            level.addFreshEntityWithPassengers(entity);
        }
        return entity;
    }

    @Override
    @Nullable
    public T fishofthieves$createByBucket(ServerLevel level, @Nullable PostSpawnProcessor<T> postSpawnConfig, EntitySpawnReason entitySpawnReason)
    {
        var entity = this.create(level, entitySpawnReason);

        if (entity == null)
        {
            return null;
        }
        else
        {
            if (entity instanceof Mob mob)
            {
                mob.finalizeSpawn(level, level.getCurrentDifficultyAt(mob.blockPosition()), entitySpawnReason, null);
            }

            if (postSpawnConfig != null)
            {
                postSpawnConfig.apply(entity);
            }
            return entity;
        }
    }
}