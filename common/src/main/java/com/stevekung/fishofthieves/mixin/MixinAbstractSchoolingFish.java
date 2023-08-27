package com.stevekung.fishofthieves.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;

@Mixin(AbstractSchoolingFish.class)
public abstract class MixinAbstractSchoolingFish extends AbstractFish
{
    public MixinAbstractSchoolingFish(EntityType<? extends AbstractFish> entityType, Level level)
    {
        super(entityType, level);
    }

    @Shadow
    AbstractSchoolingFish leader;
    @Shadow
    int schoolSize;
    @Shadow
    abstract boolean hasFollowers();

    private static String color(boolean cond)
    {
        var color = cond ? ChatFormatting.GREEN : ChatFormatting.RED;
        return color.toString() + cond;
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        if (!this.level.isClientSide && this.isDeadOrDying()) {
            var getThis = AbstractSchoolingFish.class.cast(this);
            if (getThis.isFollower() && leader != null)
            {
                ((AbstractSchoolingFishAccessor)leader).callRemoveFollower();
            }
        }
        super.remove(reason);
    }

    @Override
    public void tick()
    {
        super.tick();
        var getThis = AbstractSchoolingFish.class.cast(this);

        if (!this.level.isClientSide() && this.getType() == EntityType.TROPICAL_FISH)
        {
            var compo = Component.empty();
            var text = "";

            if (getThis.hasFollowers())
            {
                text += " hasFollowers: " + color(getThis.hasFollowers());
                text += ChatFormatting.RESET;
            }
            if (getThis.isFollower())
            {
                text += " isFollower: " + color(getThis.isFollower());
                text += ChatFormatting.RESET;
            }

            text += " schoolSize: " + ChatFormatting.GOLD + this.schoolSize;
            text += ChatFormatting.RESET;
            text += " uuid: " + this.getUUID().toString().split("-")[0];

            if (this.leader != null)
            {
                text += " leader: " + ChatFormatting.GOLD + this.leader.getUUID().toString().split("-")[0];
                text += ChatFormatting.RESET;
            }

            compo = Component.literal(text);
            this.setCustomNameVisible(true);
            this.setCustomName(compo);
        }

        if (this.hasFollowers() && this.level.random.nextInt(200) == 1)
        {
            var list = this.level.getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0, 8.0, 8.0));

            if (list.size() <= 1)
            {
                this.schoolSize = 1;
            }
        }
    }
}