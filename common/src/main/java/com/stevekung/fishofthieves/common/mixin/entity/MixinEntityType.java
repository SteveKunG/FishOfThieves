package com.stevekung.fishofthieves.common.mixin.entity;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import com.stevekung.fishofthieves.common.entity.BucketableEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
    abstract T create(Level level);

    @Override
    @Nullable
    public Entity spawnByBucket(ServerLevel serverLevel, @Nullable ItemStack stack, @Nullable Player player, MobSpawnType spawnType)
    {
        return this.spawnByBucket(serverLevel, stack == null ? null : stack.getTag(), stack != null && stack.hasCustomHoverName() ? stack.getHoverName() : null, player, spawnType);
    }

    @Override
    @Nullable
    public T spawnByBucket(ServerLevel level, @Nullable CompoundTag compound, @Nullable Component customName, @Nullable Player player, MobSpawnType spawnType)
    {
        var entity = this.createByBucket(level, compound, customName, player, spawnType);

        if (entity != null)
        {
            level.addFreshEntityWithPassengers(entity);
        }
        return entity;
    }

    @Override
    @Nullable
    public T createByBucket(ServerLevel level, @Nullable CompoundTag compound, @Nullable Component customName, @Nullable Player player, MobSpawnType spawnType)
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
                mob.finalizeSpawn(level, level.getCurrentDifficultyAt(mob.blockPosition()), spawnType, null, compound);
            }

            if (customName != null && entity instanceof LivingEntity)
            {
                entity.setCustomName(customName);
            }

            EntityType.updateCustomEntityTag(level, player, entity, compound);
            return entity;
        }
    }
}