package com.stevekung.fishofthieves.feature.stateproviders;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTBlockStateProviderTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class RandomizedIntBooleanStateProvider extends BlockStateProvider
{
    public static final MapCodec<RandomizedIntBooleanStateProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("source").forGetter(provider -> provider.source),
                    Codec.STRING.fieldOf("integer_property").forGetter(provider -> provider.integerPropertyName),
                    IntProviders.CODEC.fieldOf("integer_values").forGetter(provider -> provider.integerValues),
                    Codec.STRING.fieldOf("boolean_property").forGetter(provider -> provider.booleanPropertyName),
                    FloatProviders.CODEC.fieldOf("boolean_chance").forGetter(provider -> provider.booleanChance))
            .apply(instance, RandomizedIntBooleanStateProvider::new));
    private final BlockStateProvider source;
    private final String integerPropertyName;
    @Nullable
    private IntegerProperty integerProperty;
    private final IntProvider integerValues;
    private final String booleanPropertyName;
    @Nullable
    private BooleanProperty booleanProperty;
    private final FloatProvider booleanChance;

    public RandomizedIntBooleanStateProvider(BlockStateProvider source, IntegerProperty integerProperty, IntProvider integerValues, BooleanProperty booleanProperty, FloatProvider booleanChance)
    {
        this.source = source;
        this.integerProperty = integerProperty;
        this.integerPropertyName = integerProperty.getName();
        this.integerValues = integerValues;
        this.booleanProperty = booleanProperty;
        this.booleanPropertyName = booleanProperty.getName();
        this.booleanChance = booleanChance;
        var collection = integerProperty.getPossibleValues();

        for (var i = integerValues.minInclusive(); i <= integerValues.maxInclusive(); i++)
        {
            if (!collection.contains(i))
            {
                throw new IllegalArgumentException("Property value out of range: " + integerProperty.getName() + ": " + i);
            }
        }
    }

    public RandomizedIntBooleanStateProvider(BlockStateProvider source, String integerPropertyName, IntProvider integerValues, String booleanPropertyName, FloatProvider booleanChance)
    {
        this.source = source;
        this.integerPropertyName = integerPropertyName;
        this.integerValues = integerValues;
        this.booleanPropertyName = booleanPropertyName;
        this.booleanChance = booleanChance;
    }

    @Override
    protected BlockStateProviderType<?> type()
    {
        return FOTBlockStateProviderTypes.RANDOMIZED_INT_BOOLEAN_STATE_PROVIDER;
    }

    @Override
    public BlockState getState(WorldGenLevel level, RandomSource random, BlockPos pos)
    {
        var blockState = this.source.getState(level, random, pos);

        if (this.integerProperty == null || !blockState.hasProperty(this.integerProperty))
        {
            this.integerProperty = findProperty(blockState, this.integerPropertyName, IntegerProperty.class);
        }
        if (this.booleanProperty == null || !blockState.hasProperty(this.booleanProperty))
        {
            this.booleanProperty = findProperty(blockState, this.booleanPropertyName, BooleanProperty.class);
        }
        return blockState.setValue(this.integerProperty, this.integerValues.sample(random))
                .setValue(this.booleanProperty, random.nextFloat() < this.booleanChance.sample(random));
    }

    private static <T extends Property<?>> T findProperty(BlockState state, String propertyName, Class<T> propertyClass)
    {
        var collection = state.getProperties();
        var optional = collection.stream().filter(property -> property.getName().equals(propertyName)).filter(propertyClass::isInstance).map(propertyClass::cast).findAny();
        return optional.orElseThrow(() -> new IllegalArgumentException("Illegal property: " + propertyName));
    }
}