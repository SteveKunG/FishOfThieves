package com.stevekung.fishofthieves.forge.mixin.item;

import org.spongepowered.asm.mixin.Mixin;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import com.stevekung.fishofthieves.item.FOTWormItem;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.teammetallurgy.aquaculture.api.bait.IBaitItem;

@Mixin(FOTWormItem.class)
@IfModLoaded(value = "aquaculture")
public class MixinFOTWormItem implements IBaitItem
{
    @Override
    public int getLureSpeedModifier()
    {
        var item = FOTWormItem.class.cast(this);

        if (item == FOTItems.EARTHWORMS || item == FOTItems.LEECHES)
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }
}