package com.stevekung.fishofthieves.forge.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class AddLootModifier extends LootModifier
{
    public static final MapCodec<AddLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> codecStart(instance).and(ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("loot_table").forGetter(m -> m.lootTable)).apply(instance, AddLootModifier::new));
    private final ResourceKey<LootTable> lootTable;

    public AddLootModifier(LootItemCondition[] conditions, ResourceKey<LootTable> lootTable)
    {
        super(conditions);
        this.lootTable = lootTable;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        var params = new LootParams.Builder(context.getLevel()).create(LootContextParamSets.EMPTY);
        context.getResolver().lookupOrThrow(Registries.LOOT_TABLE).getOrThrow(this.lootTable).value().getRandomItems(params, generatedLoot::add);
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec()
    {
        return CODEC;
    }
}