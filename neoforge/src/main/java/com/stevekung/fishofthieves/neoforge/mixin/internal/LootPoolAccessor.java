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

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.stevekung.fishofthieves.neoforge.internal.lootapi.FabricLootPoolBuilder;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

/**
 * Accesses loot pool fields for {@link FabricLootPoolBuilder#copyOf(LootPool)}.
 * These are normally available in the transitive access widener module.
 */
@Mixin(LootPool.class)
public interface LootPoolAccessor
{
    @Accessor("rolls")
    NumberProvider fabric_getRolls();

    @Accessor("bonusRolls")
    NumberProvider fabric_getBonusRolls();

    @Accessor("entries")
    List<LootPoolEntryContainer> fabric_getEntries();

    @Accessor("conditions")
    List<LootItemCondition> fabric_getConditions();

    @Accessor("functions")
    List<LootItemFunction> fabric_getFunctions();
}