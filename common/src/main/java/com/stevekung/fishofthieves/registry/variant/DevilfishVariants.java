package com.stevekung.fishofthieves.registry.variant;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.*;
import com.stevekung.fishofthieves.entity.variant.DevilfishVariant;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.FluidTags;

public class DevilfishVariants
{
    public static final ResourceKey<DevilfishVariant> ASHEN = createKey("ashen");
    public static final ResourceKey<DevilfishVariant> SEASHELL = createKey("seashell");
    public static final ResourceKey<DevilfishVariant> LAVA = createKey("lava");
    public static final ResourceKey<DevilfishVariant> FORSAKEN = createKey("forsaken");
    public static final ResourceKey<DevilfishVariant> FIRELIGHT = createKey("firelight");

    public static void bootstrap(BootstrapContext<DevilfishVariant> context)
    {
        register(context, ASHEN, "ashen", 0);
        register(context, SEASHELL, "seashell", 1);
        register(context, LAVA, "lava", 2, MatchBlocksInRangeCondition.blocksInRange(Optional.empty(), Optional.of(context.lookup(Registries.FLUID).getOrThrow(FluidTags.LAVA)), 4).build());
        register(context, FORSAKEN, "forsaken", 3, ProbabilityCondition.defaultRareProbablity().build());
        register(context, FIRELIGHT, "firelight", 4, true, AllOfCondition.allOf(NightCondition.night(), MatchBlocksInRangeCondition.blocksInRange(Optional.of(context.lookup(Registries.BLOCK).getOrThrow(FOTTags.Blocks.FIRELIGHT_DEVILFISH_WARM_BLOCKS)), Optional.of(context.lookup(Registries.FLUID).getOrThrow(FluidTags.LAVA)), 4)).build());
    }

    static void register(BootstrapContext<DevilfishVariant> context, ResourceKey<DevilfishVariant> key, String name, int customModelData, SpawnCondition... conditions)
    {
        register(context, key, name, customModelData, false, conditions);
    }

    static void register(BootstrapContext<DevilfishVariant> context, ResourceKey<DevilfishVariant> key, String name, int customModelData, boolean glow, SpawnCondition... conditions)
    {
        var texture = FishOfThieves.id("entity/devilfish/" + name);
        var glowTexture = FishOfThieves.id("entity/devilfish/" + name + "_glow");
        context.register(key, new DevilfishVariant(name, texture, glow ? Optional.of(glowTexture) : Optional.empty(), List.of(conditions), BuiltInRegistries.ITEM.wrapAsHolder(FOTItems.DEVILFISH), customModelData));
    }

    private static ResourceKey<DevilfishVariant> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.DEVILFISH_VARIANT, FishOfThieves.id(name));
    }
}