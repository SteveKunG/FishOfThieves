package com.stevekung.fishofthieves.loot.predicate;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTEntitySubPredicates;

import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public record TreasuredFishPredicate(boolean treasured) implements EntitySubPredicate
{
    public static final MapCodec<TreasuredFishPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.BOOL.fieldOf("treasured").forGetter(TreasuredFishPredicate::treasured)).apply(instance, TreasuredFishPredicate::new));

    @Override
    public MapCodec<TreasuredFishPredicate> codec()
    {
        return FOTEntitySubPredicates.TREASURED;
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position)
    {
        if (!(entity instanceof ThievesFish<?> thievesFish))
        {
            return false;
        }
        else
        {
            return this.treasured == thievesFish.isTreasured();
        }
    }
}