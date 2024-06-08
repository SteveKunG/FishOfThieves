package com.stevekung.fishofthieves.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stevekung.fishofthieves.block.FishPlaqueBlock;
import com.stevekung.fishofthieves.blockentity.FishPlaqueBlockEntity;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.phys.Vec3;

public class FishPlaqueRenderer implements BlockEntityRenderer<FishPlaqueBlockEntity>
{
    private final EntityRenderDispatcher entityRenderer;

    public FishPlaqueRenderer(BlockEntityRendererProvider.Context context)
    {
        this.entityRenderer = context.getEntityRenderer();
    }

    @Override
    public void render(FishPlaqueBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay)
    {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.0, 0.5);
        var entity = blockEntity.getOrCreateDisplayEntity(blockEntity.getLevel());

        if (entity != null)
        {
            var animationTick = blockEntity.getAnimation(partialTick);
            var blockState = blockEntity.getBlockState();
            var facing = blockState.getValue(FishPlaqueBlock.FACING);
            var rotation = blockState.getValue(FishPlaqueBlock.ROTATION) - 1;
            var isHorizontal = entity.getType().is(FOTTags.EntityTypes.HORIZONTAL_MOB_RENDER);
            var scale = 0.53125F;
            var stepMultiplier = isHorizontal ? 0.3f : 0.4f;
            var maxScale = Math.max(entity.getBbWidth(), entity.getBbHeight());
            var yDegree = -facing.toYRot() + 90f;
            var vec3 = new Vec3(facing.getStepX() * stepMultiplier, -scale, facing.getStepZ() * stepMultiplier);

            if (maxScale > 1.0f)
            {
                scale /= maxScale;
            }

            // Rotate by facing state
            poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());

            // Rotate by rotation state
            poseStack.mulPose(Axis.YP.rotationDegrees(yDegree));

            // Adjust rendered entity position a little bit
            poseStack.translate(0, -0.02f, 0);

            if (isHorizontal)
            {
                poseStack.mulPose(Axis.XP.rotationDegrees(rotation * 360.0F / 8.0F));
                poseStack.translate(0, -0.10f, 0);
            }
            else
            {
                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(-rotation * 360.0F / 8.0F));
            }

            entity.setYHeadRot(0);
            entity.setYBodyRot(0);
            poseStack.scale(scale, scale, scale);
            this.entityRenderer.render(entity, 0.0, 0.0, 0.0, 0.0F, animationTick, poseStack, bufferSource, packedLight);
        }
        poseStack.popPose();
    }
}