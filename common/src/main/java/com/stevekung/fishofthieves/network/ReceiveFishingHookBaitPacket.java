package com.stevekung.fishofthieves.network;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record ReceiveFishingHookBaitPacket(int entityId, ItemStack itemStack) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<ReceiveFishingHookBaitPacket> TYPE = new CustomPacketPayload.Type<>(FishOfThieves.RECEIVE_FISHING_HOOK_BAIT);
    public static final StreamCodec<RegistryFriendlyByteBuf, ReceiveFishingHookBaitPacket> CODEC = CustomPacketPayload.codec(ReceiveFishingHookBaitPacket::write, ReceiveFishingHookBaitPacket::new);

    public ReceiveFishingHookBaitPacket(RegistryFriendlyByteBuf buf)
    {
        this(buf.readInt(), ItemStack.OPTIONAL_STREAM_CODEC.decode(buf));
    }

    public void write(RegistryFriendlyByteBuf buf)
    {
        buf.writeInt(this.entityId);
        ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, this.itemStack);
    }

    @Override
    public CustomPacketPayload.Type<ReceiveFishingHookBaitPacket> type()
    {
        return TYPE;
    }
}