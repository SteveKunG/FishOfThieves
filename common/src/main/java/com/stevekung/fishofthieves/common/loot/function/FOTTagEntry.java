package com.stevekung.fishofthieves.common.loot.function;

import java.util.List;
import java.util.function.Consumer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.common.FishOfThieves;
import com.stevekung.fishofthieves.common.registry.FOTLootPoolEntries;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
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
    public static final Codec<FOTTagEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(TagKey.codec(Registries.ITEM).fieldOf("name").forGetter(tagEntry -> tagEntry.tag), Codec.BOOL.fieldOf("expand").forGetter(tagEntry -> tagEntry.expand)).and(singletonFields(instance)).apply(instance, FOTTagEntry::new));
    private final TagKey<Item> tag;
    private final boolean expand;

    FOTTagEntry(TagKey<Item> tagKey, boolean expand, int weight, int quality, List<LootItemCondition> conditions, List<LootItemFunction> functions)
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
}