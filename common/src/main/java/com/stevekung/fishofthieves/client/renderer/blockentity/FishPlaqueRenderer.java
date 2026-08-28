package com.stevekung.fishofthieves.client.renderer.blockentity;

import org.jspecify.annotations.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stevekung.fishofthieves.block.FishPlaqueBlock;
import com.stevekung.fishofthieves.blockentity.FishPlaqueBlockEntity;
import com.stevekung.fishofthieves.client.renderer.blockentity.state.FishPlaqueBlockEntityRenderState;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.phys.Vec3;

public class FishPlaqueRenderer implements BlockEntityRenderer<FishPlaqueBlockEntity, FishPlaqueBlockEntityRenderState>
{
    private final EntityRenderDispatcher entityRenderer;

    public FishPlaqueRenderer(BlockEntityRendererProvider.Context context)
    {
        this.entityRenderer = context.entityRenderer();
    }

    @Override
    public void extractRenderState(FishPlaqueBlockEntity blockEntity, FishPlaqueBlockEntityRenderState fishPlaqueState, float partialTicks, Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay)
    {
        BlockEntityRenderer.super.extractRenderState(blockEntity, fishPlaqueState, partialTicks, vec3, crumblingOverlay);

        if (blockEntity.getLevel() != null)
        {
            var entity = blockEntity.getOrCreateDisplayEntity(blockEntity.getLevel());

            if (entity != null)
            {
                var entityType = entity.typeHolder();
                var maxScale = Math.max(entity.getBbWidth(), entity.getBbHeight());

                fishPlaqueState.scale = 0.53125F;

                if (maxScale > 1.0F)
                {
                    fishPlaqueState.scale /= maxScale;
                }

                var powered = blockEntity.getBlockState().getValue(FishPlaqueBlock.POWERED) && entityType.is(FOTTags.EntityTypes.FISH_PLAQUE_HORIZONTAL_RENDER_ON_POWERED);
                entity.fishofthieves$setIsInFishPlaque(powered);
                fishPlaqueState.isHorizontal = entity.isInWater() || entityType.is(FOTTags.EntityTypes.FISH_PLAQUE_HORIZONTAL_RENDER);
                fishPlaqueState.displayEntity = this.entityRenderer.extractEntity(entity, blockEntity.getAnimation(partialTicks));
                fishPlaqueState.displayEntity.lightCoords = fishPlaqueState.lightCoords;

                if (fishPlaqueState.displayEntity instanceof LivingEntityRenderState livingEntityRenderState)
                {
                    livingEntityRenderState.bodyRot = 0f;
                }
            }
        }
    }

    @Override
    public void submit(FishPlaqueBlockEntityRenderState fishPlaqueState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState)
    {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.0, 0.5);
        var entityRenderState = fishPlaqueState.displayEntity;

        if (entityRenderState != null)
        {
            var blockState = fishPlaqueState.blockState;
            var facing = blockState.getValue(FishPlaqueBlock.FACING);
            var rotation = blockState.getValue(FishPlaqueBlock.ROTATION) - 1;
            var scale = fishPlaqueState.scale;

            var isHorizontal = fishPlaqueState.isHorizontal;
            var stepMultiplier = isHorizontal ? 0.3f : 0.4f;
            var yDegree = -facing.toYRot() + 90f;
            var vec3Translate = new Vec3(facing.getStepX() * stepMultiplier, -scale, facing.getStepZ() * stepMultiplier);

            // Rotate by facing state
            poseStack.translate(-vec3Translate.x(), -vec3Translate.y(), -vec3Translate.z());

            // Rotate by rotation state
            poseStack.rotate(Axis.YP.rotationDegrees(yDegree));

            // Adjust rendered entity position a little bit
            poseStack.translate(0, -0.02f, 0);

            if (isHorizontal)
            {
                poseStack.rotate(Axis.XP.rotationDegrees(rotation * 360.0F / 8.0F));
                poseStack.translate(0, -0.10f, 0);
            }
            else
            {
                poseStack.rotate(Axis.ZP.rotationDegrees(90.0F));
                poseStack.rotate(Axis.YP.rotationDegrees(-rotation * 360.0F / 8.0F));
            }

            poseStack.scale(scale, scale, scale);
            this.entityRenderer.submit(entityRenderState, cameraRenderState, 0.0, 0.0, 0.0, poseStack, submitNodeCollector);
        }
        poseStack.popPose();
    }

    @Override
    public FishPlaqueBlockEntityRenderState createRenderState()
    {
        return new FishPlaqueBlockEntityRenderState();
    }
}