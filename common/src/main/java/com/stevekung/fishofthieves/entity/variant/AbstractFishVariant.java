package com.stevekung.fishofthieves.entity.variant;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.mojang.datafixers.util.Function5;
import com.mojang.datafixers.util.Function6;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.NeverCondition;
import com.stevekung.fishofthieves.entity.condition.SpawnCondition;
import com.stevekung.fishofthieves.entity.condition.SpawnConditionContext;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;

public interface AbstractFishVariant
{
    Comparator<Holder<? extends AbstractFishVariant>> COMPARATOR = Comparator.comparing(Holder::value, Comparator.comparingInt(AbstractFishVariant::order));

    int order();

    String name();

    ResourceLocation texture();

    ResourceLocation fullTexture();

    Optional<ResourceLocation> fullGlowTexture();

    Optional<ResourceLocation> glowTexture();

    Optional<Boolean> treasured();

    SpawnSettings spawnSettings();

    static <T extends AbstractFishVariant> Codec<T> simpleCodec(Function6<String, ResourceLocation, Optional<ResourceLocation>, Optional<Boolean>, SpawnSettings, Integer, T> factory)
    {
        return RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.NON_EMPTY_STRING.fieldOf("name").forGetter(AbstractFishVariant::name),
                ResourceLocation.CODEC.fieldOf("texture").forGetter(AbstractFishVariant::texture),
                ResourceLocation.CODEC.optionalFieldOf("glow_texture").forGetter(AbstractFishVariant::glowTexture),
                Codec.BOOL.optionalFieldOf("treasured").forGetter(AbstractFishVariant::treasured),
                SpawnSettings.CODEC.optionalFieldOf("spawn_settings", new SpawnSettings(List.of(), Optional.empty())).forGetter(AbstractFishVariant::spawnSettings),
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("order").forGetter(AbstractFishVariant::order)
        ).apply(instance, factory));
    }

    static <T extends AbstractFishVariant> Codec<T> networkCodec(Function5<String, ResourceLocation, Optional<ResourceLocation>, Optional<Boolean>, Integer, T> factory)
    {
        return RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.NON_EMPTY_STRING.fieldOf("name").forGetter(AbstractFishVariant::name),
                ResourceLocation.CODEC.fieldOf("texture").forGetter(AbstractFishVariant::texture),
                ResourceLocation.CODEC.optionalFieldOf("glow_texture").forGetter(AbstractFishVariant::glowTexture),
                Codec.BOOL.optionalFieldOf("treasured").forGetter(AbstractFishVariant::treasured),
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("order").forGetter(AbstractFishVariant::order)
        ).apply(instance, factory));
    }

    static ResourceLocation fullTextureId(ResourceLocation texture)
    {
        return texture.withPath(string -> "textures/" + string + ".png");
    }

    static <T extends AbstractFishVariant> Holder<T> getSpawnVariant(ServerLevel serverLevel, RegistryAccess registryAccess, ResourceKey<? extends Registry<? extends T>> registryKey, ResourceKey<T> defaultKey, LivingEntity livingEntity, boolean fromBucket)
    {
        var registry = registryAccess.registryOrThrow(registryKey);
        var context = new SpawnConditionContext(serverLevel, null, registryAccess, livingEntity.blockPosition(), livingEntity.getRandom());
        var muha = Util.getRandomSafe(registry.holders().filter(variant -> fromBucket || Util.allOf(variant.value().spawnSettings().entity()).test(context)).toList(), livingEntity.getRandom());
        return muha.orElseGet(() -> registry.getHolderOrThrow(defaultKey));
    }

    record RegisterContext<T>(String entityName, Function6<String, ResourceLocation, Optional<ResourceLocation>, Optional<Boolean>, SpawnSettings, Integer, T> factory)
    {
        public static <T> RegisterContext<T> create(String entityName, Function6<String, ResourceLocation, Optional<ResourceLocation>, Optional<Boolean>, SpawnSettings, Integer, T> factory)
        {
            return new RegisterContext<>(entityName, factory);
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, SpawnCondition... conditions)
        {
            this.register(context, key, name, order, false, Optional.empty(), List.of(conditions), List.of());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, List<SpawnCondition> conditions, List<SpawnCondition> fishingOverride)
        {
            this.register(context, key, name, order, false, Optional.empty(), conditions, fishingOverride);
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, boolean glow, SpawnCondition... conditions)
        {
            this.register(context, key, name, order, glow, Optional.empty(), List.of(conditions), List.of());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, boolean glow, boolean treasured)
        {
            this.register(context, key, name, order, glow, Optional.of(treasured), List.of(NeverCondition.never().build()), List.of());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, boolean glow, Optional<Boolean> treasured, List<SpawnCondition> conditions, List<SpawnCondition> fishingOverride)
        {
            var treasuredPath = treasured.isPresent() ? "treasured/" : "";
            var texture = FishOfThieves.id("entity/" + this.entityName + "/" + treasuredPath + name);
            var glowTexture = FishOfThieves.id("entity/" + this.entityName + "/" + treasuredPath + name + "_glow");
            context.register(key, this.factory.apply(name, texture, glow ? Optional.of(glowTexture) : Optional.empty(), treasured, new SpawnSettings(conditions, fishingOverride.isEmpty() ? Optional.empty() : Optional.of(fishingOverride)), order));
        }
    }

    record SpawnSettings(List<SpawnCondition> entity, Optional<List<SpawnCondition>> fishing)
    {
        public static final Codec<SpawnSettings> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        FOTSpawnConditions.DIRECT_CODEC.listOf().optionalFieldOf("entity", List.of()).forGetter(SpawnSettings::entity),
                        FOTSpawnConditions.DIRECT_CODEC.listOf().optionalFieldOf("fishing").forGetter(SpawnSettings::fishing))
                .apply(instance, SpawnSettings::new));

        public static final SpawnSettings EMPTY = new SpawnSettings(List.of(), Optional.empty());
    }
}