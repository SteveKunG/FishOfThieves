package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.item.predicate.ItemBucketEntityDataPredicate;

import net.minecraft.core.Registry;
import net.minecraft.core.component.predicates.DataComponentPredicate;
import net.minecraft.core.registries.BuiltInRegistries;

public class FOTDataComponentPredicates
{
    public static final DataComponentPredicate.Type<ItemBucketEntityDataPredicate> BUCKET_ENTITY_DATA = register("bucket_entity_data", new DataComponentPredicate.ConcreteType<>(ItemBucketEntityDataPredicate.CODEC));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Data Component Predicate");
    }

    private static <T extends DataComponentPredicate> DataComponentPredicate.Type<T> register(String key, DataComponentPredicate.Type<T> type)
    {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_PREDICATE_TYPE, key, type);
    }
}