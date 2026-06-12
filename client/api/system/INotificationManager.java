package org.rusherhack.client.api.system;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.core.logging.ILog;
import org.rusherhack.core.notification.NotificationType;

public interface INotificationManager extends ILog {
   boolean shouldNotifyModuleToggles();

   default void send(NotificationType type, String text) {
      this.send(type, "", text);
   }

   default void send(NotificationType type, Component text) {
      this.send(type, "", text);
   }

   default void send(NotificationType type, String prefix, String text) {
      this.send(type, prefix, text, type.getTypeID());
   }

   default void send(NotificationType type, String prefix, Component text) {
      this.send(type, prefix, text, type.getTypeID());
   }

   default void send(NotificationType type, String text, int id) {
      this.send(type, "", text, id);
   }

   default void send(NotificationType type, Component text, int id) {
      this.send(type, "", text, id);
   }

   default void send(NotificationType type, String prefix, String text, int id) {
      this.send(type, prefix, Component.literal(text), id);
   }

   void send(NotificationType var1, String var2, Component var3, int var4);

   default void chat(String string) {
      this.chat(string, Style.EMPTY);
   }

   default void chat(String string, Style textStyle) {
      this.chat(Component.literal(string).withStyle(textStyle));
   }

   default void chat(Component component) {
      int color = RusherHackAPI.colors().primaryColor().getRealValue().getRGB();
      this.chat(component, color, Style.EMPTY.withColor(color), 0);
   }

   void chat(Component var1, int var2, Style var3, int var4);

   @Override
   default void info(String message) {
      this.send(NotificationType.INFO, message);
   }

   @Override
   default void warn(String message) {
      this.send(NotificationType.WARNING, message);
   }

   @Override
   default void error(String message) {
      this.send(NotificationType.ERROR, message);
   }

   @Override
   default void debug(String message) {
      this.send(NotificationType.DEBUG, message);
   }
}
