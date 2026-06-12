package org.rusherhack.client.api.system;

import java.util.Collection;
import net.minecraft.client.multiplayer.PlayerInfo;

public interface IServerState {
   Collection<PlayerInfo> getOnlinePlayers();

   default float getTPS() {
      return this.getTPS(true);
   }

   float getTPS(boolean var1);

   float getPing();

   long getLastUpdate();

   String getServerIP();

   boolean isPlayerSprinting();

   boolean isPlayerSneaking();

   double getPlayerX();

   double getPlayerY();

   double getPlayerZ();

   float getPlayerYaw();

   float getPlayerPitch();

   int getPlayerSlot();
}
