package org.rusherhack.client.api.accessors.render;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BufferSource.class)
public interface IMixinMultiBufferSource$BufferSource {
   @Accessor("sharedBuffer")
   ByteBufferBuilder getSharedBuffer();

   @Accessor("sharedBuffer")
   @Mutable
   void setSharedBuffer(ByteBufferBuilder var1);
}
