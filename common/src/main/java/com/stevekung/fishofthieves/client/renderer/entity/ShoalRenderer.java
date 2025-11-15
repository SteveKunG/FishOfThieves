package com.stevekung.fishofthieves.client.renderer.entity;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.mixin.client.accessor.EntityRenderDispatcherAccessor;
import com.stevekung.fishofthieves.registry.FOTEntities;

import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class ShoalRenderer extends EntityRenderer<Shoal>
{
    private static final List<EntityType<?>> TIER_1_FISH = List.of(FOTEntities.PONDIE, FOTEntities.ANCIENTSCALE, FOTEntities.WRECKER, FOTEntities.DEVILFISH, FOTEntities.ISLEHOPPER);
    private static final List<EntityType<?>> TIER_2_FISH = List.of(FOTEntities.SPLASHTAIL, FOTEntities.WILDSPLASH, FOTEntities.BATTLEGILL, FOTEntities.PLENTIFIN, FOTEntities.STORMFISH);

    private static final Function<Level, List<Entity>> TIER1 = Util.memoize(level -> pickRandom(TIER_1_FISH).stream().map(entityType -> (Entity) entityType.create(level)).peek(entity -> entity.wasTouchingWater = true).toList());
    private static final Function<Level, List<Entity>> TIER2 = Util.memoize(level -> pickRandom(TIER_2_FISH).stream().map(entityType -> (Entity) entityType.create(level)).peek(entity -> entity.wasTouchingWater = true).toList());

    private final EntityRenderDispatcher entityRenderDispatcher;

    public ShoalRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        this.entityRenderDispatcher = context.getEntityRenderDispatcher();
    }

    // For petals rotation equations credit to https://github.com/VazkiiMods/Botania/blob/1.20.x/Xplat/src/main/java/vazkii/botania/client/render/block_entity/PetalApothecaryBlockEntityRenderer.java#L63
    // Under the Botania license.
    @Override
    public void render(Shoal shoal, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        poseStack.pushPose();
        var ageInTicks = shoal.tickCount + partialTicks;
        var list = (shoal.getId() % 2 == 0 ? TIER1 : TIER2).apply(shoal.level());

        var offsetPerFish = 360F / list.size();
        var modifier = 15F;
        var rotationModifier = 0.25F;
        var radiusBase = 1.0F;
        var radiusMod = 0.1F;
        var v = 1F / 8F;

        poseStack.translate(-0.05F, 0F, 0F);

        for (var index = 0; index < list.size(); index++)
        {
            var entity = list.get(index);

            if (entity instanceof ThievesFish<?> thievesFish)
            {
                thievesFish.setTrophy(true);
            }

            var offset = offsetPerFish * index;
            float deg = (int) (ageInTicks / rotationModifier % 360F + offset);
            var rad = (float) (deg / 180F * Math.PI);
            var radiusX = (float) (radiusBase + radiusMod * Math.sin(ageInTicks / modifier));
            var radiusZ = (float) (radiusBase + radiusMod * Math.cos(ageInTicks / modifier));
            var x = (float) (radiusX * Math.cos(rad));
            var z = (float) (radiusZ * Math.sin(rad));
            var y = (float) Math.cos((ageInTicks + 50 * index) / 5F) / 10F;

            poseStack.pushPose();
            poseStack.translate(x, y, z);
            var yRotate = Math.max(0.6F, Mth.sin(ageInTicks * 0.1f) / 4.0F);
            var zRotate = (float) Math.cos(0.1f * ageInTicks) / 16F;

            v /= 2F;
            poseStack.translate(v, v, v);
            poseStack.mulPose(new Quaternionf().rotateAxis(-rad, 0, yRotate, zRotate));
            poseStack.translate(-v, -v, -v);
            v *= 2F;

            this.renderEntityInShoal(entity, ageInTicks, entityYaw, partialTicks, poseStack, buffer, packedLight);
            poseStack.popPose();
        }

        poseStack.popPose();
        super.render(shoal, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void renderEntityInShoal(Entity entity, float ageInTicks, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight)
    {
        var renderer = (EntityRenderer<? super Entity>) ((EntityRenderDispatcherAccessor) this.entityRenderDispatcher).getRenderers().get(entity.getType());
        renderer.render(entity, entityYaw, ageInTicks, poseStack, buffer, packedLight);

        if (entity instanceof LivingEntity livingEntity && renderer instanceof LivingEntityRenderer livingEntityRenderer)
        {
            var yBodyRot = Mth.rotLerp(partialTicks, livingEntity.yBodyRotO, livingEntity.yBodyRot);
            var yHeadRot = Mth.rotLerp(partialTicks, livingEntity.yHeadRotO, livingEntity.yHeadRot);
            var netHeadYaw = yHeadRot - yBodyRot;
            var limbSwingAmount = 0.0F;
            var limbSwing = 0.0F;
            var headPitch = Mth.lerp(partialTicks, livingEntity.xRotO, livingEntity.getXRot());

            if (LivingEntityRenderer.isEntityUpsideDown(livingEntity))
            {
                headPitch *= -1.0F;
                netHeadYaw *= -1.0F;
            }

            if (!livingEntity.isPassenger() && livingEntity.isAlive())
            {
                limbSwingAmount = livingEntity.walkAnimation.speed(partialTicks);
                limbSwing = livingEntity.walkAnimation.position(partialTicks);

                if (livingEntity.isBaby())
                {
                    limbSwing *= 3.0F;
                }

                if (limbSwingAmount > 1.0F)
                {
                    limbSwingAmount = 1.0F;
                }
            }

            var model = livingEntityRenderer.getModel();
            model.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
            model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Shoal entity)
    {
        return null;
    }

    private static List<? extends EntityType<?>> pickRandom(List<EntityType<?>> list)
    {
        return new Random().ints(0, list.size()).distinct().limit(3).mapToObj(list::get).toList();
    }
}