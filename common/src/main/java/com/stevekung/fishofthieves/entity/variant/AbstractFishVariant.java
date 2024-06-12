package com.stevekung.fishofthieves.entity.variant;

import java.util.List;
import java.util.Optional;

import com.mojang.datafixers.util.Function7;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.SpawnCondition;
import com.stevekung.fishofthieves.entity.condition.SpawnConditionContext;
import com.stevekung.fishofthieves.item.FOTItem;
import com.stevekung.fishofthieves.registry.FOTSpawnConditions;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public interface AbstractFishVariant
{
    int customModelData();

    default Holder<Item> baseItem()
    {
        return Holder.direct(Items.AIR);
    }

    String name();

    ResourceLocation texture();

    ResourceLocation fullTexture();

    Optional<ResourceLocation> fullGlowTexture();

    Optional<ResourceLocation> glowTexture();

    List<SpawnCondition> conditions();

    Optional<List<SpawnCondition>> fishingOverride();

    static <B extends AbstractFishVariant> Codec<B> simpleCodec(Function7<String, ResourceLocation, Optional<ResourceLocation>, List<SpawnCondition>, Optional<List<SpawnCondition>>, Holder<Item>, Integer, B> factory)
    {
        //@formatter:off
        return RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.NON_EMPTY_STRING.fieldOf("name").forGetter(AbstractFishVariant::name),
                ResourceLocation.CODEC.fieldOf("texture").forGetter(AbstractFishVariant::texture),
                ResourceLocation.CODEC.optionalFieldOf("glow_texture").forGetter(AbstractFishVariant::glowTexture),
                FOTSpawnConditions.DIRECT_CODEC.listOf().optionalFieldOf("conditions", List.of()).forGetter(AbstractFishVariant::conditions),
                FOTSpawnConditions.DIRECT_CODEC.listOf().optionalFieldOf("fishing_override").forGetter(AbstractFishVariant::fishingOverride),
                RegistryFixedCodec.create(Registries.ITEM).validate(AbstractFishVariant::validateItem).fieldOf("base_item").forGetter(AbstractFishVariant::baseItem),
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("custom_model_data").forGetter(AbstractFishVariant::customModelData)
        ).apply(instance, factory));
        //@formatter:on
    }

    @SuppressWarnings("deprecation")
    static DataResult<Holder<Item>> validateItem(Holder<Item> holder)
    {
        if (holder.is(Items.AIR.builtInRegistryHolder()))
        {
            return DataResult.error(() -> "Item must not be minecraft:air");
        }
        else if (!(holder.value() instanceof FOTItem))
        {
            return DataResult.error(() -> "Item must be extends to FOTItem");
        }
        return DataResult.success(holder);
    }

    static ResourceLocation fullTextureId(ResourceLocation texture)
    {
        return texture.withPath(string -> "textures/" + string + ".png");
    }

    static <T extends AbstractFishVariant> Holder<T> getSpawnVariant(ServerLevel serverLevel, RegistryAccess registryAccess, ResourceKey<? extends Registry<? extends T>> registryKey, ResourceKey<T> defaultKey, LivingEntity livingEntity, boolean fromBucket)
    {
        var registry = registryAccess.registryOrThrow(registryKey);
        var context = new SpawnConditionContext(serverLevel, registryAccess, livingEntity.blockPosition(), livingEntity.getRandom());
        var muha = Util.getRandomSafe(registry.holders().filter(variant -> fromBucket || Util.allOf(variant.value().conditions()).test(context)).toList(), livingEntity.getRandom());
        return muha.orElseGet(() -> registry.getHolderOrThrow(defaultKey));
    }

    class RegisterContext<T>
    {
        private final String entityName;
        private final Item baseItem;
        private final Function7<String, ResourceLocation, Optional<ResourceLocation>, List<SpawnCondition>, Optional<List<SpawnCondition>>, Holder<Item>, Integer, T> factory;

        RegisterContext(String entityName, Item baseItem, Function7<String, ResourceLocation, Optional<ResourceLocation>, List<SpawnCondition>, Optional<List<SpawnCondition>>, Holder<Item>, Integer, T> factory)
        {
            this.entityName = entityName;
            this.baseItem = baseItem;
            this.factory = factory;
        }

        public static <T> RegisterContext<T> create(String entityName, Item baseItem, Function7<String, ResourceLocation, Optional<ResourceLocation>, List<SpawnCondition>, Optional<List<SpawnCondition>>, Holder<Item>, Integer, T> factory)
        {
            return new RegisterContext<>(entityName, baseItem, factory);
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int customModelData, SpawnCondition... conditions)
        {
            this.register(context, key, name, customModelData, false, List.of(conditions), List.of());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int customModelData, List<SpawnCondition> conditions, List<SpawnCondition> fishingOverride)
        {
            this.register(context, key, name, customModelData, false, conditions, fishingOverride);
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int customModelData, boolean glow, SpawnCondition... conditions)
        {
            this.register(context, key, name, customModelData, glow, List.of(conditions), List.of());
        }

        public void register(BootstrapContext<T> context, ResourceKey<T> key, String name, int customModelData, boolean glow, List<SpawnCondition> conditions, List<SpawnCondition> fishingOverride)
        {
            var texture = FishOfThieves.id("entity/" + this.entityName + "/" + name);
            var glowTexture = FishOfThieves.id("entity/" + this.entityName + "/" + name + "_glow");
            context.register(key, this.factory.apply(name, texture, glow ? Optional.of(glowTexture) : Optional.empty(), conditions, fishingOverride.isEmpty() ? Optional.empty() : Optional.of(fishingOverride), BuiltInRegistries.ITEM.wrapAsHolder(this.baseItem), customModelData));
        }
    }
}