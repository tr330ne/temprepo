package org.rusherhack.client.api.utils;

import java.util.HashSet;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.rusherhack.client.api.Globals;
import org.rusherhack.client.api.RusherHackAPI;

public class ChatUtils implements Globals {
   public static final HashSet<String> MESSAGE_COMMANDS = new HashSet<>(List.of("w", "msg", "pm", "t", "tell", "whisper", "r", "reply", "l", "last"));
   public static final HashSet<String> REPLY_COMMANDS = new HashSet<>(List.of("r", "reply", "l", "last"));
   public static final String SECTION_SIGN = "§";

   public static void print(String string) {
      print(string, Style.EMPTY);
   }

   public static void print(String string, Style textStyle) {
      print(Component.literal(string).withStyle(textStyle));
   }

   public static void print(Component component) {
      int color = RusherHackAPI.colors().primaryColor().getRealValue().getRGB();
      print(component, color, Style.EMPTY.withColor(color), 0);
   }

   public static void print(Component component, int tagColor, Style prefixStyle, int typeID) {
      RusherHackAPI.getNotificationManager().chat(component, tagColor, prefixStyle, typeID);
   }

   public static String stripFormatting(String string) {
      return ChatFormatting.stripFormatting(string);
   }
}
