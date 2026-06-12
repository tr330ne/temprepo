package org.rusherhack.client.api.feature.window;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.rusherhack.client.api.Globals;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.command.ToggleableFeatureCommand;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.render.font.IFontRenderer;
import org.rusherhack.client.api.render.graphic.IGraphic;
import org.rusherhack.client.api.ui.ElementBase;
import org.rusherhack.client.api.ui.window.view.WindowView;
import org.rusherhack.core.bind.IBindable;
import org.rusherhack.core.command.AbstractCommand;
import org.rusherhack.core.feature.IFeatureConfigurable;
import org.rusherhack.core.interfaces.IDraggable;
import org.rusherhack.core.interfaces.IHideable;
import org.rusherhack.core.interfaces.IScrollable;
import org.rusherhack.core.interfaces.ITickable;
import org.rusherhack.core.interfaces.ITypeable;
import org.rusherhack.core.serialize.JsonSerializable;
import org.rusherhack.core.setting.Setting;

public abstract class Window
   extends ElementBase
   implements IFeatureConfigurable,
   Globals,
   IDraggable,
   IScrollable,
   ITypeable,
   ITickable,
   IHideable,
   IBindable,
   JsonSerializable {
   private final String name;
   private String displayName;
   private String description;
   @Nullable
   private IGraphic icon = null;
   private final List<Setting<?>> settings = new ArrayList<>();
   protected double width;
   protected double height;
   private boolean focused;
   private boolean hidden = false;
   private boolean dragging = false;
   private double dragDeltaX;
   private double dragDeltaY;
   private final double defaultX;
   private final double defaultY;
   private final double defaultWidth;
   private final double defaultHeight;

   public Window(String title, double width, double height) {
      this(title, 100.0, 100.0, width, height);
   }

   public Window(String title, double x, double y, double width, double height) {
      this.name = title.toLowerCase().replace(" ", "_");
      this.displayName = title;
      this.description = "";
      this.width = width;
      this.height = height;
      this.setX(x);
      this.setY(y);
      this.defaultX = x;
      this.defaultY = y;
      this.defaultWidth = width;
      this.defaultHeight = height;
      RusherHackAPI.getBindManager().register(this);
   }

   public abstract WindowView getRootView();

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      return this.getRootView().mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public void mouseReleased(double mouseX, double mouseY, int button) {
      this.getRootView().mouseReleased(mouseX, mouseY, button);
   }

   @Override
   public void mouseMoved(double mouseX, double mouseY) {
      if (this.isDragging()) {
         this.setX(mouseX - this.dragDeltaX);
         this.setY(mouseY - this.dragDeltaY);
      }
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
      this.getRootView().mouseScrolled(mouseX, mouseY, delta);
      return false;
   }

   @Override
   public void tick() {
      this.getRootView().tick();
   }

   @Override
   public boolean charTyped(char character) {
      return this.getRootView().charTyped(character);
   }

   @Override
   public boolean keyTyped(int key, int scanCode, int modifiers) {
      return this.getRootView().keyTyped(key, scanCode, modifiers);
   }

   @Override
   public boolean isHovered(double mouseX, double mouseY) {
      return RusherHackAPI.getWindowManager().getWindowHandler().isElementHovered(this, mouseX, mouseY);
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
   }

   @Override
   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   @Override
   public String getReferenceKey() {
      return "feature.window." + IFeatureConfigurable.super.getReferenceKey();
   }

   @Override
   public List<Setting<?>> getSettings() {
      return this.settings;
   }

   @Override
   public double getWidth() {
      return this.width;
   }

   @Override
   public double getHeight() {
      return this.height;
   }

   @Override
   public boolean isHidden() {
      return this.hidden;
   }

   public void setHidden(boolean hidden) {
      this.hidden = hidden;
   }

   @Override
   public boolean reset() {
      this.setHidden(false);
      this.setX(this.defaultX);
      this.setY(this.defaultY);
      this.width = this.defaultWidth;
      this.height = this.defaultHeight;
      return true;
   }

   @Override
   public String getBindReference() {
      return String.format("feature_window_%s", this.getName().toLowerCase().replace(" ", "_"));
   }

   @Override
   public void onKeybindEvent() {
      this.setHidden(false);
      RusherHackAPI.schedule(() -> RusherHackAPI.getWindowManager().moveToTop(this));
   }

   public void onClose() {
      this.setHidden(true);
   }

   @Override
   public boolean isDragging() {
      return this.dragging;
   }

   public void setDragging(boolean dragging, double deltaX, double deltaY) {
      this.dragging = dragging;
      this.dragDeltaX = deltaX;
      this.dragDeltaY = deltaY;
   }

   public boolean isFocused() {
      return this.focused;
   }

   public void setFocused(boolean focused) {
      this.focused = focused;
      if (!focused) {
         this.getRootView().unfocus();
      }
   }

   public void setIcon(IGraphic icon) {
      this.icon = icon;
   }

   @Nullable
   public IGraphic getIcon() {
      return this.icon;
   }

   public boolean renderIcon(double x, double y, double width, double height) {
      if (this.icon != null) {
         this.getRenderer().drawGraphicRectangle(this.icon, x, y, width, height);
         return true;
      } else {
         return false;
      }
   }

   public AbstractCommand createCommand() {
      return new ToggleableFeatureCommand(this);
   }

   public JsonElement serialize() {
      JsonObject windowObject = new JsonObject();
      windowObject.addProperty("name", this.getName());
      windowObject.addProperty("hidden", this.isHidden());
      windowObject.addProperty("focused", this.isFocused());
      windowObject.addProperty("x", this.getX());
      windowObject.addProperty("y", this.getY());
      if (!this.settings.isEmpty()) {
         JsonArray settings = new JsonArray();

         for (Setting<?> setting : this.settings) {
            if (setting.shouldSerialize(false)) {
               settings.add(setting.serialize());
            }
         }

         windowObject.add("settings", settings);
      }

      return windowObject;
   }

   public boolean deserialize(JsonElement obj) {
      if (!obj.isJsonObject()) {
         return false;
      }

      JsonObject windowObject = obj.getAsJsonObject();
      if (windowObject.has("x")) {
         this.setX(windowObject.get("x").getAsDouble());
      }

      if (windowObject.has("y")) {
         this.setY(windowObject.get("y").getAsDouble());
      }

      if (windowObject.has("hidden")) {
         this.setHidden(windowObject.get("hidden").getAsBoolean());
      }

      if (windowObject.has("focused")) {
         boolean isFocused = windowObject.get("focused").getAsBoolean();
         this.setFocused(isFocused);
         if (isFocused) {
            RusherHackAPI.getWindowManager().moveToTop(this);
         }
      }

      if (windowObject.has("settings")) {
         for (JsonElement setting : windowObject.get("settings").getAsJsonArray()) {
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
   }

   public IRenderer2D getRenderer() {
      return RusherHackAPI.getWindowManager().getRenderer();
   }

   public IFontRenderer getFontRenderer() {
      return RusherHackAPI.getWindowManager().getFontRenderer();
   }
}
