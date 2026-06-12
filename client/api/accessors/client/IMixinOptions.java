package org.rusherhack.client.api.accessors.client;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Options.class)
public interface IMixinOptions {
   @Accessor("fov")
   @Mutable
   void setFov(OptionInstance<Integer> var1);
}
