package org.rusherhack.client.api.events.network;

import java.util.UUID;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.jetbrains.annotations.Nullable;
import org.rusherhack.core.event.type.Event;

public class EventPlayerConnection extends Event {
   private final EventPlayerConnection.Action action;
   private final UUID uuid;
   private final String name;
   @Nullable
   private final PlayerInfo playerInfo;

   public EventPlayerConnection(EventPlayerConnection.Action action, UUID uuid, String name, @Nullable PlayerInfo playerInfo) {
      this.action = action;
      this.uuid = uuid;
      this.name = name;
      this.playerInfo = playerInfo;
   }

   public String getName() {
      return this.name;
   }

   public UUID getUUID() {
      return this.uuid;
   }

   public EventPlayerConnection.Action getAction() {
      return this.action;
   }

   @Nullable
   public PlayerInfo getPlayerInfo() {
      return this.playerInfo;
   }

   public enum Action {
      JOIN,
      LEAVE;
   }
}
