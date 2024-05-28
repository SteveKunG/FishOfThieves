package com.stevekung.fishofthieves.common.entity.variant;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.common.FishOfThieves;
import com.stevekung.fishofthieves.common.spawn.SpawnConditionContext;
import net.minecraft.resources.ResourceLocation;

public class IslehopperVariant extends AbstractFishVariant
{
    private IslehopperVariant(Supplier<Predicate<SpawnConditionContext>> condition, ResourceLocation texture, ResourceLocation glowTexture)
    {
        super(condition, texture, glowTexture);
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private Predicate<SpawnConditionContext> condition;
        private ResourceLocation texture;
        @Nullable
        private ResourceLocation glowTexture;

        Builder() {}

        public Builder condition(Predicate<SpawnConditionContext> condition)
        {
            Objects.requireNonNull(condition, "Condition may not be null.");
            this.condition = condition;
            return this;
        }

        public Builder texture(String name)
        {
            Objects.requireNonNull(name, "Name may not be null.");
            this.texture = this.createTexture(name);
            return this;
        }

        public Builder glowTexture(String glowTexture)
        {
            this.glowTexture = this.createTexture(glowTexture);
            return this;
        }

        public IslehopperVariant build()
        {
            return new IslehopperVariant(() -> this.condition, this.texture, this.glowTexture);
        }

        private ResourceLocation createTexture(String name)
        {
            return FishOfThieves.id("textures/entity/islehopper/%s.png".formatted(name));
        }
    }
}