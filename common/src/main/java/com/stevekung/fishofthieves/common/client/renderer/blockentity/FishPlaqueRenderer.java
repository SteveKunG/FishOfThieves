package com.stevekung.fishofthieves.common.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stevekung.fishofthieves.common.block.FishPlaqueBlock;
import com.stevekung.fishofthieves.common.blockentity.FishPlaqueBlockEntity;
import com.stevekung.fishofthieves.common.registry.FOTTags;
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

            poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());

            // rotate by facing state
            poseStack.mulPose(Axis.YP.rotationDegrees(yDegree));
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));

            if (isHorizontal)
            {
                switch (rotation)
                {
                    // TODO better solution for this??
                    case 0 -> poseStack.translate(-0.1, 0, 0);
                    case 1 -> poseStack.translate(-0.083125, 0, -0.1);
                    case 2 -> poseStack.translate(0, 0, -scale * 0.22);
                    case 3 -> poseStack.translate(0.073125, 0, -0.1);
                    case 4 -> poseStack.translate(0.123125, 0, 0);
                    case 5 -> poseStack.translate(0.083125, 0, 0.073125);
                    case 6 -> poseStack.translate(0, 0, -scale * -0.22);
                    case 7 -> poseStack.translate(-0.073125, 0, 0.073125);
                }

                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(rotation * 360.0F / 8.0F));
            }
            else
            {
                poseStack.mulPose(Axis.YP.rotationDegrees(-rotation * 360.0F / 8.0F));
            }

            poseStack.scale(scale, scale, scale);
            this.entityRenderer.render(entity, 0.0, 0.0, 0.0, 0.0F, 0.0f, poseStack, bufferSource, packedLight);
        }
        poseStack.popPose();
    }
}