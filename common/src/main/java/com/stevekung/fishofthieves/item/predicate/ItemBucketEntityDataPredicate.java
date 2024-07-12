package com.stevekung.fishofthieves.item.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.world.item.ItemStack;

public record ItemBucketEntityDataPredicate(BucketNbtPredicate value) implements ItemSubPredicate
{
    public static final Codec<ItemBucketEntityDataPredicate> CODEC = BucketNbtPredicate.CODEC.xmap(ItemBucketEntityDataPredicate::new, ItemBucketEntityDataPredicate::value);

    @Override
    public boolean matches(ItemStack stack)
    {
        return this.value.matches(stack);
    }

    public static ItemBucketEntityDataPredicate bucketEntityData(BucketNbtPredicate value)
    {
        return new ItemBucketEntityDataPredicate(value);
    }
}