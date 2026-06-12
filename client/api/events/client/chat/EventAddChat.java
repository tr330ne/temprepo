package org.rusherhack.client.api.events.client.chat;

import net.minecraft.network.chat.Component;
import org.rusherhack.core.event.type.EventCancellable;

public class EventAddChat extends EventCancellable {
   private Component chatComponent;

   public EventAddChat(Component chatComponent) {
      this.chatComponent = chatComponent;
   }

   public Component getChatComponent() {
      return this.chatComponent;
   }

   public void setChatComponent(Component chatComponent) {
      this.chatComponent = chatComponent;
   }
}
