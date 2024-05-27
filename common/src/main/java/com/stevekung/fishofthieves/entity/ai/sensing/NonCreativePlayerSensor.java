package com.stevekung.fishofthieves.entity.ai.sensing;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.player.Player;

public class NonCreativePlayerSensor extends Sensor<LivingEntity>
{
    @Override
    public Set<MemoryModuleType<?>> requires()
    {
        return ImmutableSet.of(MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
    }

    @Override
    protected void doTick(ServerLevel level, LivingEntity entity)
    {
        var list = level.players().stream().filter(EntitySelector.NO_CREATIVE_OR_SPECTATOR).filter(serverPlayer -> entity.closerThan(serverPlayer, 16.0)).sorted(Comparator.comparingDouble(entity::distanceToSqr)).collect(Collectors.<Player>toList());
        var brain = entity.getBrain();
        brain.setMemory(MemoryModuleType.NEAREST_PLAYERS, list);
        var list2 = list.stream().filter(player -> isEntityTargetable(entity, player)).toList();
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER, list2.isEmpty() ? null : list2.getFirst());
        var optional = list2.stream().filter(player -> isEntityAttackable(entity, player)).findFirst();
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, optional);
    }
}