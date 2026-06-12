package org.rusherhack.client.api.accessors.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.multiplayer.prediction.PredictiveAction;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MultiPlayerGameMode.class)
public interface IMixinMultiPlayerGameMode {
   @Accessor("connection")
   ClientPacketListener getConnection();

   @Accessor("destroyBlockPos")
   BlockPos getDestroyBlockPos();

   @Accessor("destroyProgress")
   float getDestroyProgress();

   @Accessor("destroyProgress")
   void setDestroyProgress(float var1);

   @Accessor("destroyDelay")
   int getDestroyDelay();

   @Accessor("destroyDelay")
   void setDestroyDelay(int var1);

   @Accessor("isDestroying")
   void setIsDestroying(boolean var1);

   @Invoker("startPrediction")
   void invokeStartPrediction(ClientLevel var1, PredictiveAction var2);

   @Invoker("ensureHasSentCarriedItem")
   void invokeEnsureHasSentCarriedItem();

   @Invoker("performUseItemOn")
   InteractionResult invokePerformUseItemOn(LocalPlayer var1, InteractionHand var2, BlockHitResult var3);
}
