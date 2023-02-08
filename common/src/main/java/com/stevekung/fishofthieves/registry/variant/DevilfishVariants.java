package com.stevekung.fishofthieves.registry.variant;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.DevilfishVariant;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.Registry;
import net.minecraft.tags.FluidTags;

public class DevilfishVariants
{
    public static final DevilfishVariant ASHEN = DevilfishVariant.builder().condition(SpawnSelectors.always()).texture("ashen").build();
    public static final DevilfishVariant SEASHELL = DevilfishVariant.builder().condition(SpawnSelectors.always()).texture("seashell").build();
    public static final DevilfishVariant LAVA = DevilfishVariant.builder().condition(SpawnSelectors.simpleSpawn(context -> TerrainUtils.lookForBlock(context.blockPos(), 4, blockPos2 -> context.level().getFluidState(blockPos2).is(FluidTags.LAVA) && context.level().getFluidState(blockPos2).isSource()).isPresent())).texture("lava").build();
    public static final DevilfishVariant FORSAKEN = DevilfishVariant.builder().condition(SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.forsakenDevilfishProbability)).texture("forsaken").build();
    public static final DevilfishVariant FIRELIGHT = DevilfishVariant.builder().condition(SpawnSelectors.simpleSpawn(true, context ->
    {
        var optional = TerrainUtils.lookForBlock(context.blockPos(), 4, blockPos2 -> context.level().getBlockState(blockPos2).is(FOTTags.Blocks.FIRELIGHT_DEVILFISH_WARM_BLOCKS) || context.level().getFluidState(blockPos2).is(FluidTags.LAVA) && context.level().getFluidState(blockPos2).isSource());
        return context.isNight() && optional.isPresent();
    })).texture("firelight").glowTexture("firelight_glow").build();

    public static void init()
    {
        register("ashen", DevilfishVariants.ASHEN);
        register("seashell", DevilfishVariants.SEASHELL);
        register("lava", DevilfishVariants.LAVA);
        register("forsaken", DevilfishVariants.FORSAKEN);
        register("firelight", DevilfishVariants.FIRELIGHT);
    }

    private static void register(String key, DevilfishVariant variant)
    {
        Registry.register(FOTRegistry.DEVILFISH_VARIANT, FishOfThieves.res(key), variant);
    }
}