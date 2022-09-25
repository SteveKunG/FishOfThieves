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
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public record WreckerVariant(Supplier<Predicate<SpawnConditionContext>> condition, ResourceLocation texture, Optional<ResourceLocation> glowTexture) implements FishData
{
    public static final WreckerVariant ROSE = create(SpawnSelectors.always(), name("rose"));
    public static final WreckerVariant SUN = new WreckerVariant(() -> SpawnSelectors.simpleSpawn(SpawnSelectors.dayAndSeeSky()), name("sun"), Optional.of(new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/wrecker/sun_glow.png")));
    public static final WreckerVariant BLACKCLOUD = new WreckerVariant(SpawnSelectors::thunderingAndSeeSky, name("blackcloud"), Optional.of(new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/wrecker/blackcloud_glow.png")));
    public static final WreckerVariant SNOW = new WreckerVariant(() -> SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.snowWreckerProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.snowWreckerProbability).and(SpawnSelectors.biomeTag(FOTTags.SPAWNS_SNOW_WRECKERS))), name("snow"), Optional.of(new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/wrecker/snow_glow.png")));
    public static final WreckerVariant MOON = new WreckerVariant(() -> SpawnSelectors.simpleSpawn(true, SpawnSelectors.nightAndSeeSky().and(context -> context.level().getMoonBrightness() > 0F)), name("moon"), Optional.of(new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/wrecker/moon_glow.png")));

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
        WreckerVariant.register("rose", ROSE);
        WreckerVariant.register("sun", SUN);
        WreckerVariant.register("blackcloud", BLACKCLOUD);
        WreckerVariant.register("snow", SNOW);
        WreckerVariant.register("moon", MOON);
    }

    private static void register(String key, WreckerVariant variant)
    {
        Registry.register(FOTRegistry.WRECKER_VARIANT, new ResourceLocation(FishOfThieves.MOD_ID, key), variant);
    }

    private static WreckerVariant create(Predicate<SpawnConditionContext> condition, ResourceLocation texture)
    {
        return new WreckerVariant(() -> condition, texture, Optional.empty());
    }

    private static ResourceLocation name(String variant)
    {
        return new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/wrecker/%s.png".formatted(variant));
    }
}