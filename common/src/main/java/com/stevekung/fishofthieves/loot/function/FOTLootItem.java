package com.stevekung.fishofthieves.loot.function;

import java.util.List;
import java.util.function.Consumer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.item.FOTItem;
import com.stevekung.fishofthieves.registry.FOTLootPoolEntries;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FOTLootItem extends LootPoolSingletonContainer
{
    private final Holder<Item> item;
    public static final MapCodec<FOTLootItem> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("name").forGetter(lootItem -> lootItem.item)).and(singletonFields(instance)).apply(instance, FOTLootItem::new));

    FOTLootItem(Holder<Item> item, int weight, int quality, List<LootItemCondition> conditions, List<LootItemFunction> functions)
    {
        super(weight, quality, conditions, functions);
        this.item = item;
    }

    @Override
    public LootPoolEntryType getType()
    {
        return FOTLootPoolEntries.FOT_ITEM;
    }

    @Override
    public void createItemStack(Consumer<ItemStack> stackConsumer, LootContext lootContext)
    {
        var itemStack = new ItemStack(this.item);
        var vec3 = lootContext.getParamOrNull(LootContextParams.ORIGIN);
        stackConsumer.accept(FOTItem.generateRandomFishVariantLootItem(itemStack, lootContext.getLevel(), vec3, lootContext.getRandom()));
    }

    @SuppressWarnings("deprecation")
    public static Builder<?> lootTableItem(ItemLike item)
    {
        return simpleBuilder((weight, quality, conditions, functions) -> new FOTLootItem(item.asItem().builtInRegistryHolder(), weight, quality, conditions, functions));
    }
}