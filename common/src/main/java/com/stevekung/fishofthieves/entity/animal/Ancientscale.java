package com.stevekung.fishofthieves.entity.animal;

import java.util.function.Consumer;

import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.registry.FOTDataSerializers;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistry;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.registry.variants.AncientscaleVariant;
import com.stevekung.fishofthieves.registry.variants.FishVariantTags;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

public class Ancientscale extends AbstractSchoolingThievesFish<AncientscaleVariant>
{
    private static final EntityDataAccessor<AncientscaleVariant> VARIANT = SynchedEntityData.defineId(Ancientscale.class, FOTDataSerializers.ANCIENTSCALE_VARIANT);
    public static final Consumer<Int2ObjectOpenHashMap<String>> DATA_FIX_MAP = map ->
    {
        map.defaultReturnValue("fishofthieves:almond");
        map.put(0, "fishofthieves:almond");
        map.put(1, "fishofthieves:sapphire");
        map.put(2, "fishofthieves:smoke");
        map.put(3, "fishofthieves:bone");
        map.put(4, "fishofthieves:starshine");
    };

    public Ancientscale(EntityType<? extends Ancientscale> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, LEECHES_FOOD, false));
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(VARIANT, AncientscaleVariant.ALMOND);
    }

    @Override
    public Registry<AncientscaleVariant> getRegistry()
    {
        return FOTRegistry.ANCIENTSCALE_VARIANT;
    }

    @Override
    public void setVariant(AncientscaleVariant variant)
    {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public AncientscaleVariant getVariant()
    {
        return this.entityData.get(VARIANT);
    }

    @Override
    public Holder<AncientscaleVariant> getSpawnVariant(boolean fromBucket)
    {
        return this.getSpawnVariant(this, FishVariantTags.DEFAULT_ANCIENTSCALE_SPAWNS, AncientscaleVariant.ALMOND, fromBucket);
    }

    @Override
    public Consumer<Int2ObjectOpenHashMap<String>> getDataFix()
    {
        return DATA_FIX_MAP;
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.ANCIENTSCALE_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.ANCIENTSCALE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.ANCIENTSCALE_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.ANCIENTSCALE_FLOP;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.3F, 0.25F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.3575F : 0.18F;
    }

    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return LEECHES_FOOD.test(itemStack);
    }

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        var waterRules = WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, random);
        return waterRules || TerrainUtils.isInFeature((ServerLevel) levelAccessor, blockPos, StructureTags.MINESHAFT) || TerrainUtils.isInFeature((ServerLevel) levelAccessor, blockPos, BuiltinStructures.STRONGHOLD);
    }
}