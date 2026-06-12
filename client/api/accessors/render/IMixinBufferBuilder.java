package org.rusherhack.client.api.accessors.render;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BufferBuilder.class)
public interface IMixinBufferBuilder {
   @Accessor("building")
   boolean isBuilding();

   @Accessor("buffer")
   ByteBufferBuilder getBuffer();
}
