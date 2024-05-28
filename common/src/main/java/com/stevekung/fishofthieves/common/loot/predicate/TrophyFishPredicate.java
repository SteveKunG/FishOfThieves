package com.stevekung.fishofthieves.common.loot.predicate;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.common.entity.ThievesFish;
import com.stevekung.fishofthieves.common.registry.FOTEntitySubPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public record TrophyFishPredicate(Optional<Boolean> trophy) implements EntitySubPredicate
{
    public static final TrophyFishPredicate ANY = new TrophyFishPredicate(Optional.empty());
    public static final MapCodec<TrophyFishPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(ExtraCodecs.strictOptionalField(Codec.BOOL, "trophy").forGetter(TrophyFishPredicate::trophy)).apply(instance, TrophyFishPredicate::new));

    public static TrophyFishPredicate trophy(boolean trophy)
    {
        return new TrophyFishPredicate(Optional.of(trophy));
    }

    @Override
    public EntitySubPredicate.Type type()
    {
        return FOTEntitySubPredicate.TROPHY;
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 vec3)
    {
        if (this == ANY)
        {
            return true;
        }
        else if (!(entity instanceof ThievesFish<?> thievesFish))
        {
            return false;
        }
        else
        {
            return this.trophy.get() == thievesFish.isTrophy();
        }
    }
}