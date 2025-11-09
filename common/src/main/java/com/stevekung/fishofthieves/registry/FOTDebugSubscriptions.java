package com.stevekung.fishofthieves.registry;

import java.util.List;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.network.DebugCenterPosStructureInfo;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.debug.DebugSubscription;

public class FOTDebugSubscriptions
{
    public static final DebugSubscription<List<DebugCenterPosStructureInfo>> STRUCTURE_CENTER_POS = registerWithValue("structure_center_pos", DebugCenterPosStructureInfo.STREAM_CODEC.apply(ByteBufCodecs.list()));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Debug Subscriptions");
    }

    private static <T> DebugSubscription<T> registerWithValue(String name, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec)
    {
        return Registry.register(BuiltInRegistries.DEBUG_SUBSCRIPTION, FishOfThieves.id(name), new DebugSubscription<>(streamCodec));
    }
}