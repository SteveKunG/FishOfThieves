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
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public record WildsplashVariant(Supplier<Predicate<SpawnConditionContext>> condition, ResourceLocation texture, Optional<ResourceLocation> glowTexture) implements FishData
{
    public static final WildsplashVariant RUSSET = create(SpawnSelectors.always(), name("russet"));
    public static final WildsplashVariant SANDY = create(SpawnSelectors.simpleSpawn(SpawnSelectors.biomeTag(FOTTags.SPAWNS_SANDY_WILDSPLASH).and(SpawnSelectors.continentalness(Continentalness.COAST))), name("sandy"));
    public static final WildsplashVariant OCEAN = create(SpawnSelectors.simpleSpawn(SpawnSelectors.biomeTag(FOTTags.SPAWNS_OCEAN_WILDSPLASH)), name("ocean"));
    public static final WildsplashVariant MUDDY = create(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.muddyWildsplashProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.muddyWildsplashProbability).and(SpawnSelectors.biomeTag(FOTTags.SPAWNS_MUDDY_WILDSPLASH))), name("muddy"));
    public static final WildsplashVariant CORAL = new WildsplashVariant(() -> SpawnSelectors.simpleSpawn(true, SpawnSelectors.nightAndSeeSky().and(SpawnSelectors.biomeTag(FOTTags.SPAWNS_CORAL_WILDSPLASH)).and(context -> TerrainUtils.lookForBlocksWithSize(context.blockPos(), 3, 24, blockPos -> context.level().getBlockState(blockPos).is(FOTTags.CORAL_WILDSPLASH_SPAWNABLE_ON)))), name("coral"), Optional.of(new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/wildsplash/coral_glow.png")));

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
        WildsplashVariant.register("russet", RUSSET);
        WildsplashVariant.register("sandy", SANDY);
        WildsplashVariant.register("ocean", OCEAN);
        WildsplashVariant.register("muddy", MUDDY);
        WildsplashVariant.register("coral", CORAL);
    }

    private static void register(String key, WildsplashVariant variant)
    {
        Registry.register(FOTRegistry.WILDSPLASH_VARIANT, new ResourceLocation(FishOfThieves.MOD_ID, key), variant);
    }

    private static WildsplashVariant create(Predicate<SpawnConditionContext> condition, ResourceLocation texture)
    {
        return new WildsplashVariant(() -> condition, texture, Optional.empty());
    }

    private static ResourceLocation name(String variant)
    {
        return new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/wildsplash/%s.png".formatted(variant));
    }
}