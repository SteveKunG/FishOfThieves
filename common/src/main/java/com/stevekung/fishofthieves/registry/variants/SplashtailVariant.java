package com.stevekung.fishofthieves.registry.variants;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.FishData;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.spawn.SpawnConditionContext;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public record SplashtailVariant(Supplier<Predicate<SpawnConditionContext>> condition, ResourceLocation texture, Optional<ResourceLocation> glowTexture) implements FishData
{
    public static final SplashtailVariant RUBY = create(SpawnSelectors.always(), name("ruby"));
    public static final SplashtailVariant SUNNY = create(SpawnSelectors.simpleSpawn(SpawnSelectors.dayAndSeeSky()), name("sunny"));
    public static final SplashtailVariant INDIGO = create(SpawnSelectors.always(), name("indigo"));
    public static final SplashtailVariant UMBER = create(SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.umberSplashtailProbability), name("umber"));
    public static final SplashtailVariant SEAFOAM = new SplashtailVariant(SpawnSelectors::nightAndSeeSky, name("seafoam"), Optional.of(new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/splashtail/seafoam_glow.png")));

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
        SplashtailVariant.register("ruby", RUBY);
        SplashtailVariant.register("sunny", SUNNY);
        SplashtailVariant.register("indigo", INDIGO);
        SplashtailVariant.register("umber", UMBER);
        SplashtailVariant.register("seafoam", SEAFOAM);
    }

    private static void register(String key, SplashtailVariant variant)
    {
        Registry.register(FOTRegistry.SPLASHTAIL_VARIANT, new ResourceLocation(FishOfThieves.MOD_ID, key), variant);
    }

    private static SplashtailVariant create(Predicate<SpawnConditionContext> condition, ResourceLocation texture)
    {
        return new SplashtailVariant(() -> condition, texture, Optional.empty());
    }

    private static ResourceLocation name(String variant)
    {
        return new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/splashtail/%s.png".formatted(variant));
    }
}