package org.rusherhack.client.api.events.client.internal;

import net.minecraft.network.chat.Component;
import org.rusherhack.core.event.type.EventCancellable;
import org.rusherhack.core.notification.NotificationType;

public class EventNotification extends EventCancellable {
   private final NotificationType type;
   private final Component text;
   private final boolean chat;
   private final boolean hud;

   public EventNotification(NotificationType type, Component text, boolean chat, boolean hud) {
      this.type = type;
      this.text = text;
      this.chat = chat;
      this.hud = hud;
   }

   public NotificationType getType() {
      return this.type;
   }

   public Component getText() {
      return this.text;
   }

   public boolean isChat() {
      return this.chat;
   }

   public boolean isHud() {
      return this.hud;
   }
}
