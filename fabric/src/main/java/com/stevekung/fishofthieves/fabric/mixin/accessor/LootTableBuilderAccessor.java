package com.stevekung.fishofthieves.fabric.mixin.accessor;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

@Mixin(LootTable.Builder.class)
public interface LootTableBuilderAccessor
{
    @Accessor("pools")
    List<LootPool> getPools();
}