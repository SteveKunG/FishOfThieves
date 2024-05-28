package com.stevekung.fishofthieves.common.loot.function;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.common.registry.FOTLootItemFunctions;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetRandomFireworkFunction extends LootItemConditionalFunction
{
    public static final Codec<SetRandomFireworkFunction> CODEC = RecordCodecBuilder.create(instance -> commonFields(instance).and(Codec.INT.listOf().fieldOf("fireworkColors").forGetter(copyBlockState -> copyBlockState.fireworkColors)).apply(instance, SetRandomFireworkFunction::new));
    private final List<Integer> fireworkColors;

    SetRandomFireworkFunction(List<LootItemCondition> predicates, List<Integer> fireworkColors)
    {
        super(predicates);
        this.fireworkColors = ImmutableList.copyOf(fireworkColors);
    }

    @Override
    public LootItemFunctionType getType()
    {
        return FOTLootItemFunctions.SET_RANDOM_FIREWORK;
    }

    @Override
    public ItemStack run(ItemStack itemStack, LootContext context)
    {
        if (itemStack.is(Items.FIREWORK_ROCKET) && !this.fireworkColors.isEmpty())
        {
            var random = context.getRandom();
            var compoundTag = itemStack.getOrCreateTagElement(FireworkRocketItem.TAG_FIREWORKS);
            var listTag = new ListTag();
            var explosionTag = new CompoundTag();
            var flicker = random.nextBoolean();
            var trail = random.nextBoolean();

            if (flicker)
            {
                explosionTag.putBoolean(FireworkRocketItem.TAG_EXPLOSION_FLICKER, true);
            }
            if (trail)
            {
                explosionTag.putBoolean(FireworkRocketItem.TAG_EXPLOSION_TRAIL, true);
            }
            explosionTag.putIntArray(FireworkRocketItem.TAG_EXPLOSION_COLORS, List.of(Util.getRandom(this.fireworkColors, random)));
            explosionTag.putIntArray(FireworkRocketItem.TAG_EXPLOSION_FADECOLORS, List.of(DyeColor.WHITE.getFireworkColor()));
            explosionTag.putByte(FireworkRocketItem.TAG_EXPLOSION_TYPE, (byte) Util.getRandom(FireworkRocketItem.Shape.values(), random).getId());
            listTag.add(explosionTag);
            compoundTag.putByte(FireworkRocketItem.TAG_FLIGHT, (byte) (1 + random.nextInt(3)));
            compoundTag.put(FireworkRocketItem.TAG_EXPLOSIONS, listTag);
        }
        return itemStack;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder extends LootItemConditionalFunction.Builder<Builder>
    {
        private final List<Integer> fireworkColors = Lists.newArrayList();

        @Override
        protected Builder getThis()
        {
            return this;
        }

        public Builder withColor(DyeColor color)
        {
            this.fireworkColors.add(color.getFireworkColor());
            return this;
        }

        public Builder withColor(int color)
        {
            this.fireworkColors.add(color);
            return this;
        }

        @Override
        public LootItemFunction build()
        {
            return new SetRandomFireworkFunction(this.getConditions(), this.fireworkColors);
        }
    }
}