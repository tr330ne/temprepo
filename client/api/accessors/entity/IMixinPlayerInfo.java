package org.rusherhack.client.api.accessors.entity;

import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerInfo.class)
public interface IMixinPlayerInfo {
   @Invoker("setGameMode")
   void invokeSetGameMode(GameType var1);
}
