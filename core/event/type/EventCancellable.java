package org.rusherhack.core.event.type;

import org.rusherhack.core.event.stage.Stage;

public class EventCancellable extends Event {
   private boolean cancelled = false;

   public EventCancellable() {
   }

   public EventCancellable(Stage stage) {
      super(stage);
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }
}
