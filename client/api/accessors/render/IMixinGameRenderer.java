package org.rusherhack.client.api.accessors.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRenderer.class)
public interface IMixinGameRenderer {
   @Accessor("renderBlockOutline")
   void setRenderBlockOutline(boolean var1);

   @Invoker("bobView")
   void invokeBobView(PoseStack var1, float var2);
}
