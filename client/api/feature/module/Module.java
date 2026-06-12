package org.rusherhack.client.api.feature.module;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.network.chat.Component;
import org.rusherhack.client.api.Globals;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.command.ModuleCommand;
import org.rusherhack.core.command.AbstractCommand;
import org.rusherhack.core.event.listener.EventListener;
import org.rusherhack.core.logging.ILoggable;
import org.rusherhack.core.logging.ILogger;
import org.rusherhack.core.notification.NotificationType;
import org.rusherhack.core.setting.Setting;

public abstract class Module implements IModule, EventListener, ILoggable, Globals {
   private final String name;
   private String displayName;
   private String description;
   private final List<String> aliases = new ArrayList<>();
   private final ModuleCategory category;
   private boolean hidden = false;
   private boolean drawn = true;
   private boolean notifications = true;
   private boolean listening = true;
   private final List<Setting<?>> settings = new ArrayList<>();
   protected final ILogger logger;

   public Module(String name, ModuleCategory category) {
      this(name, "", category);
   }

   public Module(String name, String description, ModuleCategory category) {
      this.name = this.displayName = name;
      this.description = description;
      this.category = category;
      this.logger = RusherHackAPI.createLogger(this.name);
      this.addAliases(this.name);
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String getDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(String displayName) {
      this.displayName = displayName;
      this.addAliases(displayName);
   }

   @Override
   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   @Override
   public String[] getAliases() {
      return this.aliases.toArray(new String[0]);
   }

   public void addAliases(String... aliases) {
      this.aliases.addAll(Arrays.asList(aliases));
   }

   @Override
   public String getReferenceKey() {
      return "feature.module." + IModule.super.getReferenceKey();
   }

   @Override
   public ModuleCategory getCategory() {
      return this.category;
   }

   @Override
   public void sendNotification(NotificationType type, String message) {
      if (this.shouldNotify()) {
         RusherHackAPI.getNotificationManager().send(type, this.getName(), message);
      }
   }

   @Override
   public void sendNotification(NotificationType type, String message, int id) {
      if (this.shouldNotify()) {
         RusherHackAPI.getNotificationManager().send(type, this.getName(), message, id);
      }
   }

   public void sendNotification(NotificationType type, Component component) {
      if (this.shouldNotify()) {
         RusherHackAPI.getNotificationManager().send(type, this.getName(), component);
      }
   }

   public void sendNotification(NotificationType type, Component component, int id) {
      if (this.shouldNotify()) {
         RusherHackAPI.getNotificationManager().send(type, this.getName(), component, id);
      }
   }

   @Override
   public void registerSettings(Setting<?>... settings) {
      IModule.super.registerSettings(settings);
   }

   @Override
   public List<Setting<?>> getSettings() {
      return this.settings;
   }

   @Override
   public boolean isDrawn() {
      return !this.isHidden() && this.drawn;
   }

   @Override
   public void setDrawn(boolean drawn) {
      this.drawn = drawn;
   }

   @Override
   public boolean isHidden() {
      return this.hidden;
   }

   public void setHidden(boolean hidden) {
      this.hidden = hidden;
   }

   public boolean shouldNotify() {
      return this.notifications;
   }

   @Deprecated(forRemoval = true)
   public boolean areNotificationsEnabled() {
      return this.shouldNotify();
   }

   public void setNotify(boolean notifications) {
      this.notifications = notifications;
   }

   @Deprecated(forRemoval = true)
   public void setNotifications(boolean notifications) {
      this.setNotify(notifications);
   }

   public JsonElement serialize() {
      JsonObject obj = new JsonObject();
      obj.addProperty("name", this.name);
      obj.addProperty("drawn", this.drawn);
      obj.addProperty("notifications", this.notifications);
      if (!this.settings.isEmpty()) {
         JsonArray settings = new JsonArray();

         for (Setting<?> setting : this.settings) {
            if (setting.shouldSerialize(false)) {
               settings.add(setting.serialize());
            }
         }

         obj.add("settings", settings);
      }

      return obj;
   }

   public boolean deserialize(JsonElement jsonElement) {
      if (jsonElement.isJsonObject() && !jsonElement.isJsonNull()) {
         JsonObject obj = jsonElement.getAsJsonObject();
         if (!obj.has("name")) {
            return false;
         }

         if (obj.has("hidden")) {
            this.drawn = !obj.get("hidden").getAsBoolean();
         } else if (obj.has("drawn")) {
            this.drawn = obj.get("drawn").getAsBoolean();
         }

         if (obj.has("notifications")) {
            this.notifications = obj.get("notifications").getAsBoolean();
         }

         if (obj.has("settings")) {
            for (JsonElement setting : obj.get("settings").getAsJsonArray()) {
               if (setting.isJsonObject()) {
                  JsonObject settingObj = setting.getAsJsonObject();
                  String name = settingObj.get("name").getAsString();
                  Setting<?> matchedSetting = this.getSetting(name);
                  if (matchedSetting != null) {
                     matchedSetting.deserialize(setting);
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean isListening() {
      return this.listening;
   }

   public void setListening(boolean state) {
      this.listening = state;
   }

   @Override
   public ILogger getLogger() {
      return this.logger;
   }

   public AbstractCommand createCommand() {
      return new ModuleCommand(this);
   }
}
