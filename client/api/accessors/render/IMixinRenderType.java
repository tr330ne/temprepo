package org.rusherhack.client.api.accessors.render;

import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderType.class)
public interface IMixinRenderType {
   @Accessor("sortOnUpload")
   boolean isTranslucent();
}
