package com.stevekung.fishofthieves.registry.variants;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.FishData;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.spawn.SpawnConditionContext;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LightLayer;

public record StormfishVariant(Supplier<Predicate<SpawnConditionContext>> condition, ResourceLocation texture, Optional<ResourceLocation> glowTexture) implements FishData
{
    public static final StormfishVariant ANCIENT = create(SpawnSelectors.always(), name("ancient"));
    public static final StormfishVariant SHORES = create(SpawnSelectors.simpleSpawn(SpawnSelectors.continentalness(Continentalness.COAST)), name("shores"));
    public static final StormfishVariant WILD = create(SpawnSelectors.simpleSpawn(SpawnSelectors.biomeTag(FOTTags.SPAWNS_WILD_STORMFISH)), name("wild"));
    public static final StormfishVariant SHADOW = create(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.shadowStormfishProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.shadowStormfishProbability).and(context -> context.level().getBrightness(LightLayer.SKY, context.blockPos()) <= 4)), name("shadow"));
    public static final StormfishVariant TWILIGHT = new StormfishVariant(() -> SpawnSelectors.simpleSpawn(true, context -> context.level().getSkyDarken() >= 9), name("twilight"), Optional.of(new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/stormfish/twilight_glow.png")));

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
        StormfishVariant.register("ancient", ANCIENT);
        StormfishVariant.register("shores", SHORES);
        StormfishVariant.register("wild", WILD);
        StormfishVariant.register("shadow", SHADOW);
        StormfishVariant.register("twilight", TWILIGHT);
    }

    private static void register(String key, StormfishVariant variant)
    {
        Registry.register(FOTRegistry.STORMFISH_VARIANT, new ResourceLocation(FishOfThieves.MOD_ID, key), variant);
    }

    private static StormfishVariant create(Predicate<SpawnConditionContext> condition, ResourceLocation texture)
    {
        return new StormfishVariant(() -> condition, texture, Optional.empty());
    }

    private static ResourceLocation name(String variant)
    {
        return new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/stormfish/%s.png".formatted(variant));
    }
}