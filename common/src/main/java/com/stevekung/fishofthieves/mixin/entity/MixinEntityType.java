package com.stevekung.fishofthieves.mixin.entity;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.stevekung.fishofthieves.entity.BucketableEntityType;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(EntityType.class)
public abstract class MixinEntityType<T extends Entity> implements BucketableEntityType<T>
{
    @Shadow
    @Nullable
    abstract T create(Level level);

    @Override
    @Nullable
    public T fishofthieves$spawnByBucket(ServerLevel serverLevel, @Nullable ItemStack stack, @Nullable Player player, MobSpawnType spawnType)
    {
        Consumer<T> consumer;

        if (stack != null)
        {
            consumer = EntityType.createDefaultStackConfig(serverLevel, stack, player);
        }
        else
        {
            consumer = entity ->
            {
            };
        }
        return this.fishofthieves$spawnByBucket(serverLevel, consumer, spawnType);
    }

    @Override
    @Nullable
    public T fishofthieves$spawnByBucket(ServerLevel level, @Nullable Consumer<T> consumer, MobSpawnType spawnType)
    {
        var entity = this.fishofthieves$createByBucket(level, consumer, spawnType);

        if (entity != null)
        {
            level.addFreshEntityWithPassengers(entity);
        }
        return entity;
    }

    @Override
    @Nullable
    public T fishofthieves$createByBucket(ServerLevel level, @Nullable Consumer<T> consumer, MobSpawnType spawnType)
    {
        var entity = this.create(level);

        if (entity == null)
        {
            return null;
        }
        else
        {
            if (entity instanceof Mob mob)
            {
                mob.finalizeSpawn(level, level.getCurrentDifficultyAt(mob.blockPosition()), spawnType, null);
            }

            if (consumer != null)
            {
                consumer.accept(entity);
            }
            return entity;
        }
    }
}