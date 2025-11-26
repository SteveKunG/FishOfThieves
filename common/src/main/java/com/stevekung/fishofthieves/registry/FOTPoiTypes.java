package com.stevekung.fishofthieves.registry;

import java.util.Set;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.block.ShoalBlock;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.state.BlockState;

public class FOTPoiTypes
{
    public static final ResourceKey<PoiType> NATURAL_SHOAL = createKey("natural_shoal");
    public static final ResourceKey<PoiType> TREASURED_SHOAL = createKey("treasured_shoal");

    public static void init()
    {
        register(NATURAL_SHOAL, Set.of(FOTBlocks.SHOAL_BLOCK.defaultBlockState().setValue(ShoalBlock.TREASURED, false)), 0, 1);
        register(TREASURED_SHOAL, Set.of(FOTBlocks.SHOAL_BLOCK.defaultBlockState().setValue(ShoalBlock.TREASURED, true)), 0, 1);
    }

    private static void register(ResourceKey<PoiType> value, Set<BlockState> matchingStates, int maxTickets, int validRange)
    {
        var key = BuiltInRegistries.POINT_OF_INTEREST_TYPE;
        var poiType = new PoiType(matchingStates, maxTickets, validRange);
        Registry.register(key, value, poiType);
        PoiTypes.registerBlockStates(key.getHolderOrThrow(value), matchingStates);
    }

    private static ResourceKey<PoiType> createKey(String name)
    {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, FishOfThieves.id(name));
    }
}