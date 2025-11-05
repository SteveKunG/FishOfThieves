package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record BooleanEnvironmentAttributeCheck(EnvironmentAttribute<?> attribute) implements SpawnCondition
{
    public static final MapCodec<BooleanEnvironmentAttributeCheck> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(
                    EnvironmentAttributes.CODEC.fieldOf("attribute")
                            .validate(attribute1 -> attribute1.defaultValue() instanceof Boolean ? DataResult.success(attribute1) : DataResult.error(() -> "Environment attribute value is not a boolean type"))
                            .forGetter(BooleanEnvironmentAttributeCheck::attribute)
            ).apply(instance, BooleanEnvironmentAttributeCheck::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        return (boolean) context.environmentAttributes().getValue(this.attribute, context.pos());
    }

    public static SpawnCondition attribute(EnvironmentAttribute<?> attribute)
    {
        return new BooleanEnvironmentAttributeCheck(attribute);
    }
}