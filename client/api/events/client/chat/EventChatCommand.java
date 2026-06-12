package org.rusherhack.client.api.events.client.chat;

import org.rusherhack.core.event.type.EventCancellable;

public class EventChatCommand extends EventCancellable {
   private String command;

   public EventChatCommand(String command) {
      this.command = command;
   }

   public String getCommand() {
      return this.command;
   }

   public void setCommand(String command) {
      this.command = command;
   }
}
