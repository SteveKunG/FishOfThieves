package com.stevekung.fishofthieves.item;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.blockentity.FOTSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FOTSignItem extends StandingAndWallBlockItem
{
    public FOTSignItem(Item.Properties properties, Block standingBlock, Block wallBlock)
    {
        super(standingBlock, wallBlock, properties, Direction.DOWN);
    }

    public FOTSignItem(Item.Properties properties, Block standingBlock, Block wallBlock, Direction attachmentDirection)
    {
        super(standingBlock, wallBlock, properties, attachmentDirection);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state)
    {
        var update = super.updateCustomBlockEntityTag(pos, level, player, stack, state);

        if (!level.isClientSide() && !update && player != null && level.getBlockEntity(pos) instanceof FOTSignBlockEntity signBlockEntity && level.getBlockState(pos).getBlock() instanceof SignBlock signBlock)
        {
            signBlock.openTextEdit(player, signBlockEntity, true);
        }
        return update;
    }
}
