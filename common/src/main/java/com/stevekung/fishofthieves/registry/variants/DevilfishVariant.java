package com.stevekung.fishofthieves.registry.variants;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.FishData;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.spawn.SpawnConditionContext;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import com.stevekung.fishofthieves.utils.FOTPlatform;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;

public record DevilfishVariant(Supplier<Predicate<SpawnConditionContext>> condition, ResourceLocation texture, Optional<ResourceLocation> glowTexture) implements FishData
{
    public static final DevilfishVariant ASHEN = create(SpawnSelectors.always(), name("ashen"));
    public static final DevilfishVariant SEASHELL = create(SpawnSelectors.always(), name("seashell"));
    public static final DevilfishVariant LAVA = create(SpawnSelectors.simpleSpawn(context -> TerrainUtils.lookForBlock(context.blockPos(), 4, blockPos2 -> context.level().getFluidState(blockPos2).is(FluidTags.LAVA) && context.level().getFluidState(blockPos2).isSource()).isPresent()), name("lava"));
    public static final DevilfishVariant FORSAKEN = create(SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.forsakenDevilfishProbability), name("forsaken"));
    public static final DevilfishVariant FIRELIGHT = new DevilfishVariant(() -> SpawnSelectors.simpleSpawn(true, context ->
    {
        var optional = TerrainUtils.lookForBlock(context.blockPos(), 4, blockPos2 -> context.level().getBlockState(blockPos2).is(FOTTags.FIRELIGHT_DEVILFISH_WARM_BLOCKS) || context.level().getFluidState(blockPos2).is(FluidTags.LAVA) && context.level().getFluidState(blockPos2).isSource());
        return context.isNight() && optional.isPresent();
    }), name("firelight"), Optional.of(new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/devilfish/firelight_glow.png")));

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
        DevilfishVariant.register("ashen", ASHEN);
        DevilfishVariant.register("seashell", SEASHELL);
        DevilfishVariant.register("lava", LAVA);
        DevilfishVariant.register("forsaken", FORSAKEN);
        DevilfishVariant.register("firelight", FIRELIGHT);
    }

    private static void register(String key, DevilfishVariant variant)
    {
        FOTPlatform.registerDevilfishVariant(key, variant);
    }

    private static DevilfishVariant create(Predicate<SpawnConditionContext> condition, ResourceLocation texture)
    {
        return new DevilfishVariant(() -> condition, texture, Optional.empty());
    }

    private static ResourceLocation name(String variant)
    {
        return new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/devilfish/%s.png".formatted(variant));
    }
}