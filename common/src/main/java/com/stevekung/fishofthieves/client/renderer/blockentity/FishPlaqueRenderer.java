package com.stevekung.fishofthieves.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stevekung.fishofthieves.block.FishPlaqueBlock;
import com.stevekung.fishofthieves.blockentity.FishPlaqueBlockEntity;
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
            var scale = 0.53125F;
            var stepMultiplier = 0.4f;
            var vec3 = new Vec3(facing.getStepX() * stepMultiplier, -scale, facing.getStepZ() * stepMultiplier);
            var maxScale = Math.max(entity.getBbWidth(), entity.getBbHeight());
            var yDegree = -facing.toYRot() + 90f;

            if (maxScale > 1.0f)
            {
                scale /= maxScale;
            }

            poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());

            // rotate by facing state
            poseStack.mulPose(Vector3f.YP.rotationDegrees(yDegree));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(-rotation * 360.0F / 8.0F));

            poseStack.scale(scale, scale, scale);
            this.entityRenderer.render(entity, 0.0, 0.0, 0.0, 0.0F, 0.0f, poseStack, bufferSource, packedLight);
        }
        poseStack.popPose();
    }
}