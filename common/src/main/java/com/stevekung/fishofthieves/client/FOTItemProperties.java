package com.stevekung.fishofthieves.client;

import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.item.ResourceKeyHolder;
import com.stevekung.fishofthieves.registry.FOTItems;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;

public class FOTItemProperties
{
    private static final List<Item> FISH_ITEMS = List.of(FOTItems.SPLASHTAIL, FOTItems.PONDIE, FOTItems.ISLEHOPPER, FOTItems.ANCIENTSCALE, FOTItems.PLENTIFIN, FOTItems.WILDSPLASH, FOTItems.DEVILFISH, FOTItems.BATTLEGILL, FOTItems.WRECKER, FOTItems.STORMFISH);
    private static final List<Item> BUCKET_ITEMS = List.of(FOTItems.SPLASHTAIL_BUCKET, FOTItems.PONDIE_BUCKET, FOTItems.ISLEHOPPER_BUCKET, FOTItems.ANCIENTSCALE_BUCKET, FOTItems.PLENTIFIN_BUCKET, FOTItems.WILDSPLASH_BUCKET, FOTItems.DEVILFISH_BUCKET, FOTItems.BATTLEGILL_BUCKET, FOTItems.WRECKER_BUCKET, FOTItems.STORMFISH_BUCKET);

    public static void register()
    {
        createItemProperties(FISH_ITEMS, DataComponents.CUSTOM_DATA);
        createItemProperties(BUCKET_ITEMS, DataComponents.BUCKET_ENTITY_DATA);
    }

    private static void createItemProperties(List<Item> items, DataComponentType<CustomData> dataComponentType)
    {
        for (var item : items)
        {
            if (!(item instanceof ResourceKeyHolder resourceKeyHolder))
            {
                return;
            }

            var registryKey = resourceKeyHolder.getResourceKey().location().getPath();

            ItemProperties.register(item, FishOfThieves.id(registryKey), (itemStack, level, livingEntity, seed) ->
            {
                if (level == null)
                {
                    return 0;
                }

                var customData = itemStack.getOrDefault(dataComponentType, CustomData.EMPTY);
                var variant = customData.copyTag().getString(registryKey);
                var variantList = level.registryAccess().lookupOrThrow(resourceKeyHolder.getResourceKey()).listElements().sorted(AbstractFishVariant.COMPARATOR).map(Holder.Reference::key).toList();

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