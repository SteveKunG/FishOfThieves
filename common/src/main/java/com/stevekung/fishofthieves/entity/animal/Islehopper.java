package com.stevekung.fishofthieves.entity.animal;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractThievesFish;
import com.stevekung.fishofthieves.entity.FishData;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.spawn.SpawnConditionContext;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.PeakTypes;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;

public class Islehopper extends AbstractThievesFish<FishData>
{
//    private static final Map<FishData, ResourceLocation> GLOW_BY_TYPE = Collections.singletonMap(Variant.AMETHYST, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/islehopper/amethyst_glow.png"));

    public Islehopper(EntityType<? extends Islehopper> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.ISLEHOPPER_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.ISLEHOPPER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.ISLEHOPPER_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.ISLEHOPPER_FLOP;
    }

    @Override
    public boolean skipAttackInteraction(Entity entity)
    {
        var multiplier = this.isTrophy() ? 2 : 1;

        if (entity instanceof ServerPlayer serverPlayer && entity.hurt(DamageSource.mobAttack(this), multiplier))
        {
            if (!this.isSilent())
            {
                serverPlayer.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.PUFFER_FISH_STING, 0.0f));
            }
            serverPlayer.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * multiplier, 0), this);
        }
        return false;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.3F, 0.2F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.29F : 0.15F;
    }

//    @Override
//    public boolean canGlow()
//    {
//        return this.getVariant() == Variant.AMETHYST;
//    }
//
//    @Override
//    public float getGlowBrightness(float ageInTicks)
//    {
//        return Mth.clamp(1.0F + Mth.cos(ageInTicks * 0.05f), 0.5F, 1.0F);
//    }
//
//    @Override
//    public Variant getVariant()
//    {
//        return Variant.BY_ID[Mth.positiveModulo(this.entityData.get(TYPE), Variant.BY_ID.length)];
//    }
//
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

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        var waterRules = WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, random);
        var biome = levelAccessor.getBiome(blockPos);
        var continentalness = TerrainUtils.getContinentalness((ServerLevel) levelAccessor, blockPos);
        var peakTypes = TerrainUtils.getPeakTypes((ServerLevel) levelAccessor, blockPos);

        if (biome.is(BiomeTags.IS_OCEAN) || biome.is(BiomeTags.IS_BEACH))
        {
            return (peakTypes == PeakTypes.LOW || peakTypes == PeakTypes.MID) && (continentalness == Continentalness.COAST || continentalness == Continentalness.OCEAN) && waterRules;
        }
        return biome.is(FOTTags.IS_CAVES) && blockPos.getY() <= 0 || waterRules;
    }

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

    @Override
    public Consumer<Int2ObjectOpenHashMap<String>> getDataFix()
    {
        return null;
    }

    //    public enum Variant implements FishData
//    {
//        STONE(SpawnSelectors.always()),
//        MOSS(SpawnSelectors.simpleSpawn(SpawnSelectors.biomes(BiomeTags.IS_JUNGLE, BiomeTags.HAS_CLOSER_WATER_FOG).or(SpawnSelectors.includeByKey(Biomes.LUSH_CAVES)))),
//        HONEY(SpawnSelectors.simpleSpawn(context ->
//        {
//            var optional = TerrainUtils.lookForBlock(context.blockPos(), 5, blockPos2 ->
//            {
//                var blockState = context.level().getBlockState(blockPos2);
//                return blockState.is(BlockTags.BEEHIVES) && BeehiveBlockEntity.getHoneyLevel(blockState) == 5;
//            });
//            return optional.isPresent();
//        })),
//        RAVEN(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.ravenIslehopperProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.ravenIslehopperProbability).and(context -> context.blockPos().getY() <= 0))),
//        AMETHYST(SpawnSelectors.simpleSpawn(true, context -> TerrainUtils.lookForBlocksWithSize(context.blockPos(), 2, 16, blockPos2 -> context.level().getBlockState(blockPos2).is(BlockTags.CRYSTAL_SOUND_BLOCKS))));
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