package com.stevekung.fishofthieves.entity.animal;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.variant.DevilfishVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.DevilfishVariants;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class Devilfish extends AbstractSchoolingThievesFish<DevilfishVariant>
{
    private static final Predicate<LivingEntity> SELECTORS = livingEntity -> livingEntity instanceof Enemy && livingEntity.getMobType() == MobType.UNDEAD && livingEntity.isInWater() && livingEntity.attackable();
    private static final EntityDataAccessor<DevilfishVariant> VARIANT = SynchedEntityData.defineId(Devilfish.class, FOTDataSerializers.DEVILFISH_VARIANT);
    public static final Consumer<Int2ObjectOpenHashMap<String>> DATA_FIX_MAP = map ->
    {
        map.defaultReturnValue("fishofthieves:ashen");
        map.put(0, "fishofthieves:ashen");
        map.put(1, "fishofthieves:seashell");
        map.put(2, "fishofthieves:lava");
        map.put(3, "fishofthieves:forsaken");
        map.put(4, "fishofthieves:firelight");
    };

    public Devilfish(EntityType<? extends Devilfish> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, LEECHES_FOOD, false));

        if (FishOfThieves.CONFIG.general.neutralFishBehavior)
        {
            this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5f, true));
            this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, Monster.class, 20, true, false, SELECTORS));
        }
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(VARIANT, DevilfishVariants.ASHEN);
    }

    @Override
    public Registry<DevilfishVariant> getRegistry()
    {
        return FOTRegistry.DEVILFISH_VARIANT;
    }

    @Override
    public void setVariant(DevilfishVariant variant)
    {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public DevilfishVariant getVariant()
    {
        return this.entityData.get(VARIANT);
    }

    @Override
    public Holder<DevilfishVariant> getSpawnVariant(boolean fromBucket)
    {
        return this.getSpawnVariant(this, FOTTags.FishVariant.DEFAULT_DEVILFISH_SPAWNS, DevilfishVariants.ASHEN, fromBucket);
    }

    @Override
    public Consumer<Int2ObjectOpenHashMap<String>> getDataFix()
    {
        return DATA_FIX_MAP;
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.DEVILFISH_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.DEVILFISH_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.DEVILFISH_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.DEVILFISH_FLOP;
    }

    @Override
    public int getMaxSchoolSize()
    {
        return 4;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.275F, 0.275F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.375F : 0.18F;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag)
    {
        spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);

        if (this.isTrophy())
        {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.0d);
        }
        return spawnData;
    }

    @Override
    public void setTrophy(boolean trophy)
    {
        if (trophy)
        {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.0d);
        }
        super.setTrophy(trophy);
    }

    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return LEECHES_FOOD.test(itemStack);
    }

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, LevelAccessor level, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        var isWater = level.getFluidState(blockPos.below()).is(FluidTags.WATER) && level.getBlockState(blockPos.above()).is(Blocks.WATER);
        return isWater && blockPos.getY() <= 0 && !level.getBiome(blockPos).is(FOTTags.Biomes.DEVILFISH_CANNOT_SPAWN);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0).add(Attributes.FOLLOW_RANGE, 10.0).add(Attributes.ATTACK_DAMAGE, 1.0).add(Attributes.ATTACK_KNOCKBACK, 0.01);
    }
}