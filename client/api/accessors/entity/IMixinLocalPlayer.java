package org.rusherhack.client.api.accessors.entity;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LocalPlayer.class)
public interface IMixinLocalPlayer {
   @Accessor("xLast")
   double getLastX();

   @Accessor("yLast1")
   double getLastY();

   @Accessor("zLast")
   double getLastZ();

   @Accessor("lastOnGround")
   boolean wasLastOnGround();

   @Accessor("jumpRidingScale")
   void setJumpRidingScale(float var1);

   @Accessor("usingItemHand")
   InteractionHand getUsingItemHand();

   @Invoker("sendPosition")
   void invokeSendPosition();

   @Invoker("canStartSprinting")
   boolean invokeCanStartSprinting();
}
