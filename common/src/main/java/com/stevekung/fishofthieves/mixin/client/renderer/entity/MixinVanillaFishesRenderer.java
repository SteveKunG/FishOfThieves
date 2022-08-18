package com.stevekung.fishofthieves.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.stevekung.fishofthieves.client.model.ScaleableModel;
import com.stevekung.fishofthieves.client.renderer.entity.layers.HeadphoneLayer;
import com.stevekung.fishofthieves.entity.PartyFish;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.entity.Mob;

//@formatter:off
@Mixin(value = {
        CodRenderer.class,
        SalmonRenderer.class,
        PufferfishRenderer.class,
        TropicalFishRenderer.class
})
//@formatter:on
public abstract class MixinVanillaFishesRenderer<T extends Mob & PartyFish, M extends EntityModel<T> & ScaleableModel<T>> extends MobRenderer<T, M>
{
    MixinVanillaFishesRenderer()
    {
        super(null, null, 0);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void fishofthieves$addLayer(EntityRendererProvider.Context context, CallbackInfo info)
    {
        this.addLayer(new HeadphoneLayer<>(this, context));
    }
}