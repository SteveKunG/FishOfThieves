package com.stevekung.fishofthieves.registry.variants;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.FishData;
import com.stevekung.fishofthieves.spawn.SpawnConditionContext;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import com.stevekung.fishofthieves.utils.FOTPlatform;
import net.minecraft.resources.ResourceLocation;

public record PondieVariant(Supplier<Predicate<SpawnConditionContext>> condition, ResourceLocation texture, Optional<ResourceLocation> glowTexture) implements FishData
{
    public static final PondieVariant CHARCOAL = create(SpawnSelectors.always(), name("charcoal"));
    public static final PondieVariant ORCHID = create(SpawnSelectors.always(), name("orchid"));
    public static final PondieVariant BRONZE = create(SpawnSelectors.always(), name("bronze"));
    public static final PondieVariant BRIGHT = create(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.brightPondieProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.brightPondieProbability).and(SpawnSelectors.dayAndSeeSky())), name("bright"));
    public static final PondieVariant MOONSKY = new PondieVariant(SpawnSelectors::nightAndSeeSky, name("moonsky"), Optional.of(new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/pondie/moonsky_glow.png")));

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
        return this.glowTexture;
    }

    public static void init()
    {
        PondieVariant.register("charcoal", CHARCOAL);
        PondieVariant.register("orchid", ORCHID);
        PondieVariant.register("bronze", BRONZE);
        PondieVariant.register("bright", BRIGHT);
        PondieVariant.register("moonsky", MOONSKY);
    }

    private static void register(String key, PondieVariant variant)
    {
        FOTPlatform.registerPondieVariant(key, variant);
    }

    private static PondieVariant create(Predicate<SpawnConditionContext> condition, ResourceLocation texture)
    {
        return new PondieVariant(() -> condition, texture, Optional.empty());
    }

    private static ResourceLocation name(String variant)
    {
        return new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/pondie/%s.png".formatted(variant));
    }
}