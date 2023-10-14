package com.stevekung.fishofthieves.loot.function;

import java.util.function.Consumer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTLootPoolEntries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FOTLootItem extends LootPoolSingletonContainer
{
    final Item item;

    FOTLootItem(Item item, int weight, int quality, LootItemCondition[] conditions, LootItemFunction[] functions)
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

        if (FishOfThieves.CONFIG.general.dropAndPickFishesWithVariant)
        {
            var isNight = !lootContext.getLevel().isDay();
            var data = getData(lootContext, isNight);

            if (data > 0)
            {
                itemStack.getOrCreateTag().putInt("CustomModelData", data);
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

    public static LootPoolSingletonContainer.Builder<?> lootTableItem(ItemLike item)
    {
        return simpleBuilder((weight, quality, conditions, functions) -> new FOTLootItem(item.asItem(), weight, quality, conditions, functions));
    }

    public static class Serializer extends LootPoolSingletonContainer.Serializer<FOTLootItem>
    {
        @Override
        public void serializeCustom(JsonObject object, FOTLootItem context, JsonSerializationContext conditions)
        {
            super.serializeCustom(object, context, conditions);
            var resourceLocation = BuiltInRegistries.ITEM.getKey(context.item);

            if (resourceLocation == null)
            {
                throw new IllegalArgumentException("Can't serialize unknown item " + context.item);
            }
            else
            {
                object.addProperty("name", resourceLocation.toString());
            }
        }

        @Override
        protected FOTLootItem deserialize(JsonObject object, JsonDeserializationContext context, int weight, int quality, LootItemCondition[] conditions, LootItemFunction[] functions)
        {
            var item = GsonHelper.getAsItem(object, "name");
            return new FOTLootItem(item, weight, quality, conditions, functions);
        }
    }
}