package org.rusherhack.client.api.accessors.entity;

import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractClientPlayer.class)
public interface IMixinAbstractClientPlayer {
   @Accessor("playerInfo")
   void setPlayerInfo(PlayerInfo var1);

   @Invoker("getPlayerInfo")
   PlayerInfo invokeGetPlayerInfo();
}
