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
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;

public record IslehopperVariant(Supplier<Predicate<SpawnConditionContext>> condition, ResourceLocation texture, Optional<ResourceLocation> glowTexture) implements FishData
{
    public static final IslehopperVariant STONE = create(SpawnSelectors.always(), name("stone"));
    public static final IslehopperVariant MOSS = create(SpawnSelectors.simpleSpawn(SpawnSelectors.biomeTag(FOTTags.SPAWNS_MOSS_ISLEHOPPERS)), name("moss"));
    public static final IslehopperVariant HONEY = create(SpawnSelectors.simpleSpawn(context ->
    {
        var poiManager = context.level().getPoiManager();
        var optional = poiManager.findClosest(type -> type.is(PoiTypes.BEEHIVE) || type.is(PoiTypes.BEE_NEST), context.blockPos(), 9, PoiManager.Occupancy.ANY);

        if (optional.isPresent())
        {
            var blockState = context.level().getBlockState(optional.get());
            return BeehiveBlockEntity.getHoneyLevel(blockState) == 5;
        }
        return false;
    }), name("honey"));
    public static final IslehopperVariant RAVEN = create(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.ravenIslehopperProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.ravenIslehopperProbability).and(context -> context.blockPos().getY() <= 0)), name("raven"));
    public static final IslehopperVariant AMETHYST = new IslehopperVariant(() -> SpawnSelectors.simpleSpawn(true, context -> TerrainUtils.lookForBlocksWithSize(context.blockPos(), 2, 16, blockPos2 -> context.level().getBlockState(blockPos2).is(BlockTags.CRYSTAL_SOUND_BLOCKS))), name("amethyst"), Optional.of(new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/islehopper/amethyst_glow.png")));

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
        IslehopperVariant.register("stone", STONE);
        IslehopperVariant.register("moss", MOSS);
        IslehopperVariant.register("honey", HONEY);
        IslehopperVariant.register("raven", RAVEN);
        IslehopperVariant.register("amethyst", AMETHYST);
    }

    private static void register(String key, IslehopperVariant variant)
    {
        FOTPlatform.registerIslehopperVariant(key, variant);
    }

    private static IslehopperVariant create(Predicate<SpawnConditionContext> condition, ResourceLocation texture)
    {
        return new IslehopperVariant(() -> condition, texture, Optional.empty());
    }

    private static ResourceLocation name(String variant)
    {
        return new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/islehopper/%s.png".formatted(variant));
    }
}