package org.rusherhack.client.api.events.network;

import net.minecraft.network.protocol.Packet;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.EventCancellable;

public abstract class EventPacket extends EventCancellable {
   private final Packet<?> packet;

   public EventPacket(Packet<?> packet) {
      this.packet = packet;
   }

   @Override
   public Stage getPreferredStage() {
      return Stage.PRE;
   }

   public Packet<?> getPacket() {
      return this.packet;
   }

   public static class Receive extends EventPacket {
      public Receive(Packet<?> packet) {
         super(packet);
      }
   }

   public static class Send extends EventPacket {
      public Send(Packet<?> packet) {
         super(packet);
      }
   }
}
