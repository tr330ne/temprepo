package org.rusherhack.client.api.accessors.render;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ByteBufferBuilder.class)
public interface IMixinByteBufferBuilder {
   @Accessor("pointer")
   long getPointer();
}
