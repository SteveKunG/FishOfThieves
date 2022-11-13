package com.stevekung.fishofthieves.entity.animal;

import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;
import com.google.common.collect.Maps;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractThievesFish;
import com.stevekung.fishofthieves.entity.FishVariant;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.spawn.SpawnConditionContext;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ConfiguredStructureTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.phys.Vec3;

public class Wrecker extends AbstractThievesFish
{
    private static final Map<FishVariant, ResourceLocation> GLOW_BY_TYPE = Util.make(Maps.newHashMap(), map ->
    {
        map.put(Variant.SUN, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/wrecker/sun_glow.png"));
        map.put(Variant.BLACKCLOUD, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/wrecker/blackcloud_glow.png"));
        map.put(Variant.SNOW, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/wrecker/snow_glow.png"));
        map.put(Variant.MOON, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/wrecker/moon_glow.png"));
    });
    private static final Predicate<LivingEntity> SELECTORS = livingEntity -> livingEntity.getMobType() != MobType.WATER && livingEntity.isInWater() && livingEntity.attackable();

    public Wrecker(EntityType<? extends Wrecker> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 2.0d, true));
        this.goalSelector.addGoal(2, new SwimToNearbyWreckageGoal(this, 1.25d));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25d, EARTHWORMS_FOOD, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Monster.class, 20, true, false, SELECTORS));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, 50, true, false, SELECTORS));
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.WRECKER_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.WRECKER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.WRECKER_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.WRECKER_FLOP;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.275F, 0.25F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.35F : 0.175F;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag)
    {
        spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);

        if (this.isTrophy())
        {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.5d);
        }
        return spawnData;
    }

    @Override
    public void setTrophy(boolean trophy)
    {
        if (trophy)
        {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.5d);
        }
        super.setTrophy(trophy);
    }

    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return EARTHWORMS_FOOD.test(itemStack);
    }

    @Override
    public boolean canGlow()
    {
        return this.getVariant() != Variant.ROSE;
    }

    @Override
    public Variant getVariant()
    {
        return Variant.BY_ID[Mth.positiveModulo(this.entityData.get(TYPE), Variant.BY_ID.length)];
    }

    @Override
    public int getSpawnVariantId(boolean bucket)
    {
        return ThievesFish.getSpawnVariant(this, Variant.BY_ID, Variant[]::new, bucket);
    }

    @Override
    public Map<FishVariant, ResourceLocation> getGlowTextureByType()
    {
        return GLOW_BY_TYPE;
    }

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, Random random)
    {
        return TerrainUtils.isInFeature((ServerLevel) levelAccessor, blockPos, BuiltinStructures.SHIPWRECK) || TerrainUtils.isInFeature((ServerLevel) levelAccessor, blockPos, BuiltinStructures.RUINED_PORTAL_OCEAN) && levelAccessor.getFluidState(blockPos).is(FluidTags.WATER);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0).add(Attributes.FOLLOW_RANGE, 10.0).add(Attributes.ATTACK_DAMAGE, 1.5).add(Attributes.ATTACK_KNOCKBACK, 0.01);
    }

    static class SwimToNearbyWreckageGoal extends RandomStrollGoal
    {
        SwimToNearbyWreckageGoal(Wrecker wrecker, double speedModifier)
        {
            super(wrecker, speedModifier);
        }

        @Override
        @Nullable
        protected Vec3 getPosition()
        {
            var shipwreckPos = ((ServerLevel) this.mob.level).findNearestMapFeature(ConfiguredStructureTags.SHIPWRECK, this.mob.blockPosition(), 32, false);
            var ruinedPortalPos = ((ServerLevel) this.mob.level).findNearestMapFeature(ConfiguredStructureTags.RUINED_PORTAL, this.mob.blockPosition(), 32, false);

            if (this.mob.level.random.nextFloat() < 0.3f)
            {
                return LandRandomPos.getPos(this.mob, 10, 7);
            }
            if (shipwreckPos != null)
            {
                return Vec3.atCenterOf(shipwreckPos);
            }
            if (ruinedPortalPos != null)
            {
                return Vec3.atCenterOf(ruinedPortalPos);
            }
            return super.getPosition();
        }
    }

    public enum Variant implements FishVariant
    {
        ROSE(SpawnSelectors.always()),
        SUN(SpawnSelectors.simpleSpawn(SpawnSelectors.dayAndSeeSky())),
        BLACKCLOUD(SpawnSelectors.thunderingAndSeeSky()),
        SNOW(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.snowWreckerProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.snowWreckerProbability).and(SpawnSelectors.biomeTag(FOTTags.SPAWNS_SNOW_WRECKERS)))),
        MOON(SpawnSelectors.simpleSpawn(true, SpawnSelectors.nightAndSeeSky().and(context -> context.level().getMoonBrightness() > 0F)));

        public static final Variant[] BY_ID = Stream.of(values()).sorted(Comparator.comparingInt(Variant::getId)).toArray(Variant[]::new);
        private final Predicate<SpawnConditionContext> condition;

        Variant(Predicate<SpawnConditionContext> condition)
        {
            this.condition = condition;
        }

        @Override
        public String getName()
        {
            return this.name().toLowerCase(Locale.ROOT);
        }

        @Override
        public int getId()
        {
            return this.ordinal();
        }

        @Override
        public Predicate<SpawnConditionContext> getCondition()
        {
            return this.condition;
        }
    }
}