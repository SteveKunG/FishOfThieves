package com.stevekung.fishofthieves.loot.function;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.stevekung.fishofthieves.registry.FOTLootItemFunctions;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.GsonHelper;
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
    private final List<Integer> fireworkColors;

    SetRandomFireworkFunction(LootItemCondition[] lootItemConditions, List<Integer> fireworkColors)
    {
        super(lootItemConditions);
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

    public static class Serializer extends LootItemConditionalFunction.Serializer<SetRandomFireworkFunction>
    {
        @Override
        public void serialize(JsonObject json, SetRandomFireworkFunction value, JsonSerializationContext serializationContext)
        {
            super.serialize(json, value, serializationContext);

            if (!value.fireworkColors.isEmpty())
            {
                var jsonArray = new JsonArray();

                for (var fireworkColor : value.fireworkColors)
                {
                    jsonArray.add(serializationContext.serialize(fireworkColor));
                }
                json.add("colors", jsonArray);
            }
        }

        @Override
        public SetRandomFireworkFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditions)
        {
            var fireworkColors = Lists.<Integer>newArrayList();

            if (object.has("colors"))
            {
                for (var jsonElement : GsonHelper.getAsJsonArray(object, "colors"))
                {
                    fireworkColors.add(jsonElement.getAsInt());
                }
            }
            return new SetRandomFireworkFunction(conditions, fireworkColors);
        }
    }
}