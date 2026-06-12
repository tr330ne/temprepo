package org.rusherhack.client.api.accessors.entity;

import net.minecraft.world.entity.projectile.ThrowableProjectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ThrowableProjectile.class)
public interface IMixinThrowableProjectile {
   @Invoker("getDefaultGravity")
   double invokeGetGravity();
}
