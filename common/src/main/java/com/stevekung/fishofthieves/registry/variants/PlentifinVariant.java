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

public record PlentifinVariant(Supplier<Predicate<SpawnConditionContext>> condition, ResourceLocation texture, Optional<ResourceLocation> glowTexture) implements FishData
{
    public static final PlentifinVariant OLIVE = create(SpawnSelectors.always(), name("olive"));
    public static final PlentifinVariant AMBER = create(SpawnSelectors.simpleSpawn(Predicate.not(SpawnSelectors.rainingAndSeeSky()).and(context ->
    {
        var time = context.level().getTimeOfDay(1.0F);
        return time >= 0.75F && time <= 0.9F;
    }).and(SpawnConditionContext::seeSkyInWater)), name("amber"));
    public static final PlentifinVariant CLOUDY = create(SpawnSelectors.simpleSpawn(SpawnSelectors.rainingAndSeeSky()), name("cloudy"));
    public static final PlentifinVariant BONEDUST = create(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.bonedustPlentifinProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.bonedustPlentifinProbability).or(SpawnSelectors.structureTag(FOTTags.BONEDUST_PLENTIFINS_SPAWN_IN).and(context -> context.random().nextInt(10) == 0))), name("bonedust"));
    public static final PlentifinVariant WATERY = new PlentifinVariant(SpawnSelectors::nightAndSeeSky, name("watery"), Optional.of(new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/plentifin/watery_glow.png")));

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
        PlentifinVariant.register("olive", OLIVE);
        PlentifinVariant.register("amber", AMBER);
        PlentifinVariant.register("cloudy", CLOUDY);
        PlentifinVariant.register("bonedust", BONEDUST);
        PlentifinVariant.register("watery", WATERY);
    }

    private static void register(String key, PlentifinVariant variant)
    {
        Registry.register(FOTRegistry.PLENTIFIN_VARIANT, new ResourceLocation(FishOfThieves.MOD_ID, key), variant);
    }

    private static PlentifinVariant create(Predicate<SpawnConditionContext> condition, ResourceLocation texture)
    {
        return new PlentifinVariant(() -> condition, texture, Optional.empty());
    }

    private static ResourceLocation name(String variant)
    {
        return new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/plentifin/%s.png".formatted(variant));
    }
}