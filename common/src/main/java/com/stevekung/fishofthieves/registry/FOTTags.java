package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.core.FishOfThieves;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

public class FOTTags
{
    public static final TagKey<Item> THIEVES_FISH_BUCKET = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "thieves_fish_bucket"));

    public static final TagKey<EntityType<?>> THIEVES_FISH = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "thieves_fish"));

    public static final TagKey<Structure> STRONGHOLDS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "strongholds"));
    public static final TagKey<Structure> MONUMENTS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "monuments"));
    public static final TagKey<Structure> PILLAGER_OUTPOSTS = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "pillager_outposts"));

    public static final TagKey<Biome> UNDERGROUND = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(FishOfThieves.MOD_ID, "underground"));
}