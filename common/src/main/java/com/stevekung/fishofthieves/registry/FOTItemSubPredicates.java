package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.item.predicate.ItemBucketEntityDataPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicate.Type;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FOTItemSubPredicates
{
    public static final Type<ItemBucketEntityDataPredicate> BUCKET_ENTITY_DATA = new Type<>(ItemBucketEntityDataPredicate.CODEC);

    public static void init()
    {
        register("bucket_entity_data", BUCKET_ENTITY_DATA);
    }

    private static <T extends ItemSubPredicate> void register(String key, Type<T> type)
    {
        Registry.register(BuiltInRegistries.ITEM_SUB_PREDICATE_TYPE, key, type);
    }
}