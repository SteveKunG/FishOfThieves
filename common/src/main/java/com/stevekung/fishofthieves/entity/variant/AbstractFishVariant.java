package com.stevekung.fishofthieves.entity.variant;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.mojang.datafixers.util.Function5;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.FishOfThieves;

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
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public interface AbstractFishVariant extends PriorityProvider<SpawnContext, SpawnCondition>
{
    int customModelData();

    String name();

    ResourceLocation texture();

    ResourceLocation fullTexture();

    Optional<ResourceLocation> fullGlowTexture();

    Optional<ResourceLocation> glowTexture();

    SpawnSettings spawnSettings();

    @Override
    default List<Selector<SpawnContext, SpawnCondition>> selectors()
    {
        return this.spawnSettings().entity().selectors();
    }

    static <T extends AbstractFishVariant> Codec<T> simpleCodec(Function5<String, ResourceLocation, Optional<ResourceLocation>, SpawnSettings, Integer, T> factory)
    {
        //@formatter:off
        return RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.NON_EMPTY_STRING.fieldOf("name").forGetter(AbstractFishVariant::name),
                ResourceLocation.CODEC.fieldOf("texture").forGetter(AbstractFishVariant::texture),
                ResourceLocation.CODEC.optionalFieldOf("glow_texture").forGetter(AbstractFishVariant::glowTexture),
                SpawnSettings.CODEC.optionalFieldOf("spawn_settings", new SpawnSettings(SpawnPrioritySelectors.fallback(0), Optional.empty())).forGetter(AbstractFishVariant::spawnSettings),
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("custom_model_data").forGetter(AbstractFishVariant::customModelData)
        ).apply(instance, factory));
        //@formatter:on
    }

    static ResourceLocation fullTextureId(ResourceLocation texture)
    {
        return texture.withPath(string -> "textures/" + string + ".png");
    }

    static <T extends AbstractFishVariant> Holder<T> getSpawnVariant(ServerLevel serverLevel, RegistryAccess registryAccess, ResourceKey<? extends Registry<? extends T>> registryKey, ResourceKey<T> defaultKey, LivingEntity livingEntity, boolean fromBucket)
    {
        var registry = registryAccess.lookupOrThrow(registryKey);
        var context = new SpawnContext(livingEntity.blockPosition(), serverLevel, serverLevel.getBiome(livingEntity.blockPosition()));

        if (fromBucket)
        {
            var muha = Util.getRandomSafe(registry.listElements().toList(), livingEntity.getRandom());
            return muha.orElseGet(() -> registry.getOrThrow(defaultKey));
        }
        return PriorityProvider.pick(registryAccess.lookupOrThrow(registryKey).listElements(), Holder::value, livingEntity.getRandom(), context).orElseGet(() -> registry.getOrThrow(defaultKey));
    }

    class RegisterContext<T>
    {
        private final String entityName;
        private final Function5<String, ResourceLocation, Optional<ResourceLocation>, SpawnSettings, Integer, T> factory;

        RegisterContext(String entityName, Function5<String, ResourceLocation, Optional<ResourceLocation>, SpawnSettings, Integer, T> factory)
        {
            this.entityName = entityName;
            this.factory = factory;
        }

        public static <T> RegisterContext<T> create(String entityName, Function5<String, ResourceLocation, Optional<ResourceLocation>, SpawnSettings, Integer, T> factory)
        {
            return new RegisterContext<>(entityName, factory);
        }

        public <Context, Condition extends PriorityProvider.SelectorCondition<Context>> PriorityProvider.Selector<Context, Condition> select(Optional<Condition> condition, int priority)
        {
            return new PriorityProvider.Selector<>(condition, priority);
        }

        public <Context, Condition extends PriorityProvider.SelectorCondition<Context>> PriorityProvider.Selector<Context, Condition> select(Condition condition, int priority)
        {
            return new PriorityProvider.Selector<>(condition, priority);
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        public <Context, Condition extends PriorityProvider.SelectorCondition<Context>> PriorityProvider.Selector<Context, Condition> select(Predicate<Context> condition, int priority)
        {
            return new PriorityProvider.Selector(Optional.of(condition), priority);
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int customModelData)
        {
            this.register(context, key, name, customModelData, false, PriorityProvider.alwaysTrue(0), Optional.empty());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int customModelData, SpawnCondition condition)
        {
            this.register(context, key, name, customModelData, false, List.of(this.select(condition, 0)), Optional.empty());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int customModelData, Predicate<SpawnContext> conditions)
        {
            this.register(context, key, name, customModelData, false, List.of(this.select(conditions, 0)), Optional.empty());
        }

        @SafeVarargs
        public final void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int customModelData, PriorityProvider.Selector<SpawnContext, SpawnCondition>... conditions)
        {
            this.register(context, key, name, customModelData, false, List.of(conditions), Optional.empty());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int customModelData, List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> conditions, List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> fishingOverride)
        {
            this.register(context, key, name, customModelData, false, conditions, Optional.of(fishingOverride));
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int customModelData, boolean glow, PriorityProvider.Selector<SpawnContext, SpawnCondition> conditions)
        {
            this.register(context, key, name, customModelData, glow, List.of(conditions), Optional.empty());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int customModelData, boolean glow, SpawnCondition condition)
        {
            this.register(context, key, name, customModelData, glow, List.of(this.select(condition, 0)), Optional.empty());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int customModelData, boolean glow, Predicate<SpawnContext> predicate)
        {
            this.register(context, key, name, customModelData, glow, List.of(this.select(predicate, 0)), Optional.empty());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int customModelData, boolean glow, PriorityProvider.Selector<SpawnContext, SpawnCondition> conditions, List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> fishingOverride)
        {
            this.register(context, key, name, customModelData, glow, List.of(conditions), Optional.of(fishingOverride));
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int customModelData, boolean glow, List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> conditions, Optional<List<PriorityProvider.Selector<SpawnContext, SpawnCondition>>> fishingOverride)
        {
            var texture = FishOfThieves.id("entity/" + this.entityName + "/" + name);
            var glowTexture = FishOfThieves.id("entity/" + this.entityName + "/" + name + "_glow");
            context.register(key, this.factory.apply(name, texture, glow ? Optional.of(glowTexture) : Optional.empty(), new SpawnSettings(new SpawnPrioritySelectors(conditions), fishingOverride.map(SpawnPrioritySelectors::new)), customModelData));
        }
    }

    record SpawnSettings(SpawnPrioritySelectors entity, Optional<SpawnPrioritySelectors> fishing)
    {
        //@formatter:off
        public static final Codec<SpawnSettings> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        SpawnPrioritySelectors.CODEC.optionalFieldOf("entity", SpawnPrioritySelectors.fallback(0)).forGetter(SpawnSettings::entity),
                        SpawnPrioritySelectors.CODEC.optionalFieldOf("fishing").forGetter(SpawnSettings::fishing))
                .apply(instance, SpawnSettings::new));
        //@formatter:on
    }
}