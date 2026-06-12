package org.rusherhack.client.api.accessors.entity;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface IMixinLivingEntity {
   @Accessor("noJumpDelay")
   int getNoJumpDelay();

   @Accessor("noJumpDelay")
   void setNoJumpDelay(int var1);

   @Accessor("attackStrengthTicker")
   int getAttackStrengthTicker();

   @Invoker("calculateFallDamage")
   int invokeCalculateFallDamage(float var1, float var2);
}
