package org.rusherhack.client.api.accessors.client;

import com.mojang.blaze3d.platform.InputConstants.Key;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(KeyMapping.class)
public interface IMixinKeyMapping {
   @Accessor("key")
   Key getKey();

   @Invoker("release")
   void invokeReleaseKey();
}
