package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.item.predicate.ItemBucketEntityDataPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FOTItemSubPredicates
{
    public static final ItemSubPredicate.Type<ItemBucketEntityDataPredicate> BUCKET_ENTITY_DATA = register("bucket_entity_data", new ItemSubPredicate.Type<>(ItemBucketEntityDataPredicate.CODEC));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Item Sub Predicate");
    }

    private static <T extends ItemSubPredicate> ItemSubPredicate.Type<T> register(String key, ItemSubPredicate.Type<T> type)
    {
        return Registry.register(BuiltInRegistries.ITEM_SUB_PREDICATE_TYPE, key, type);
    }
}