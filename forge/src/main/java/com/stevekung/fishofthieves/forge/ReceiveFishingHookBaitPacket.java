package com.stevekung.fishofthieves.forge;

import java.util.function.Supplier;

import com.stevekung.fishofthieves.network.FOTClientPackets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class ReceiveFishingHookBaitPacket
{
    private final int entityId;
    private final ItemStack itemStack;

    public ReceiveFishingHookBaitPacket(int entityId, ItemStack itemStack)
    {
        this.entityId = entityId;
        this.itemStack = itemStack;
    }

    public ReceiveFishingHookBaitPacket(FriendlyByteBuf buf)
    {
        this.entityId = buf.readInt();
        this.itemStack = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.entityId);
        buf.writeItem(this.itemStack);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx)
    {
        var minecraft = Minecraft.getInstance();
        ctx.get().enqueueWork(() -> FOTClientPackets.setFishingHookBait(minecraft, this.entityId, this.itemStack));
    }
}