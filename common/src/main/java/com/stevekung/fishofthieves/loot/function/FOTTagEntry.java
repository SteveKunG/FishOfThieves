package com.stevekung.fishofthieves.loot.function;

import java.util.function.Consumer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTLootPoolEntries;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FOTTagEntry extends LootPoolSingletonContainer
{
    final TagKey<Item> tag;
    final boolean expand;

    FOTTagEntry(TagKey<Item> tagKey, boolean expand, int weight, int quality, LootItemCondition[] conditions, LootItemFunction[] functions)
    {
        super(weight, quality, conditions, functions);
        this.tag = tagKey;
        this.expand = expand;
    }

    @Override
    public LootPoolEntryType getType()
    {
        return FOTLootPoolEntries.FOT_TAG;
    }

    @Override
    public void createItemStack(Consumer<ItemStack> stackConsumer, LootContext lootContext)
    {
        BuiltInRegistries.ITEM.getTagOrEmpty(this.tag).forEach(holder -> this.createItemStackWithData(stackConsumer, lootContext, holder));
    }

    private void createItemStackWithData(Consumer<ItemStack> stackConsumer, LootContext lootContext, Holder<Item> holder)
    {
        var itemStack = new ItemStack(holder);

        if (FishOfThieves.CONFIG.general.enableFishItemWithAllVariant)
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

    private boolean expandTag(LootContext context, Consumer<LootPoolEntry> generatorConsumer)
    {
        if (!this.canRun(context))
        {
            return false;
        }
        else
        {
            for (var holder : BuiltInRegistries.ITEM.getTagOrEmpty(this.tag))
            {
                generatorConsumer.accept(new LootPoolSingletonContainer.EntryBase()
                {
                    @Override
                    public void createItemStack(Consumer<ItemStack> stackConsumer, LootContext lootContext)
                    {
                        FOTTagEntry.this.createItemStackWithData(stackConsumer, lootContext, holder);
                    }
                });
            }
            return true;
        }
    }

    @Override
    public boolean expand(LootContext lootContext, Consumer<LootPoolEntry> consumer)
    {
        return this.expand ? this.expandTag(lootContext, consumer) : super.expand(lootContext, consumer);
    }

    public static LootPoolSingletonContainer.Builder<?> expandTag(TagKey<Item> tag)
    {
        return simpleBuilder((weight, quality, conditions, functions) -> new FOTTagEntry(tag, true, weight, quality, conditions, functions));
    }

    public static class Serializer extends LootPoolSingletonContainer.Serializer<FOTTagEntry>
    {
        @Override
        public void serializeCustom(JsonObject object, FOTTagEntry context, JsonSerializationContext conditions)
        {
            super.serializeCustom(object, context, conditions);
            object.addProperty("name", context.tag.location().toString());
            object.addProperty("expand", context.expand);
        }

        @Override
        protected FOTTagEntry deserialize(JsonObject object, JsonDeserializationContext context, int weight, int quality, LootItemCondition[] conditions, LootItemFunction[] functions)
        {
            var resourceLocation = new ResourceLocation(GsonHelper.getAsString(object, "name"));
            var tagKey = TagKey.create(Registries.ITEM, resourceLocation);
            var expand = GsonHelper.getAsBoolean(object, "expand");
            return new FOTTagEntry(tagKey, expand, weight, quality, conditions, functions);
        }
    }
}