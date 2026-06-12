package org.rusherhack.client.api.accessors.render;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Camera.class)
public interface IMixinCamera {
   @Invoker("setPosition")
   void invokeSetPosition(double var1, double var3, double var5);

   @Invoker("setRotation")
   void invokeSetRotation(float var1, float var2);
}
