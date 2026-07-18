package com.stevekung.fishofthieves.loot.function;

import java.util.Optional;
import java.util.function.Consumer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.item.FOTItem;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.SingleEntryContainerBase;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FOTLootItem extends SingleEntryContainerBase
{
    private final Holder<Item> item;
    public static final MapCodec<FOTLootItem> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("name").forGetter(lootItem -> lootItem.item)).and(uniformFields(instance)).apply(instance, FOTLootItem::new));

    FOTLootItem(Holder<Item> item, int weight, int quality, Optional<Holder<LootItemCondition>> condition, Optional<Holder<LootItemFunction>> modifier)
    {
        super(weight, quality, condition, modifier);
        this.item = item;
    }

    @Override
    public MapCodec<? extends SingleEntryContainerBase> codec()
    {
        return MAP_CODEC;
    }

    @Override
    public void createItemStack(Consumer<ItemStack> stackConsumer, LootContext lootContext)
    {
        var itemStack = new ItemStack(this.item);
        var vec3 = lootContext.getOptionalParameter(LootContextParams.ORIGIN);
        var entity = lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY);
        stackConsumer.accept(FOTItem.generateRandomFishVariantLootItem(itemStack, entity, lootContext.getLevel(), vec3, lootContext.getRandom()));
    }

    @SuppressWarnings("deprecation")
    public static Builder<?> lootTableItem(ItemLike item)
    {
        return simpleBuilder((weight, quality, conditions, functions) -> new FOTLootItem(item.asItem().builtInRegistryHolder(), weight, quality, conditions, functions));
    }
}