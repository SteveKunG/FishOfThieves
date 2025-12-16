package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.entity.variant.IslehopperVariant;
import com.stevekung.fishofthieves.registry.FOTBiomes;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.level.biome.Biomes;

public class IslehopperVariants
{
    public static final ResourceKey<IslehopperVariant> STONE = createKey("stone");
    public static final ResourceKey<IslehopperVariant> MOSS = createKey("moss");
    public static final ResourceKey<IslehopperVariant> HONEY = createKey("honey");
    public static final ResourceKey<IslehopperVariant> RAVEN = createKey("raven");
    public static final ResourceKey<IslehopperVariant> AMETHYST = createKey("amethyst");
    public static final ResourceKey<IslehopperVariant> BRINY = createKey("briny");

    public static void bootstrap(BootstrapContext<IslehopperVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("islehopper", IslehopperVariant::new);
        var biomeLookup = context.lookup(Registries.BIOME);
        registerContext.register(context, STONE, "stone", 0);
        registerContext.register(context, MOSS, "moss", 1, AnyConditionCheck.anyOf(new BiomeCheck(biomeLookup.getOrThrow(BiomeTags.IS_JUNGLE)),
                new BiomeCheck(HolderSet.direct(biomeLookup.getOrThrow(Biomes.SWAMP), biomeLookup.getOrThrow(Biomes.MANGROVE_SWAMP), biomeLookup.getOrThrow(Biomes.LUSH_CAVES), biomeLookup.getOrThrow(FOTBiomes.TROPICAL_ISLAND)))).build()
        );
        registerContext.register(context, HONEY, "honey", 2, HasBeehiveCheck.beehive(5, 12));
        registerContext.register(context, RAVEN, "raven", 3,
                List.of(
                        registerContext.select(AllConditionCheck.allOf(ProbabilityCheck.defaultRareProbablity(), HeightCheck.height(MinMaxBounds.Ints.atMost(0))), 0)),
                List.of(
                        registerContext.select(AllConditionCheck.allOf(ProbabilityCheck.defaultRareProbablity(), HeightCheck.height(MinMaxBounds.Ints.atMost(0))), 1),
                        registerContext.select(AllConditionCheck.allOf(RandomChanceCheck.chance(3), LivingEntityHasEffectCondition.effect(HolderSet.direct(MobEffects.BLINDNESS, MobEffects.DARKNESS))), 0)
                ));
        registerContext.register(context, AMETHYST, "amethyst", 4, true, MinimumBlockRangeCheck.minimumBlocksInRange(Optional.of(context.lookup(Registries.BLOCK).getOrThrow(FOTTags.Blocks.AMETHYST_ISLEHOPPER_SPAWNABLE_ON)), Optional.empty(), 4, 12));
        registerContext.register(context, BRINY, "briny", 5, true, true);
    }

    public static void bootstrapSimple(BootstrapContext<IslehopperVariant> context)
    {
        var registerContext = AbstractFishVariant.RegisterContext.create("islehopper", IslehopperVariant::new);
        registerContext.register(context, STONE, "stone", 0);
        registerContext.register(context, MOSS, "moss", 1);
        registerContext.register(context, HONEY, "honey", 2);
        registerContext.register(context, RAVEN, "raven", 3, ProbabilityCheck.defaultRareProbablity());
        registerContext.register(context, AMETHYST, "amethyst", 4, true, AllConditionCheck.allOf(NightCheck.night(), SeeSkyCheck.seeSky()));
        registerContext.register(context, BRINY, "briny", 5, true, true);
    }

    private static ResourceKey<IslehopperVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.ISLEHOPPER_VARIANT, FishOfThieves.id(name));
    }
}