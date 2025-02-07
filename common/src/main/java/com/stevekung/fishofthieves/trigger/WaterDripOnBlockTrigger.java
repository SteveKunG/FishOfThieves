package com.stevekung.fishofthieves.trigger;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTCriteriaTriggers;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WaterDripOnBlockTrigger extends SimpleCriterionTrigger<WaterDripOnBlockTrigger.TriggerInstance>
{
    @Override
    public Codec<TriggerInstance> codec()
    {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerLevel serverLevel, BlockPos blockPos, ServerPlayer player, BlockState state)
    {
        this.trigger(player, triggerInstance -> triggerInstance.matches(state, serverLevel, blockPos.getX(), blockPos.getY(), blockPos.getZ()));
    }

    @SuppressWarnings("deprecation")
    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<Holder<Block>> block, Optional<StatePropertiesPredicate> state, Optional<LocationPredicate> location) implements SimpleCriterionTrigger.SimpleInstance
    {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                BuiltInRegistries.BLOCK.holderByNameCodec().optionalFieldOf("block").forGetter(TriggerInstance::block),
                StatePropertiesPredicate.CODEC.optionalFieldOf("state").forGetter(TriggerInstance::state),
                LocationPredicate.CODEC.optionalFieldOf("location").forGetter(TriggerInstance::location)
        ).apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> waterDrip(Block block)
        {
            return FOTCriteriaTriggers.WATER_DRIP_ON_BLOCK.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(block.builtInRegistryHolder()), Optional.empty(), Optional.empty()));
        }

        public static Criterion<TriggerInstance> waterDrip(Block block, LocationPredicate.Builder locationPredicate)
        {
            return FOTCriteriaTriggers.WATER_DRIP_ON_BLOCK.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(block.builtInRegistryHolder()), Optional.empty(), Optional.of(locationPredicate.build())));
        }

        public boolean matches(BlockState state, ServerLevel serverLevel, double x, double y, double z)
        {
            return (this.block.isEmpty() || state.is(this.block.get())) && this.state.get().matches(state) && this.location.get().matches(serverLevel, x, y, z);
        }
    }
}