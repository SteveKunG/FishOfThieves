package com.stevekung.fishofthieves.forge.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class AddLootModifier extends LootModifier
{
    public static final Codec<AddLootModifier> CODEC = RecordCodecBuilder.create(instance -> codecStart(instance).and(ResourceLocation.CODEC.fieldOf("loot_table").forGetter(m -> m.lootTable)).apply(instance, AddLootModifier::new));
    private final ResourceLocation lootTable;

    public AddLootModifier(LootItemCondition[] conditions, ResourceLocation lootTable)
    {
        super(conditions);
        this.lootTable = lootTable;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        var params = new LootParams.Builder(context.getLevel()).create(LootContextParamSets.EMPTY);
        context.getResolver().getLootTable(this.lootTable).getRandomItems(params, generatedLoot::add);
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec()
    {
        return CODEC;
    }
}