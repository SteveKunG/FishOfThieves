package com.stevekung.fishofthieves.client;

import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.item.ResourceKeyHolder;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;

public class FOTItemProperties
{
    private FOTItemProperties() {}

    private static final List<Item> ITEMS = List.of(FOTItems.SPLASHTAIL, FOTItems.PONDIE, FOTItems.ISLEHOPPER, FOTItems.ANCIENTSCALE, FOTItems.PLENTIFIN, FOTItems.WILDSPLASH, FOTItems.DEVILFISH, FOTItems.BATTLEGILL, FOTItems.WRECKER, FOTItems.STORMFISH, FOTItems.SPLASHTAIL_BUCKET, FOTItems.PONDIE_BUCKET, FOTItems.ISLEHOPPER_BUCKET, FOTItems.ANCIENTSCALE_BUCKET, FOTItems.PLENTIFIN_BUCKET, FOTItems.WILDSPLASH_BUCKET, FOTItems.DEVILFISH_BUCKET, FOTItems.BATTLEGILL_BUCKET, FOTItems.WRECKER_BUCKET, FOTItems.STORMFISH_BUCKET);

    public static void register()
    {
        for (var item : ITEMS)
        {
            if (!(item instanceof ResourceKeyHolder resourceKeyHolder))
            {
                return;
            }

            var registryName = resourceKeyHolder.getResourceKey().location().getPath();

            ItemProperties.register(item, FishOfThieves.id(registryName), (itemStack, level, livingEntity, seed) ->
            {
                if (!itemStack.hasTag() || level == null)
                {
                    return 0;
                }

                var variant = itemStack.getTag().getString(registryName);
                var variantList = level.registryAccess().lookupOrThrow(resourceKeyHolder.getResourceKey()).listElementIds().toList();

                for (var i = 0; i < variantList.size(); i++)
                {
                    var resourceKey = variantList.get(i);

                    if (variant.equals(resourceKey.location().toString()))
                    {
                        return i / 16f;
                    }
                }
                return 0;
            });
        }
    }
}