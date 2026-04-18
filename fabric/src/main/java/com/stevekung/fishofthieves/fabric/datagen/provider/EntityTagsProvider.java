package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.ArrayUtils;

import com.stevekung.fishofthieves.references.FOTEntityTypeIds;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypeIds;

public class EntityTagsProvider extends FabricTagsProvider.EntityTypeTagsProvider
{
    // Immersive Weathering
    private static final TagKey<EntityType<?>> LIGHT_FREEZE_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("immersive_weathering", "light_freeze_immune"));

    public EntityTagsProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        var neutralFishes = new ResourceKey<?>[] { FOTEntityTypeIds.DEVILFISH, FOTEntityTypeIds.BATTLEGILL, FOTEntityTypeIds.WRECKER };
        var fishes = new ResourceKey<?>[] { FOTEntityTypeIds.SPLASHTAIL, FOTEntityTypeIds.PONDIE, FOTEntityTypeIds.ISLEHOPPER, FOTEntityTypeIds.ANCIENTSCALE, FOTEntityTypeIds.PLENTIFIN, FOTEntityTypeIds.WILDSPLASH, FOTEntityTypeIds.STORMFISH };
        this.builder(EntityTypeTags.AXOLOTL_HUNT_TARGETS).add(Arrays.stream(ArrayUtils.removeElements(fishes, neutralFishes)).map(resourceKey -> (ResourceKey<EntityType<?>>) resourceKey).toArray(ResourceKey[]::new));
        this.builder(EntityTypeTags.AQUATIC).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
        this.builder(EntityTypeTags.CAN_BREATHE_UNDER_WATER).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
        this.builder(EntityTypeTags.NOT_SCARY_FOR_PUFFERFISH).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
        this.builder(EntityTypeTags.CANNOT_BE_PUSHED_ONTO_BOATS).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
        this.builder(EntityTypeTags.BOAT).add(FOTEntityTypeIds.COCONUT_BOAT);
        this.builder(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE).add(Arrays.stream(ArrayUtils.addAll(fishes, neutralFishes)).map(resourceKey -> (ResourceKey<EntityType<?>>) resourceKey).toArray(ResourceKey[]::new));
        this.builder(FOTTags.EntityTypes.FISH_BONE_DROP).add(EntityTypeIds.COD, EntityTypeIds.SALMON, EntityTypeIds.TROPICAL_FISH);
        this.builder(FOTTags.EntityTypes.FISH_PLAQUE_HORIZONTAL_RENDER).add(EntityTypeIds.PUFFERFISH, EntityTypeIds.TADPOLE, EntityTypeIds.AXOLOTL);
        this.builder(FOTTags.EntityTypes.FISH_PLAQUE_HORIZONTAL_RENDER_ON_POWERED)
                .add(EntityTypeIds.COD, EntityTypeIds.SALMON, EntityTypeIds.TROPICAL_FISH, EntityTypeIds.AXOLOTL)
                .forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
        this.builder(FOTTags.EntityTypes.BATTLEGILL_ATTACKABLE).add(EntityTypeIds.DROWNED, EntityTypeIds.ZOMBIE, EntityTypeIds.SKELETON, EntityTypeIds.GUARDIAN, EntityTypeIds.ELDER_GUARDIAN, EntityTypeIds.PILLAGER);
        this.builder(FOTTags.EntityTypes.DEVILFISH_ATTACKABLE).add(EntityTypeIds.DROWNED, EntityTypeIds.ZOMBIE, EntityTypeIds.SKELETON, EntityTypeIds.GUARDIAN, EntityTypeIds.ELDER_GUARDIAN);
        this.builder(FOTTags.EntityTypes.WRECKER_ATTACKABLE).add(EntityTypeIds.PLAYER, EntityTypeIds.DROWNED, EntityTypeIds.ZOMBIE, EntityTypeIds.SKELETON, EntityTypeIds.GUARDIAN, EntityTypeIds.ELDER_GUARDIAN);

        // Immersive Weathering compatibility
        this.builder(LIGHT_FREEZE_IMMUNE).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
    }
}