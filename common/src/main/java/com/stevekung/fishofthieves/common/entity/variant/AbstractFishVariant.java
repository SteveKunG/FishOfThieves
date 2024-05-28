package com.stevekung.fishofthieves.common.entity.variant;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.common.entity.FishData;
import com.stevekung.fishofthieves.common.spawn.SpawnConditionContext;
import net.minecraft.resources.ResourceLocation;

public class AbstractFishVariant implements FishData
{
    private final Supplier<Predicate<SpawnConditionContext>> condition;
    private final ResourceLocation texture;
    @Nullable
    private final ResourceLocation glowTexture;

    protected AbstractFishVariant(Supplier<Predicate<SpawnConditionContext>> condition, ResourceLocation texture, ResourceLocation glowTexture)
    {
        this.condition = condition;
        this.texture = texture;
        this.glowTexture = glowTexture;
    }

    @Override
    public Predicate<SpawnConditionContext> getCondition()
    {
        return this.condition.get();
    }

    @Override
    public ResourceLocation getTexture()
    {
        return this.texture;
    }

    @Override
    public Optional<ResourceLocation> getGlowTexture()
    {
        return Optional.ofNullable(this.glowTexture);
    }
}