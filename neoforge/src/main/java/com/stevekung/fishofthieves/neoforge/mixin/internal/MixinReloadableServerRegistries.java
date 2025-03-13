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
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;

import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.DynamicOps;
import com.stevekung.fishofthieves.neoforge.internal.lootapi.FabricLootTableBuilder;
import com.stevekung.fishofthieves.neoforge.proxy.CommonProxyNeoForge;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.world.level.storage.loot.LootTable;

@Mixin(ReloadableServerRegistries.class)
public class MixinReloadableServerRegistries
{
	/**
	 * Due to possible cross-thread handling, this uses WeakHashMap instead of ThreadLocal.
	 */
	@Unique
	private static final WeakHashMap<RegistryOps<JsonElement>, HolderLookup.Provider> WRAPPERS = new WeakHashMap<>();

	@WrapOperation(method = "reload", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/ReloadableServerRegistries$EmptyTagLookupWrapper;createSerializationContext(Lcom/mojang/serialization/DynamicOps;)Lnet/minecraft/resources/RegistryOps;"))
	private static RegistryOps<JsonElement> fishofthieves$storeOps(@Coerce HolderLookup.Provider registries, DynamicOps<JsonElement> ops, Operation<RegistryOps<JsonElement>> original)
	{
		var created = original.call(registries, ops);
		WRAPPERS.put(created, registries);
		return created;
	}

	@WrapOperation(method = "reload", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;thenApplyAsync(Ljava/util/function/Function;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;"))
	private static CompletableFuture<LayeredRegistryAccess<RegistryLayer>> fishofthieves$removeOps(CompletableFuture<List<WritableRegistry<?>>> future, Function<? super List<WritableRegistry<?>>, ? extends LayeredRegistryAccess<RegistryLayer>> fn, Executor executor, Operation<CompletableFuture<LayeredRegistryAccess<RegistryLayer>>> original, @Local RegistryOps<JsonElement> ops)
	{
		return original.call(future.thenApply(writableRegistries ->
		{
			WRAPPERS.remove(ops);
			return writableRegistries;
		}), fn, executor);
	}

	@WrapOperation(method = "method_58278(Lnet/minecraft/world/level/storage/loot/LootDataType;Lnet/minecraft/resources/RegistryOps;Lnet/minecraft/core/WritableRegistry;Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/JsonElement;)V", at = @At(value = "INVOKE", target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V"))
	private static <T> void fishofthieves$modifyLootTable(Optional<T> optionalTable, Consumer<? super T> action, Operation<Void> original, @Local(argsOnly = true) ResourceLocation id, @Local(argsOnly = true) RegistryOps<JsonElement> ops)
	{
		original.call(optionalTable.map(table -> modifyLootTable(table, id, ops)), action);
	}

	@SuppressWarnings("unchecked")
	@Unique
	private static <T> T modifyLootTable(T value, ResourceLocation id, RegistryOps<JsonElement> ops)
	{
		if (!(value instanceof LootTable table))
		{
			return value;
		}

		if (table == LootTable.EMPTY)
		{
			// This is a special table and cannot be modified.
			return value;
		}

		var key = ResourceKey.create(Registries.LOOT_TABLE, id);
		// Populated above.
		var registries = WRAPPERS.get(ops);

		// Turn the current table into a modifiable builder and invoke the MODIFY event.
		var builder = FabricLootTableBuilder.copyOf(table);
		CommonProxyNeoForge.modifyLoots(key, builder, registries);

		var newTable = (T) builder.build();
		var lootTableId = table.getLootTableId();
		((LootTable) newTable).setLootTableId(lootTableId);
		return newTable;
	}
}