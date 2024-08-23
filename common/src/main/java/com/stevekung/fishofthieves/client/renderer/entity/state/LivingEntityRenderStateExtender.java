package com.stevekung.fishofthieves.client.renderer.entity.state;

public interface LivingEntityRenderStateExtender
{
    default boolean isSalmon()
    {
        throw new AssertionError("Implemented via mixin");
    }

    default void setSalmon(boolean isSalmon)
    {
        throw new AssertionError("Implemented via mixin");
    }

    default boolean isDancing()
    {
        throw new AssertionError("Implemented via mixin");
    }

    default void setDancing(boolean dancing)
    {
        throw new AssertionError("Implemented via mixin");
    }
}