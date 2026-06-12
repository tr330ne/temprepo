package org.rusherhack.core.notification.type;

import org.rusherhack.core.notification.NotificationType;

public abstract class Notification {
   private final String text;
   private final NotificationType type;
   private final int id;

   public Notification(String text, NotificationType type) {
      this(text, type, type.getTypeID());
   }

   public Notification(String text, NotificationType type, int id) {
      this.text = text;
      this.type = type;
      this.id = id;
   }

   public String getText() {
      return this.text;
   }

   public NotificationType getType() {
      return this.type;
   }

   public int getID() {
      return this.id;
   }
}
