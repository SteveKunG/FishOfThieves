package com.stevekung.fishofthieves.entity.variant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Function5;
import com.mojang.datafixers.util.Function6;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.NeverCheck;

import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public interface AbstractFishVariant extends PriorityProvider<SpawnContext, SpawnCondition>
{
    Comparator<Holder<? extends AbstractFishVariant>> COMPARATOR = Comparator.comparing(Holder::value, Comparator.comparingInt(AbstractFishVariant::order));

    int order();

    String name();

    ClientAsset.ResourceTexture texture();

    Identifier fullTexture();

    Optional<Identifier> fullGlowTexture();

    Optional<ClientAsset.ResourceTexture> glowTexture();

    Optional<Boolean> treasured();

    SpawnSettings spawnSettings();

    @Override
    default List<Selector<SpawnContext, SpawnCondition>> selectors()
    {
        return this.spawnSettings().entity().selectors();
    }

    static <T> Stream<T> select(Stream<T> stream, Function<T, AbstractFishVariant> function, SpawnContext object)
    {
        var list = new ArrayList<PriorityProvider.UnpackedEntry<SpawnContext, T>>();

        stream.forEach(objectx ->
        {
            var priorityProvider = function.apply(objectx);
            var listx = priorityProvider.spawnSettings().fishing().isPresent() ? priorityProvider.spawnSettings().fishing().get().selectors() : priorityProvider.selectors();

            for (var selector : listx)
            {
                list.add(new PriorityProvider.UnpackedEntry<>(objectx, selector.priority(), DataFixUtils.orElseGet(selector.condition(), PriorityProvider.SelectorCondition::alwaysTrue)));
            }
        });

        list.sort(PriorityProvider.UnpackedEntry.HIGHEST_PRIORITY_FIRST);
        var iterator = list.iterator();
        var i = Integer.MIN_VALUE;

        while (iterator.hasNext())
        {
            var unpackedEntry = iterator.next();

            if (unpackedEntry.priority() < i)
            {
                iterator.remove();
            }
            else if (unpackedEntry.condition().test(object))
            {
                i = unpackedEntry.priority();
            }
            else
            {
                iterator.remove();
            }
        }
        return list.stream().map(PriorityProvider.UnpackedEntry::entry);
    }

    static <T> Optional<T> pick(Stream<T> stream, Function<T, AbstractFishVariant> function, RandomSource randomSource, SpawnContext object)
    {
        var list = select(stream, function, object).toList();
        return Util.getRandomSafe(list, randomSource);
    }

    static <T extends AbstractFishVariant> Codec<T> simpleCodec(Function6<String, ClientAsset.ResourceTexture, Optional<ClientAsset.ResourceTexture>, Optional<Boolean>, SpawnSettings, Integer, T> factory)
    {
        return RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.NON_EMPTY_STRING.fieldOf("name").forGetter(AbstractFishVariant::name),
                ClientAsset.ResourceTexture.CODEC.fieldOf("texture").forGetter(AbstractFishVariant::texture),
                ClientAsset.ResourceTexture.CODEC.optionalFieldOf("glow_texture").forGetter(AbstractFishVariant::glowTexture),
                Codec.BOOL.optionalFieldOf("treasured").forGetter(AbstractFishVariant::treasured),
                SpawnSettings.CODEC.optionalFieldOf("spawn_settings", new SpawnSettings(SpawnPrioritySelectors.fallback(0), Optional.empty())).forGetter(AbstractFishVariant::spawnSettings),
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("order").forGetter(AbstractFishVariant::order)
        ).apply(instance, factory));
    }

    static <T extends AbstractFishVariant> Codec<T> networkCodec(Function5<String, ClientAsset.ResourceTexture, Optional<ClientAsset.ResourceTexture>, Optional<Boolean>, Integer, T> factory)
    {
        return RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.NON_EMPTY_STRING.fieldOf("name").forGetter(AbstractFishVariant::name),
                ClientAsset.ResourceTexture.CODEC.fieldOf("texture").forGetter(AbstractFishVariant::texture),
                ClientAsset.ResourceTexture.CODEC.optionalFieldOf("glow_texture").forGetter(AbstractFishVariant::glowTexture),
                Codec.BOOL.optionalFieldOf("treasured").forGetter(AbstractFishVariant::treasured),
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("order").forGetter(AbstractFishVariant::order)
        ).apply(instance, factory));
    }

    static <T extends AbstractFishVariant> Holder<T> getSpawnVariant(ServerLevel serverLevel, RegistryAccess registryAccess, ResourceKey<? extends Registry<? extends T>> registryKey, ResourceKey<T> defaultKey, LivingEntity livingEntity, boolean fromBucket)
    {
        var registry = registryAccess.lookupOrThrow(registryKey);
        var context = new SpawnContext(livingEntity.blockPosition(), serverLevel, serverLevel.environmentAttributes(), serverLevel.getBiome(livingEntity.blockPosition()));

        if (fromBucket)
        {
            var muha = Util.getRandomSafe(registry.listElements().toList(), livingEntity.getRandom());
            return muha.orElseGet(() -> registry.getOrThrow(defaultKey));
        }
        return PriorityProvider.pick(registryAccess.lookupOrThrow(registryKey).listElements(), Holder::value, livingEntity.getRandom(), context).orElseGet(() -> registry.getOrThrow(defaultKey));
    }

    record RegisterContext<T>(String entityName, Function6<String, ClientAsset.ResourceTexture, Optional<ClientAsset.ResourceTexture>, Optional<Boolean>, SpawnSettings, Integer, T> factory)
    {
        public static <T> RegisterContext<T> create(String entityName, Function6<String, ClientAsset.ResourceTexture, Optional<ClientAsset.ResourceTexture>, Optional<Boolean>, SpawnSettings, Integer, T> factory)
        {
            return new RegisterContext<>(entityName, factory);
        }

        public <Context, Condition extends SelectorCondition<Context>> Selector<Context, Condition> select(Condition condition, int priority)
        {
            return new Selector<>(condition, priority);
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order)
        {
            this.register(context, key, name, order, false, Optional.empty(), PriorityProvider.alwaysTrue(0), Optional.empty());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, boolean glow)
        {
            this.register(context, key, name, order, glow, Optional.empty(), PriorityProvider.alwaysTrue(0), Optional.empty());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, SpawnCondition condition)
        {
            this.register(context, key, name, order, false, Optional.empty(), List.of(this.select(condition, 0)), Optional.empty());
        }

        @SafeVarargs
        public final void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, Selector<SpawnContext, SpawnCondition>... conditions)
        {
            this.register(context, key, name, order, false, Optional.empty(), List.of(conditions), Optional.empty());
        }

        @SafeVarargs
        public final void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, boolean glow, Selector<SpawnContext, SpawnCondition>... conditions)
        {
            this.register(context, key, name, order, glow, Optional.empty(), List.of(conditions), Optional.empty());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, List<Selector<SpawnContext, SpawnCondition>> conditions, List<Selector<SpawnContext, SpawnCondition>> fishingOverride)
        {
            this.register(context, key, name, order, false, Optional.empty(), conditions, Optional.of(fishingOverride));
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, boolean glow, Selector<SpawnContext, SpawnCondition> conditions)
        {
            this.register(context, key, name, order, glow, Optional.empty(), List.of(conditions), Optional.empty());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, boolean glow, SpawnCondition condition)
        {
            this.register(context, key, name, order, glow, Optional.empty(), List.of(this.select(condition, 0)), Optional.empty());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, boolean glow, Selector<SpawnContext, SpawnCondition> conditions, List<Selector<SpawnContext, SpawnCondition>> fishingOverride)
        {
            this.register(context, key, name, order, glow, Optional.empty(), List.of(conditions), Optional.of(fishingOverride));
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, boolean glow, boolean treasured)
        {
            this.register(context, key, name, order, glow, Optional.of(treasured), List.of(this.select(NeverCheck.never(), 0)), Optional.empty());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int order, boolean glow, Optional<Boolean> treasured, List<Selector<SpawnContext, SpawnCondition>> conditions, Optional<List<Selector<SpawnContext, SpawnCondition>>> fishingOverride)
        {
            var treasuredPath = treasured.isPresent() ? "treasured/" : "";
            var texture = FishOfThieves.id("entity/" + this.entityName + "/" + treasuredPath + name);
            var glowTexture = FishOfThieves.id("entity/" + this.entityName + "/" + treasuredPath + name + "_glow");
            context.register(key, this.factory.apply(name, new ClientAsset.ResourceTexture(texture), glow ? Optional.of(new ClientAsset.ResourceTexture(glowTexture)) : Optional.empty(), treasured, new SpawnSettings(new SpawnPrioritySelectors(conditions), fishingOverride.map(SpawnPrioritySelectors::new)), order));
        }
    }

    record SpawnSettings(SpawnPrioritySelectors entity, Optional<SpawnPrioritySelectors> fishing)
    {
        public static final Codec<SpawnSettings> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        SpawnPrioritySelectors.CODEC.optionalFieldOf("entity", SpawnPrioritySelectors.fallback(0)).forGetter(SpawnSettings::entity),
                        SpawnPrioritySelectors.CODEC.optionalFieldOf("fishing").forGetter(SpawnSettings::fishing))
                .apply(instance, SpawnSettings::new));

        public static final SpawnSettings EMPTY = new SpawnSettings(SpawnPrioritySelectors.EMPTY, Optional.empty());
    }
}