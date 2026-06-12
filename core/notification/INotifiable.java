package org.rusherhack.core.notification;

public interface INotifiable {
   void sendNotification(NotificationType var1, String var2);

   void sendNotification(NotificationType var1, String var2, int var3);
}
