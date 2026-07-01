package com.stevekung.fishofthieves.feature.stateproviders;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTBlockStateProviderTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class DirectionalRandomizedIntBooleanStateProvider extends BlockStateProvider
{
    public static final MapCodec<DirectionalRandomizedIntBooleanStateProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("source").forGetter(provider -> provider.source),
                    Codec.STRING.fieldOf("integer_property").forGetter(provider -> provider.integerPropertyName),
                    IntProviders.CODEC.fieldOf("integer_values").forGetter(provider -> provider.integerValues),
                    Codec.STRING.fieldOf("direction_property").forGetter(provider -> provider.directionPropertyName),
                    Codec.STRING.fieldOf("boolean_property").forGetter(provider -> provider.booleanPropertyName),
                    FloatProviders.CODEC.fieldOf("boolean_chance").forGetter(provider -> provider.booleanChance))
            .apply(instance, DirectionalRandomizedIntBooleanStateProvider::new));
    private final BlockStateProvider source;
    private final String integerPropertyName;
    @Nullable
    private IntegerProperty integerProperty;
    private final IntProvider integerValues;
    private final String directionPropertyName;
    @Nullable
    private EnumProperty<Direction> directionProperty;
    private final String booleanPropertyName;
    @Nullable
    private BooleanProperty booleanProperty;
    private final FloatProvider booleanChance;

    public DirectionalRandomizedIntBooleanStateProvider(BlockStateProvider source, IntegerProperty integerProperty, IntProvider integerValues, EnumProperty<Direction> directionProperty, BooleanProperty booleanProperty, FloatProvider booleanChance)
    {
        this.source = source;
        this.integerProperty = integerProperty;
        this.integerPropertyName = integerProperty.getName();
        this.integerValues = integerValues;
        this.directionProperty = directionProperty;
        this.directionPropertyName = directionProperty.getName();
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

    public DirectionalRandomizedIntBooleanStateProvider(BlockStateProvider source, String integerPropertyName, IntProvider integerValues, String directionPropertyName, String booleanPropertyName, FloatProvider booleanChance)
    {
        this.source = source;
        this.integerPropertyName = integerPropertyName;
        this.integerValues = integerValues;
        this.directionPropertyName = directionPropertyName;
        this.booleanPropertyName = booleanPropertyName;
        this.booleanChance = booleanChance;
    }

    @Override
    protected BlockStateProviderType<?> type()
    {
        return FOTBlockStateProviderTypes.DIRECTIONAL_RANDOMIZED_INT_BOOLEAN_STATE_PROVIDER;
    }

    @Override
    public BlockState getState(LevelAccessor level, RandomSource random, BlockPos pos)
    {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    public BlockState getState(LevelAccessor level, RandomSource random, BlockPos pos, Direction direction)
    {
        var blockState = this.source.getState(level, random, pos);

        if (this.integerProperty == null || !blockState.hasProperty(this.integerProperty))
        {
            this.integerProperty = findProperty(blockState, this.integerPropertyName, IntegerProperty.class);
        }
        if (this.directionProperty == null || !blockState.hasProperty(this.directionProperty))
        {
            this.directionProperty = findProperty(blockState, this.directionPropertyName, EnumProperty.class);
        }
        if (this.booleanProperty == null || !blockState.hasProperty(this.booleanProperty))
        {
            this.booleanProperty = findProperty(blockState, this.booleanPropertyName, BooleanProperty.class);
        }
        return blockState.setValue(this.integerProperty, this.integerValues.sample(random))
                .setValue(this.directionProperty, direction)
                .setValue(this.booleanProperty, random.nextFloat() < this.booleanChance.sample(random));
    }

    private static <T extends Property<?>> T findProperty(BlockState state, String propertyName, Class<T> propertyClass)
    {
        var collection = state.getProperties();
        var optional = collection.stream().filter(property -> property.getName().equals(propertyName)).filter(propertyClass::isInstance).map(propertyClass::cast).findAny();
        return optional.orElseThrow(() -> new IllegalArgumentException("Illegal property: " + propertyName));
    }
}