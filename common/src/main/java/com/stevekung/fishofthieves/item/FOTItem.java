package com.stevekung.fishofthieves.item;

import java.util.Comparator;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.condition.SpawnConditionContext;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.phys.Vec3;

public class FOTItem extends Item
{
    private final EntityType<?> entityType;
    private final ResourceLocation registryKey;

    public FOTItem(Properties properties, EntityType<?> entityType, ResourceKey<?> registryKey)
    {
        super(properties);
        this.entityType = entityType;
        this.registryKey = registryKey.location();
    }

    public static void addFishVariants(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output, Item item)
    {
        if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
        {
            if (item instanceof FOTItem fotItem)
            {
                Comparator<Holder<? extends AbstractFishVariant>> comparator = Comparator.comparing(Holder::value, Comparator.comparingInt(AbstractFishVariant::customModelData));
                var registryKey = ResourceKey.<AbstractFishVariant>createRegistryKey(fotItem.getRegistryKey());
                itemDisplayParameters.holders().lookup(registryKey).ifPresent(lookup -> lookup.listElements().sorted(comparator).forEach(holder -> output.accept(create(item, holder.value().customModelData()))));
            }
        }
        else
        {
            output.accept(item);
        }
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack itemStack)
    {
        if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab && !itemStack.has(DataComponents.CUSTOM_MODEL_DATA))
        {
            // item without a custom model data component is always 0 if enable all fish variants
            itemStack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(0));
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (FishOfThieves.CONFIG.general.displayAllFishVariantInCreativeTab)
        {
            if (context.registries() != null && itemStack.getItem() instanceof FOTItem fotItem)
            {
                var registryKey = ResourceKey.<AbstractFishVariant>createRegistryKey(fotItem.getRegistryKey());
                context.registries().lookup(registryKey).ifPresent(lookup -> lookup.listElements().forEach(holder ->
                {
                    var customModelData = holder.value().customModelData();

                    if (itemStack.has(DataComponents.CUSTOM_MODEL_DATA) && itemStack.get(DataComponents.CUSTOM_MODEL_DATA).value() == customModelData)
                    {
                        tooltipComponents.add(Component.translatable(this.entityType.getDescriptionId() + "." + holder.value().name()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
                    }
                }));
            }
        }
    }

    public ResourceLocation getRegistryKey()
    {
        return this.registryKey;
    }

    public static ItemStack generateRandomFishVariantLootItem(ItemStack itemStack, ServerLevel level, @Nullable Vec3 vec3, RandomSource randomSource)
    {
        if (FishOfThieves.CONFIG.general.enableFishItemWithAllVariant && itemStack.getItem() instanceof FOTItem fotItem)
        {
            var registryKey = ResourceKey.<AbstractFishVariant>createRegistryKey(fotItem.getRegistryKey());

            if (vec3 != null)
            {
                var context = new SpawnConditionContext(level, level.registryAccess(), BlockPos.containing(vec3.x, vec3.y, vec3.z), randomSource);
                Util.getRandomSafe(level.registryAccess().registryOrThrow(registryKey).holders().filter(holder -> itemStack.is(holder.value().baseItem()) && Util.allOf(holder.value().conditions()).test(context)).toList(), randomSource).ifPresent(holder -> itemStack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(holder.value().customModelData())));
            }
        }
        return itemStack;
    }

    private static ItemStack create(Item item, int index)
    {
        var itemStack = new ItemStack(item);
        itemStack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(index));
        return itemStack;
    }
}