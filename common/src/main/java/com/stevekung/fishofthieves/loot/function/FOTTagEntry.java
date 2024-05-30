package com.stevekung.fishofthieves.loot.function;

import java.util.List;
import java.util.function.Consumer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.item.FOTItem;
import com.stevekung.fishofthieves.registry.FOTLootPoolEntries;
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
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FOTTagEntry extends LootPoolSingletonContainer
{
    public static final MapCodec<FOTTagEntry> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(TagKey.codec(Registries.ITEM).fieldOf("name").forGetter(tagEntry -> tagEntry.tag), Codec.BOOL.fieldOf("expand").forGetter(tagEntry -> tagEntry.expand)).and(singletonFields(instance)).apply(instance, FOTTagEntry::new));
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

    private void createItemStackWithData(Consumer<ItemStack> stackConsumer, LootContext lootContext, Holder<Item> itemHolder)
    {
        var itemStack = new ItemStack(itemHolder);
        var vec3 = lootContext.getParamOrNull(LootContextParams.ORIGIN);
        stackConsumer.accept(FOTItem.generateRandomFishVariantLootItem(itemStack, lootContext.getLevel(), vec3, lootContext.getRandom()));
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