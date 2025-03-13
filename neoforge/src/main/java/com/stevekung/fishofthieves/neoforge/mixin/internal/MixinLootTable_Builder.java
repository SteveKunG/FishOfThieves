/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stevekung.fishofthieves.neoforge.mixin.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import org.spongepowered.asm.mixin.*;

import com.google.common.collect.ImmutableList;
import com.stevekung.fishofthieves.neoforge.internal.lootapi.FabricLootPoolBuilder;
import com.stevekung.fishofthieves.neoforge.internal.lootapi.FabricLootTableBuilder;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

/**
 * The implementation of the injected interface {@link FabricLootTableBuilder}.
 * Simply implements the new methods by adding the relevant objects inside the lists.
 */
@Mixin(LootTable.Builder.class)
public class MixinLootTable_Builder implements FabricLootTableBuilder
{
    @Shadow
    @Final
    @Mutable
    ImmutableList.Builder<LootPool> pools;

    @Shadow
    @Final
    ImmutableList.Builder<LootItemFunction> functions;

    @Unique
    private LootTable.Builder self()
    {
        return (LootTable.Builder) (Object) this;
    }

    @Override
    public LootTable.Builder pool(LootPool pool)
    {
        this.pools.add(pool);
        return this.self();
    }

    @Override
    public LootTable.Builder apply(LootItemFunction function)
    {
        this.functions.add(function);
        return this.self();
    }

    @Override
    public void pools(Collection<? extends LootPool> pools)
    {
        this.pools.addAll(pools);
    }

    @Override
    public void apply(Collection<? extends LootItemFunction> functions)
    {
        this.functions.addAll(functions);
    }

    @Override
    public void modifyPools(Consumer<? super LootPool.Builder> modifier)
    {
        var list = new ArrayList<>(this.pools.build());
        var iterator = list.listIterator();

        while (iterator.hasNext())
        {
            var poolBuilder = FabricLootPoolBuilder.fishofthieves$copyOf(iterator.next());
            modifier.accept(poolBuilder);
            iterator.set(poolBuilder.build());
        }

        this.pools = ImmutableList.builder();
        this.pools.addAll(list);
    }
}