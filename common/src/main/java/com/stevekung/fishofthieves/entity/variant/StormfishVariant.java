package com.stevekung.fishofthieves.entity.variant;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.spawn.SpawnConditionContext;
import net.minecraft.resources.ResourceLocation;

public class StormfishVariant extends AbstractFishVariant
{
    private StormfishVariant(Supplier<Predicate<SpawnConditionContext>> condition, ResourceLocation texture, ResourceLocation glowTexture)
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

        public StormfishVariant build()
        {
            return new StormfishVariant(() -> this.condition, this.texture, this.glowTexture);
        }

        private ResourceLocation createTexture(String name)
        {
            return FishOfThieves.res("textures/entity/stormfish/%s.png".formatted(name));
        }
    }
}