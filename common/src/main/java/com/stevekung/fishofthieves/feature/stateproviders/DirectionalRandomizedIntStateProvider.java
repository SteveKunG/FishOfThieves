package com.stevekung.fishofthieves.feature.stateproviders;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTBlockStateProviderTypes;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class DirectionalRandomizedIntStateProvider extends BlockStateProvider
{
    public static final Codec<DirectionalRandomizedIntStateProvider> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("source").forGetter(provider -> provider.source),
                    Codec.STRING.fieldOf("age_property").forGetter(provider -> provider.agePropertyName),
                    IntProvider.CODEC.fieldOf("age_values").forGetter(provider -> provider.ageValues),
                    Codec.STRING.fieldOf("direction_property").forGetter(provider -> provider.directionPropertyName),
                    ExtraCodecs.nonEmptyList(Direction.CODEC.listOf()).fieldOf("direction_values").forGetter(provider -> provider.directionValues))
            .apply(instance, DirectionalRandomizedIntStateProvider::new));
    private final BlockStateProvider source;
    private final String agePropertyName;
    @Nullable
    private IntegerProperty ageProperty;
    private final IntProvider ageValues;
    private final String directionPropertyName;
    @Nullable
    private DirectionProperty directionProperty;
    private final List<Direction> directionValues;

    public DirectionalRandomizedIntStateProvider(BlockStateProvider source, IntegerProperty ageProperty, IntProvider ageValues, DirectionProperty directionProperty, List<Direction> directionValues)
    {
        this.source = source;
        this.ageProperty = ageProperty;
        this.agePropertyName = ageProperty.getName();
        this.ageValues = ageValues;
        this.directionProperty = directionProperty;
        this.directionPropertyName = directionProperty.getName();
        this.directionValues = directionValues;
        var collection = ageProperty.getPossibleValues();

        directionProperty.getPossibleValues().forEach(direction ->
        {
            if (!directionValues.contains(direction))
            {
                throw new IllegalArgumentException("Invalid direction property: " + directionProperty.getName() + ": " + direction);
            }
        });

        for (var i = ageValues.getMinValue(); i <= ageValues.getMaxValue(); i++)
        {
            if (!collection.contains(i))
            {
                throw new IllegalArgumentException("Property value out of range: " + ageProperty.getName() + ": " + i);
            }
        }
    }

    public DirectionalRandomizedIntStateProvider(BlockStateProvider source, String agePropertyName, IntProvider ageValues, String directionPropertyName, List<Direction> directionValues)
    {
        this.source = source;
        this.agePropertyName = agePropertyName;
        this.ageValues = ageValues;
        this.directionPropertyName = directionPropertyName;
        this.directionValues = directionValues;
    }

    @Override
    protected BlockStateProviderType<?> type()
    {
        return FOTBlockStateProviderTypes.DIRECTIONAL_RANDOMIZED_INT_STATE_PROVIDER;
    }

    @Override
    public BlockState getState(RandomSource random, BlockPos pos)
    {
        var blockState = this.source.getState(random, pos);

        if (this.ageProperty == null || !blockState.hasProperty(this.ageProperty))
        {
            this.ageProperty = findAgeProperty(blockState, this.agePropertyName);
        }
        if (this.directionProperty == null || !blockState.hasProperty(this.directionProperty))
        {
            this.directionProperty = findDirectionProperty(blockState, this.directionPropertyName);
        }

        return blockState.setValue(this.ageProperty, this.ageValues.sample(random)).setValue(this.directionProperty, Util.getRandom(this.directionValues, random));
    }

    private static IntegerProperty findAgeProperty(BlockState state, String propertyName)
    {
        var collection = state.getProperties();
        var optional = collection.stream().filter(property -> property.getName().equals(propertyName)).filter(IntegerProperty.class::isInstance).map(IntegerProperty.class::cast).findAny();
        return optional.orElseThrow(() -> new IllegalArgumentException("Illegal property: " + propertyName));
    }

    private static DirectionProperty findDirectionProperty(BlockState state, String propertyName)
    {
        var collection = state.getProperties();
        var optional = collection.stream().filter(property -> property.getName().equals(propertyName)).filter(DirectionProperty.class::isInstance).map(DirectionProperty.class::cast).findAny();
        return optional.orElseThrow(() -> new IllegalArgumentException("Illegal property: " + propertyName));
    }
}
