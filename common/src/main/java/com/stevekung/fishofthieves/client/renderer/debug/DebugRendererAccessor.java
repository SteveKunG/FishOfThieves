package com.stevekung.fishofthieves.client.renderer.debug;

public interface DebugRendererAccessor
{
    default StructureCenterPosDebugRenderer fishofthieves$getStructureCenterPosDebugRenderer()
    {
        throw new AssertionError("Implemented via mixin");
    }
}