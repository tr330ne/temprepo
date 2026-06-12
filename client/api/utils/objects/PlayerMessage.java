package org.rusherhack.client.api.utils.objects;

import net.minecraft.client.multiplayer.PlayerInfo;
import org.rusherhack.client.api.Globals;
import org.rusherhack.client.api.RusherHackAPI;

public class PlayerMessage {
   private final String sender;
   private final String message;

   public PlayerMessage(String sender, String message) {
      this.sender = sender;
      this.message = message;
   }

   public String getSender() {
      return this.sender;
   }

   public String getMessage() {
      return this.message;
   }

   public static PlayerMessage parse(String message, boolean strict) {
      if (message == null) {
         return new PlayerMessage(null, "");
      }

      String[] parts = message.split(" ");
      String potentialPlayerName = null;
      boolean whisper = false;
      boolean sendingWhisper = false;

      for (int i = 0; i < Math.min(parts.length, 5); i++) {
         String part = parts[i];
         String restOfMessage = message.substring(message.indexOf(part) + part.length()).trim();
         if (potentialPlayerName == null) {
            if (part.startsWith("<") && part.endsWith(">")) {
               potentialPlayerName = part.substring(1, part.length() - 1);
               message = restOfMessage;
               break;
            }

            if (!strict && Globals.mc.player != null) {
               String s = part.replace(":", "");

               for (PlayerInfo player : RusherHackAPI.getServerState().getOnlinePlayers()) {
                  if (player.getProfile().getName().equalsIgnoreCase(s)) {
                     potentialPlayerName = s;
                     break;
                  }
               }
            }
         }

         if (message.contains(":")) {
            if (i > 0 && part.equalsIgnoreCase("whispers:")) {
               String senderName = parts[i - 1];
               whisper = true;
               potentialPlayerName = senderName;
            } else if (part.toLowerCase().contains("whisper") && !part.replace(":", "").equalsIgnoreCase(potentialPlayerName)) {
               whisper = true;
            } else if (part.equalsIgnoreCase("to") && potentialPlayerName == null) {
               sendingWhisper = true;
            }
         }

         if (part.endsWith(":")) {
            message = restOfMessage;
            break;
         }
      }

      if (sendingWhisper) {
         String playerName = Globals.mc.getUser().getName();
         return new PlayerMessage.Whisper(playerName, potentialPlayerName, message);
      } else {
         return whisper
            ? new PlayerMessage.Whisper(potentialPlayerName, Globals.mc.getUser().getName(), message)
            : new PlayerMessage(potentialPlayerName, message);
      }
   }

   public static class Whisper extends PlayerMessage {
      private final String recipient;

      public Whisper(String sender, String recipient, String message) {
         super(sender, message);
         this.recipient = recipient;
      }

      public String getRecipient() {
         return this.recipient;
      }
   }
}
