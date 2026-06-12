package org.rusherhack.client.api.ui.theme;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;
import org.rusherhack.client.api.setting.ColorSetting;
import org.rusherhack.client.api.ui.hud.HudHandlerBase;
import org.rusherhack.client.api.ui.panel.PanelHandlerBase;
import org.rusherhack.client.api.ui.window.WindowHandlerBase;
import org.rusherhack.core.feature.IFeatureConfigurable;
import org.rusherhack.core.serialize.JsonSerializable;
import org.rusherhack.core.setting.Setting;

public interface ITheme extends IFeatureConfigurable, JsonSerializable {
   default void initialize() {
      PanelHandlerBase<?> clickGui = this.getClickGuiHandler();
      if (clickGui != null) {
         clickGui.initialize();
      }

      HudHandlerBase hud = this.getHudHandler();
      if (hud != null) {
         hud.initialize();
      }

      WindowHandlerBase windowHandler = this.getWindowHandler();
      if (windowHandler != null) {
         windowHandler.initialize();
      }
   }

   @Nullable
   default PanelHandlerBase<?> getClickGuiHandler() {
      return null;
   }

   @Nullable
   default HudHandlerBase getHudHandler() {
      return null;
   }

   @Nullable
   default WindowHandlerBase getWindowHandler() {
      return null;
   }

   ColorSetting getColorSetting();

   default Color getPrimaryColor() {
      return this.getColorSetting().getValue();
   }

   default JsonElement serialize() {
      JsonObject obj = new JsonObject();
      obj.addProperty("name", this.getName());
      if (!this.getSettings().isEmpty()) {
         JsonArray settings = new JsonArray();

         for (Setting<?> setting : this.getSettings()) {
            settings.add(setting.serialize());
         }

         obj.add("settings", settings);
      }

      PanelHandlerBase<?> clickGuiHandler = this.getClickGuiHandler();
      HudHandlerBase hudHandler = this.getHudHandler();
      PanelHandlerBase<?> hudPanel = hudHandler != null ? hudHandler.getHudManagerPanel() : null;
      if (clickGuiHandler != null && clickGuiHandler.shouldSerialize(false)) {
         obj.add("clickgui", clickGuiHandler.serialize());
      }

      if (hudPanel != null && hudPanel.shouldSerialize(false)) {
         obj.add("hudManager", hudPanel.serialize());
      }

      return obj;
   }

   default boolean deserialize(JsonElement jsonElement) {
      if (jsonElement.isJsonObject() && !jsonElement.isJsonNull()) {
         JsonObject obj = jsonElement.getAsJsonObject();
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

         PanelHandlerBase<?> clickGuiHandler = this.getClickGuiHandler();
         if (obj.has("clickgui") && clickGuiHandler != null) {
            clickGuiHandler.deserialize(obj.get("clickgui"));
         }

         HudHandlerBase hudHandler = this.getHudHandler();
         PanelHandlerBase<?> hudPanel = hudHandler != null ? hudHandler.getHudManagerPanel() : null;
         if (obj.has("hudManager") && hudPanel != null) {
            hudPanel.deserialize(obj.get("hudManager"));
         }

         return true;
      } else {
         return false;
      }
   }
}
