package com.stevekung.fishofthieves.loot.function;

import java.util.Optional;
import java.util.function.Consumer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.item.FOTItem;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.ExpandableContainerBase;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FOTTagEntry extends ExpandableContainerBase
{
    public static final MapCodec<FOTTagEntry> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(RegistryCodecs.homogeneousList(Registries.ITEM).fieldOf("items").forGetter(entry -> entry.tag)).and(expandableFields(instance)).apply(instance, FOTTagEntry::new));
    private final HolderSet<Item> tag;

    FOTTagEntry(HolderSet<Item> tag, boolean expand, int weight, int quality, Optional<Holder<LootItemCondition>> condition, Optional<Holder<LootItemFunction>> modifier)
    {
        super(expand, weight, quality, condition, modifier);
        this.tag = tag;
    }

    @Override
    public MapCodec<? extends ExpandableContainerBase> codec()
    {
        return MAP_CODEC;
    }

    @Override
    protected boolean addExpandedEntries(Consumer<LootPoolEntry> output)
    {
        for (var item : this.tag)
        {
            output.accept(new UniformContainerBase.EntryBase()
            {
                @Override
                public void createItemStack(Consumer<ItemStack> output, LootContext context)
                {
                    output.accept(FOTTagEntry.this.createItemStackWithData(context, item));
                }
            });
        }
        return true;
    }

    @Override
    protected boolean addUnexpandedEntry(Consumer<LootPoolEntry> output)
    {
        output.accept(new UniformContainerBase.EntryBase()
        {
            @Override
            public void createItemStack(Consumer<ItemStack> output, LootContext context)
            {
                FOTTagEntry.this.tag.forEach(item -> output.accept(FOTTagEntry.this.createItemStackWithData(context, item)));
            }
        });
        return true;
    }

    private ItemStack createItemStackWithData(LootContext lootContext, Holder<Item> itemHolder)
    {
        var itemStack = new ItemStack(itemHolder);
        var vec3 = lootContext.getOptionalParameter(LootContextParams.ORIGIN);
        var entity = lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY);
        return FOTItem.generateRandomFishVariantLootItem(itemStack, entity, lootContext.getLevel(), vec3, lootContext.getRandom());
    }

    public static UniformContainerBase.Builder<?> expandTag(HolderSet<Item> tag)
    {
        return simpleBuilder((weight, quality, conditions, functions) -> new FOTTagEntry(tag, true, weight, quality, conditions, functions));
    }
}