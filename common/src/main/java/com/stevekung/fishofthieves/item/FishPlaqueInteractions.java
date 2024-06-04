package com.stevekung.fishofthieves.item;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.api.block.fish_plaque.FishPlaqueInteraction;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class FishPlaqueInteractions
{
    public static final ResourceKey<FishPlaqueInteraction> SNAIL = createKey("naturalist/snail");
    public static final ResourceKey<FishPlaqueInteraction> COSMIC_COD = createKey("alexsmobs/cosmic_cod");
    public static final ResourceKey<FishPlaqueInteraction> STRADPOLE = createKey("alexsmobs/stradpole");
    public static final ResourceKey<FishPlaqueInteraction> RADGILL = createKey("alexscaves/radgill");

    public static void bootstrap(BootstrapContext<FishPlaqueInteraction> context)
    {
        register(context, SNAIL, ResourceLocation.fromNamespaceAndPath("naturalist", "snail"), Items.BUCKET);
        register(context, COSMIC_COD, ResourceLocation.fromNamespaceAndPath("alexsmobs", "cosmic_cod"), Items.BUCKET);
        register(context, STRADPOLE, ResourceLocation.fromNamespaceAndPath("alexsmobs", "stradpole"), Items.LAVA_BUCKET);
        register(context, RADGILL, ResourceLocation.fromNamespaceAndPath("alexscaves", "radgill"), ResourceLocation.fromNamespaceAndPath("alexscaves", "acid_bucket"));
    }

    static void register(BootstrapContext<FishPlaqueInteraction> context, ResourceKey<FishPlaqueInteraction> key, ResourceLocation entityType, ResourceLocation item)
    {
        context.register(key, new FishPlaqueInteraction(entityType, item));
    }

    static void register(BootstrapContext<FishPlaqueInteraction> context, ResourceKey<FishPlaqueInteraction> key, ResourceLocation entityType, Item item)
    {
        context.register(key, new FishPlaqueInteraction(entityType, BuiltInRegistries.ITEM.getKey(item)));
    }

    private static ResourceKey<FishPlaqueInteraction> createKey(String name)
    {
        return ResourceKey.create(FOTRegistries.FISH_PLAQUE_INTERACTION, FishOfThieves.id(name));
    }
}