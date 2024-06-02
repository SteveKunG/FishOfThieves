package com.stevekung.fishofthieves.loot.predicate;

import org.jetbrains.annotations.Nullable;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.entity.ThievesFish;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public record TrophyFishPredicate(boolean trophy) implements EntitySubPredicate
{
    public static final MapCodec<TrophyFishPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.BOOL.optionalFieldOf("trophy", false).forGetter(TrophyFishPredicate::trophy)).apply(instance, TrophyFishPredicate::new));

    @Override
    public MapCodec<? extends EntitySubPredicate> codec()
    {
        return CODEC;
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
            return this.trophy == thievesFish.isTrophy();
        }
    }
}