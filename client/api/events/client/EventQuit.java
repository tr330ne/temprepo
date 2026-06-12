package org.rusherhack.client.api.events.client;

import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.EventCancellable;

public class EventQuit extends EventCancellable {
   private final EventQuit.Reason reason;

   public EventQuit(EventQuit.Reason reason) {
      this.reason = reason;
   }

   public EventQuit.Reason getReason() {
      return this.reason;
   }

   @Override
   public Stage getStage() {
      return super.getStage();
   }

   @Override
   public Stage getPreferredStage() {
      return Stage.POST;
   }

   public enum Reason {
      GAME_QUIT,
      DISCONNECT;
   }
}
