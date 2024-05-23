package com.stevekung.fishofthieves.loot.function;

import java.util.List;
import java.util.function.Consumer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTLootPoolEntries;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
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

        if (FishOfThieves.CONFIG.general.enableFishItemWithAllVariant)
        {
            var isNight = !lootContext.getLevel().isDay();
            var data = getData(lootContext, isNight);

            if (data > 0)
            {
                itemStack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(data));
            }
        }

        stackConsumer.accept(itemStack);
    }

    private static int getData(LootContext lootContext, boolean isNight)
    {
        var random = lootContext.getRandom();
        var chance = random.nextDouble();
        var data = 0;

        if (chance < 0.7)
        {
            data = isNight && random.nextInt(3) == 0 ? 4 : 0;
        }
        else if (chance < 0.85)
        {
            data = 1;
        }
        else if (chance < 0.95)
        {
            data = 2;
        }
        else if (chance < 1.0)
        {
            data = 3;
        }
        return data;
    }

    @SuppressWarnings("deprecation")
    public static LootPoolSingletonContainer.Builder<?> lootTableItem(ItemLike item)
    {
        return simpleBuilder((weight, quality, conditions, functions) -> new FOTLootItem(item.asItem().builtInRegistryHolder(), weight, quality, conditions, functions));
    }
}