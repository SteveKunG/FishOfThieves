package com.stevekung.fishofthieves.client.renderer.entity.state;

public interface LivingEntityRenderStateExtender
{
    default boolean fishofthieves$isSalmon()
    {
        throw new AssertionError("Implemented via mixin");
    }

    default void fishofthieves$setSalmon(boolean isSalmon)
    {
        throw new AssertionError("Implemented via mixin");
    }

    default boolean fishofthieves$isDancing()
    {
        throw new AssertionError("Implemented via mixin");
    }

    default void fishofthieves$setDancing(boolean dancing)
    {
        throw new AssertionError("Implemented via mixin");
    }
}