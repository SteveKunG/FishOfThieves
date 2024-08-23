package com.stevekung.fishofthieves.mixin.client.model;

import org.spongepowered.asm.mixin.Mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.client.model.HeadphoneModel;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

//@formatter:off
@Mixin(value = {
        CodModel.class,
        SalmonModel.class,
        PufferfishSmallModel.class,
        PufferfishMidModel.class,
        PufferfishBigModel.class,
        TropicalFishModelA.class,
        TropicalFishModelB.class
})
//@formatter:on
public class MixinVanillaFishesModel<S extends LivingEntityRenderState> implements HeadphoneModel.Scaleable<S>
{
    /**
     * @param renderState
     * @param poseStack
     */
    @Override
    public void scale(S renderState, PoseStack poseStack)
    {

    }
    //    @Override TODO
//    public void scale(S renderState, PoseStack poseStack)
//    {
//        var type = entity.getType();
//        var scale = 1.0f;
//        var y = 0.0f;
//        var z = 0.0f;
//
//        if (type == EntityType.COD)
//        {
//            y = 0.25f;
//            z = -0.05f;
//        }
//        else if (type == EntityType.SALMON)
//        {
//            scale = 1.5f;
//            y = -0.275f;
//        }
//        else if (entity instanceof Pufferfish pufferfish)
//        {
//            z = -0.025f;
//
//            switch (pufferfish.getPuffState())
//            {
//                case 0 ->
//                {
//                    y = 0.25f;
//                    poseStack.scale(scale + 0.5f, scale, scale - 0.25f);
//                }
//                case 1 ->
//                {
//                    scale = 1.5f;
//                    y = -0.275f;
//                    poseStack.scale(scale - 0.25f, scale - 0.5f, scale - 0.5f);
//                }
//                case 2 ->
//                {
//                    y = -0.55f;
//                    poseStack.scale(3.0f, 2.0f, 2.0f);
//                }
//            }
//        }
//        else if (entity instanceof TropicalFish tropicalFish)
//        {
//            var baseVariant = tropicalFish.getVariant().base();
//
//            if (baseVariant == TropicalFish.Base.SMALL)
//            {
//                y = 0.275f;
//                z = -0.08f;
//            }
//            else
//            {
//                poseStack.scale(scale, scale + 0.5f, scale);
//                y = -0.325f;
//                z = -0.1f;
//            }
//        }
//        poseStack.scale(scale, scale, scale);
//        poseStack.translate(0.0f, y, z);
//    }
}