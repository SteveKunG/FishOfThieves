package com.stevekung.fishofthieves.item.predicate;

import com.mojang.serialization.Codec;

import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.predicates.DataComponentPredicate;

public record ItemBucketEntityDataPredicate(BucketNbtPredicate value) implements DataComponentPredicate
{
    public static final Codec<ItemBucketEntityDataPredicate> CODEC = BucketNbtPredicate.CODEC.xmap(ItemBucketEntityDataPredicate::new, ItemBucketEntityDataPredicate::value);

    @Override
    public boolean matches(DataComponentGetter dataComponentGetter)
    {
        return this.value.matches(dataComponentGetter);
    }

    public static ItemBucketEntityDataPredicate bucketEntityData(BucketNbtPredicate value)
    {
        return new ItemBucketEntityDataPredicate(value);
    }
}