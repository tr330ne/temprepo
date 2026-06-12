package org.rusherhack.client.api.events.client.chat;

import org.rusherhack.core.event.type.EventCancellable;

public class EventChatMessage extends EventCancellable {
   private String message;

   public EventChatMessage(String message) {
      this.message = message;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }
}
