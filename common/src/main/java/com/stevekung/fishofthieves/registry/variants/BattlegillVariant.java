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

public record BattlegillVariant(Supplier<Predicate<SpawnConditionContext>> condition, ResourceLocation texture, Optional<ResourceLocation> glowTexture) implements FishData
{
    public static final BattlegillVariant JADE = create(SpawnSelectors.always(), name("jade"));
    public static final BattlegillVariant SKY = create(SpawnSelectors.simpleSpawn(SpawnConditionContext::seeSkyInWater), name("sky"));
    public static final BattlegillVariant RUM = create(SpawnSelectors.always(), name("rum"));
    public static final BattlegillVariant SAND = create(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.sandBattlegillProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.sandBattlegillProbability).and(SpawnSelectors.biomes(FOTTags.SPAWNS_SAND_BATTLEGILLS))), name("sand"));
    public static final BattlegillVariant BITTERSWEET = new BattlegillVariant(SpawnSelectors::nightAndSeeSky, name("bittersweet"), Optional.of(new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/battlegill/bittersweet_glow.png")));

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
        BattlegillVariant.register("jade", JADE);
        BattlegillVariant.register("sky", SKY);
        BattlegillVariant.register("rum", RUM);
        BattlegillVariant.register("sand", SAND);
        BattlegillVariant.register("bittersweet", BITTERSWEET);
    }

    private static void register(String key, BattlegillVariant variant)
    {
        Registry.register(FOTRegistry.BATTLEGILL_VARIANT, new ResourceLocation(FishOfThieves.MOD_ID, key), variant);
    }

    private static BattlegillVariant create(Predicate<SpawnConditionContext> condition, ResourceLocation texture)
    {
        return new BattlegillVariant(() -> condition, texture, Optional.empty());
    }

    private static ResourceLocation name(String variant)
    {
        return new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/battlegill/%s.png".formatted(variant));
    }
}