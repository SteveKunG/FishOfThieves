package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.mixin.client.accessor.EntityRenderDispatcherAccessor;
import com.stevekung.fishofthieves.registry.FOTEntities;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class ShoalRenderer extends EntityRenderer<Shoal>
{
    // tier 1
    private static final Function<Level, List<Entity>> SET1 = level -> Stream.of(FOTEntities.PONDIE, FOTEntities.ANCIENTSCALE, FOTEntities.WRECKER).map(entityType -> (Entity) entityType.create(level)).toList();
    private static final Function<Level, List<Entity>> SET11 = level -> Stream.of(FOTEntities.PONDIE, FOTEntities.DEVILFISH, FOTEntities.WRECKER).map(entityType -> (Entity) entityType.create(level)).toList();

    // tier 2
    private static final Function<Level, List<Entity>> SET2 = level -> Stream.of(FOTEntities.SPLASHTAIL, FOTEntities.WILDSPLASH, FOTEntities.BATTLEGILL).map(entityType -> (Entity) entityType.create(level)).toList();
    private static final Function<Level, List<Entity>> SET3 = level -> Stream.of(FOTEntities.SPLASHTAIL, FOTEntities.PLENTIFIN, FOTEntities.STORMFISH).map(entityType -> (Entity) entityType.create(level)).toList();
    private final EntityRenderDispatcher entityRenderDispatcher;

    public ShoalRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        this.entityRenderDispatcher = context.getEntityRenderDispatcher();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void render(Shoal shoal, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        poseStack.pushPose();
        var ageInTicks = shoal.tickCount + partialTicks;
        var list = SET3.apply(shoal.level());
        poseStack.translate((float) 3 / list.size(), 0, 0);

        for (var index = 0; index < list.size(); index++)
        {
            var entity = list.get(index);

            if (entity instanceof ThievesFish<?> thievesFish)
            {
                thievesFish.setTrophy(true);
            }

            entity.wasTouchingWater = true;

            float circleRadius = 2.0f;
            poseStack.rotateAround(Axis.YN.rotationDegrees(ageInTicks), (float) (0.2f * -Math.PI * circleRadius), 0.0f, 0.0F);

            var renderer = (EntityRenderer<? super Entity>) ((EntityRenderDispatcherAccessor) this.entityRenderDispatcher).getRenderers().get(entity.getType());
            renderer.render(entity, entityYaw, ageInTicks, poseStack, buffer, packedLight);

            if (entity instanceof LivingEntity livingEntity && renderer instanceof LivingEntityRenderer livingEntityRenderer)
            {
                var f = Mth.rotLerp(partialTicks, livingEntity.yBodyRotO, livingEntity.yBodyRot);
                var g = Mth.rotLerp(partialTicks, livingEntity.yHeadRotO, livingEntity.yHeadRot);
                var h = g - f;
                var k = 0.0F;
                var l = 0.0F;
                var j = Mth.lerp(partialTicks, livingEntity.xRotO, livingEntity.getXRot());

                if (LivingEntityRenderer.isEntityUpsideDown(livingEntity))
                {
                    j *= -1.0F;
                    h *= -1.0F;
                }

                if (!livingEntity.isPassenger() && livingEntity.isAlive())
                {
                    k = livingEntity.walkAnimation.speed(partialTicks);
                    l = livingEntity.walkAnimation.position(partialTicks);

                    if (livingEntity.isBaby())
                    {
                        l *= 3.0F;
                    }

                    if (k > 1.0F)
                    {
                        k = 1.0F;
                    }
                }

                var model = livingEntityRenderer.getModel();
                model.prepareMobModel(livingEntity, l, k, partialTicks);
                model.setupAnim(livingEntity, l, k, ageInTicks, h, j);
            }
        }

        poseStack.popPose();
        super.render(shoal, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Shoal entity)
    {
        return null;
    }
}