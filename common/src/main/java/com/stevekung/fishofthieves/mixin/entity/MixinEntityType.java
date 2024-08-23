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
import net.minecraft.world.entity.EntitySpawnReason;
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
    public T spawnByBucket(ServerLevel serverLevel, @Nullable ItemStack stack, @Nullable Player player, EntitySpawnReason entitySpawnReason)
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
        return this.spawnByBucket(serverLevel, consumer, entitySpawnReason);
    }

    @Override
    @Nullable
    public T spawnByBucket(ServerLevel level, @Nullable Consumer<T> consumer, EntitySpawnReason entitySpawnReason)
    {
        var entity = this.createByBucket(level, consumer, entitySpawnReason);

        if (entity != null)
        {
            level.addFreshEntityWithPassengers(entity);
        }
        return entity;
    }

    @Override
    @Nullable
    public T createByBucket(ServerLevel level, @Nullable Consumer<T> consumer, EntitySpawnReason entitySpawnReason)
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

            if (consumer != null)
            {
                consumer.accept(entity);
            }
            return entity;
        }
    }
}