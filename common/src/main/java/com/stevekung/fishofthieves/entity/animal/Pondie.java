package com.stevekung.fishofthieves.entity.animal;

import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.FishData;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Pondie extends AbstractSchoolingThievesFish<FishData>
{
//    private static final Map<FishData, ResourceLocation> GLOW_BY_TYPE = Collections.singletonMap(Variant.MOONSKY, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/pondie/moonsky_glow.png"));

    public Pondie(EntityType<? extends Pondie> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.PONDIE_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.PONDIE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.PONDIE_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.PONDIE_FLOP;
    }

    @Override
    public int getMaxSchoolSize()
    {
        return 5;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.35F, 0.25F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.35F : 0.18F;
    }

//    @Override
//    public boolean canGlow()
//    {
//        return this.getVariant() == Variant.MOONSKY;
//    }
//
//    @Override
//    public Variant getVariant()
//    {
//        return Variant.BY_ID[Mth.positiveModulo(this.entityData.get(TYPE), Variant.BY_ID.length)];
//    }


    @Override
    public FishData getVariant()
    {
        return null;
    }

    @Override
    public void setVariant(FishData variant)
    {

    }

    @Override
    public Holder<FishData> getSpawnVariant(boolean bucket)
    {
        return null;
    }

    @Override
    public Registry<FishData> getRegistry()
    {
        return null;
    }

    //    @Override
//    public int getSpawnVariantId(boolean bucket)
//    {
//        return ThievesFish.getSpawnVariant(this, Variant.BY_ID, Variant[]::new, bucket);
//    }
//
//    @Override
//    public Map<FishData, ResourceLocation> getGlowTextureByType()
//    {
//        return GLOW_BY_TYPE;
//    }

//    public enum Variant implements FishData
//    {
//        CHARCOAL(SpawnSelectors.always()),
//        ORCHID(SpawnSelectors.always()),
//        BRONZE(SpawnSelectors.always()),
//        BRIGHT(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.brightPondieProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.brightPondieProbability).and(SpawnSelectors.dayAndSeeSky()))),
//        MOONSKY(SpawnSelectors.nightAndSeeSky());
//
//        public static final Variant[] BY_ID = Stream.of(values()).sorted(Comparator.comparingInt(Variant::getId)).toArray(Variant[]::new);
//        private final Predicate<SpawnConditionContext> condition;
//
//        Variant(Predicate<SpawnConditionContext> condition)
//        {
//            this.condition = condition;
//        }
//
//        @Override
//        public String getName()
//        {
//            return this.name().toLowerCase(Locale.ROOT);
//        }
//
//        @Override
//        public int getId()
//        {
//            return this.ordinal();
//        }
//
//        @Override
//        public Predicate<SpawnConditionContext> getCondition()
//        {
//            return this.condition;
//        }
//    }
}