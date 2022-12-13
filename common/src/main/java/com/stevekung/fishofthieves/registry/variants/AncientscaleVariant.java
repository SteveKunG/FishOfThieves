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

public record AncientscaleVariant(Supplier<Predicate<SpawnConditionContext>> condition, ResourceLocation texture, Optional<ResourceLocation> glowTexture) implements FishData
{
    public static final AncientscaleVariant ALMOND = create(SpawnSelectors.always(), name("almond"));
    public static final AncientscaleVariant SAPPHIRE = create(SpawnSelectors.always(), name("sapphire"));
    public static final AncientscaleVariant SMOKE = create(SpawnSelectors.always(), name("smoke"));
    public static final AncientscaleVariant BONE = create(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.boneAncientscaleProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.boneAncientscaleProbability).or(SpawnSelectors.structureTag(FOTTags.BONE_ANCIENTSCALES_SPAWN_IN).and(context -> context.random().nextInt(10) == 0))), name("bone"));
    public static final AncientscaleVariant STARSHINE = new AncientscaleVariant(() -> SpawnSelectors.simpleSpawn(true, SpawnSelectors.nightAndSeeSky().and(context -> context.level().getMoonBrightness() <= 0.25F)), name("starshine"), Optional.of(new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/ancientscale/starshine_glow.png")));

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
        AncientscaleVariant.register("almond", ALMOND);
        AncientscaleVariant.register("sapphire", SAPPHIRE);
        AncientscaleVariant.register("smoke", SMOKE);
        AncientscaleVariant.register("bone", BONE);
        AncientscaleVariant.register("starshine", STARSHINE);
    }

    private static void register(String key, AncientscaleVariant variant)
    {
        Registry.register(FOTRegistry.ANCIENTSCALE_VARIANT, new ResourceLocation(FishOfThieves.MOD_ID, key), variant);
    }

    private static AncientscaleVariant create(Predicate<SpawnConditionContext> condition, ResourceLocation texture)
    {
        return new AncientscaleVariant(() -> condition, texture, Optional.empty());
    }

    private static ResourceLocation name(String variant)
    {
        return new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/ancientscale/%s.png".formatted(variant));
    }
}