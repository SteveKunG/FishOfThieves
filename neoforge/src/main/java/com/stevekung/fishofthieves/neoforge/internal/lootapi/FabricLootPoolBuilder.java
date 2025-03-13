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

package com.stevekung.fishofthieves.neoforge.internal.lootapi;

import java.util.Collection;

import org.jetbrains.annotations.ApiStatus;

import com.stevekung.fishofthieves.neoforge.mixin.internal.LootPoolAccessor;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/**
 * Convenience extensions to {@link LootPool.Builder}
 * for adding pre-built objects or collections.
 *
 * <p>This interface is automatically injected to {@link LootPool.Builder}.
 */
@SuppressWarnings("unused")
@ApiStatus.NonExtendable
public interface FabricLootPoolBuilder
{
	/**
	 * Adds an entry to this builder.
	 *
	 * @param entry the added loot entry
	 * @return this builder
	 */
	default LootPool.Builder fishofthieves$with(LootPoolEntryContainer entry)
	{
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	/**
	 * Adds entries to this builder.
	 *
	 * @param entries the added loot entries
	 * @return this builder
	 */
	default LootPool.Builder fishofthieves$with(Collection<? extends LootPoolEntryContainer> entries)
	{
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	/**
	 * Adds a condition to this builder.
	 *
	 * @param condition the added condition
	 * @return this builder
	 */
	default LootPool.Builder fishofthieves$conditionally(LootItemCondition condition)
	{
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	/**
	 * Adds conditions to this builder.
	 *
	 * @param conditions the added conditions
	 * @return this builder
	 */
	default LootPool.Builder fishofthieves$conditionally(Collection<? extends LootItemCondition> conditions)
	{
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	/**
	 * Applies a function to this builder.
	 *
	 * @param function the applied loot function
	 * @return this builder
	 */
	default LootPool.Builder fishofthieves$apply(LootItemFunction function)
	{
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	/**
	 * Applies loot functions to this builder.
	 *
	 * @param functions the applied loot functions
	 * @return this builder
	 */
	default LootPool.Builder fishofthieves$apply(Collection<? extends LootItemFunction> functions)
	{
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	/**
	 * Creates a builder copy of a loot pool.
	 *
	 * @param pool the loot pool
	 * @return the copied builder
	 */
	static LootPool.Builder fishofthieves$copyOf(LootPool pool)
	{
        var accessor = (LootPoolAccessor) pool;
		return LootPool.lootPool().setRolls(accessor.fabric_getRolls()).setBonusRolls(accessor.fabric_getBonusRolls()).fishofthieves$with(accessor.fabric_getEntries()).fishofthieves$conditionally(accessor.fabric_getConditions()).fishofthieves$apply(accessor.fabric_getFunctions());
	}
}