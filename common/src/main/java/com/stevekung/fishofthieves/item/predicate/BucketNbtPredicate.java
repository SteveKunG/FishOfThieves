package com.stevekung.fishofthieves.item.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public record BucketNbtPredicate(CompoundTag tag)
{
    public static final Codec<BucketNbtPredicate> CODEC = TagParser.LENIENT_CODEC.xmap(BucketNbtPredicate::new, BucketNbtPredicate::tag);

    public boolean matches(ItemStack stack)
    {
        var customData = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
        return customData.matchedBy(this.tag);
    }
}