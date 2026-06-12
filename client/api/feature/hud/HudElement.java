package org.rusherhack.client.api.feature.hud;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.List;
import org.rusherhack.client.api.Globals;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.command.ToggleableFeatureCommand;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.client.api.render.font.IFontRenderer;
import org.rusherhack.client.api.ui.ScaledElementBase;
import org.rusherhack.core.bind.IBindable;
import org.rusherhack.core.command.AbstractCommand;
import org.rusherhack.core.event.listener.EventListener;
import org.rusherhack.core.logging.ILoggable;
import org.rusherhack.core.logging.ILogger;
import org.rusherhack.core.setting.Setting;

public abstract class HudElement extends ScaledElementBase implements IHudElement, IBindable, EventListener, ILoggable, Globals {
   private final String name;
   private String displayName;
   private boolean toggled = false;
   private String description = "";
   private final List<Setting<?>> settings = new ArrayList<>();
   protected HudElement.SnapPoint snapPoint = null;
   protected HudElement.Alignment alignment = HudElement.Alignment.TOP_LEFT;
   protected boolean dragging = false;
   private double dragDeltaX = 0.0;
   private double dragDeltaY = 0.0;
   protected final ILogger logger;

   public HudElement(String name) {
      this.name = this.displayName = name;
      this.logger = RusherHackAPI.createLogger(this.getName());
      super.setX(0.25);
      super.setY(0.25);
      RusherHackAPI.getBindManager().register(this);
   }

   public HudElement(String name, String description) {
      this(name);
      this.setDescription(description);
   }

   @Override
   public void render(RenderContext context, double mouseX, double mouseY) {
      if (this.shouldUpdateAlignment()) {
         HudElement.Alignment newAlignment = calculateAlignment(this);
         if (!this.alignment.equals(newAlignment)) {
            this.correctPosition(this.alignment, newAlignment);
            this.alignment = newAlignment;
         }
      }

      this.renderContent(context, (double)mouseX, (double)mouseY);
   }

   protected void correctPosition(HudElement.Alignment old, HudElement.Alignment newAlignment) {
      double width = this.getScaledWidth();
      double height = this.getScaledHeight();
      boolean oldLeft = old.equals(HudElement.Alignment.TOP_LEFT) || old.equals(HudElement.Alignment.BOTTOM_LEFT);
      boolean oldRight = old.equals(HudElement.Alignment.TOP_RIGHT) || old.equals(HudElement.Alignment.BOTTOM_RIGHT);
      boolean oldTop = old.equals(HudElement.Alignment.TOP_LEFT) || old.equals(HudElement.Alignment.TOP_RIGHT);
      boolean oldBottom = old.equals(HudElement.Alignment.BOTTOM_LEFT) || old.equals(HudElement.Alignment.BOTTOM_RIGHT);
      boolean enteringLeft = oldRight && (newAlignment.equals(HudElement.Alignment.TOP_LEFT) || newAlignment.equals(HudElement.Alignment.BOTTOM_LEFT));
      boolean enteringRight = oldLeft && (newAlignment.equals(HudElement.Alignment.TOP_RIGHT) || newAlignment.equals(HudElement.Alignment.BOTTOM_RIGHT));
      boolean enteringTop = oldBottom && (newAlignment.equals(HudElement.Alignment.TOP_LEFT) || newAlignment.equals(HudElement.Alignment.TOP_RIGHT));
      boolean enteringBottom = oldTop && (newAlignment.equals(HudElement.Alignment.BOTTOM_LEFT) || newAlignment.equals(HudElement.Alignment.BOTTOM_RIGHT));
      if (enteringLeft) {
         this.setX(this.getX() - width);
      }

      if (enteringRight) {
         this.setX(this.getX() + width);
      }

      if (enteringTop) {
         this.setY(this.getY() - height);
      }

      if (enteringBottom) {
         this.setY(this.getY() + height);
      }
   }

   public void postRender(RenderContext context, double mouseX, double mouseY) {
   }

   public boolean shouldSkipPostRender() {
      return false;
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
      return "feature.hud_element." + IHudElement.super.getReferenceKey();
   }

   @Override
   public List<Setting<?>> getSettings() {
      return this.settings;
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0) {
         this.dragDeltaX = mouseX - this.getStartX();
         this.dragDeltaY = mouseY - this.getStartY();
         this.dragging = true;
         this.snapPoint = null;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void mouseReleased(double mouseX, double mouseY, int button) {
      if (this.isDragging() && button == 0) {
         this.dragging = false;
      }
   }

   @Override
   public void mouseMoved(double mouseX, double mouseY) {
      if (this.isDragging()) {
         this.setStartX(mouseX - this.dragDeltaX);
         this.setStartY(mouseY - this.dragDeltaY);
      }
   }

   @Override
   public boolean isDragging() {
      return this.dragging;
   }

   @Override
   public boolean isHovered(double mouseX, double mouseY) {
      return RusherHackAPI.getThemeManager().getHudHandler().isElementHovered(this, mouseX, mouseY);
   }

   @Override
   public void toggle() {
      this.setToggled(!this.isToggled());
   }

   @Override
   public void onEnable() {
      this.toggled = true;
   }

   @Override
   public void onDisable() {
      this.toggled = false;
   }

   @Override
   public String getBindReference() {
      return String.format("feature_hudelement_toggle_%s", this.getName().toLowerCase());
   }

   @Override
   public void onKeybindEvent() {
      this.toggle();
   }

   @Override
   public ILogger getLogger() {
      return this.logger;
   }

   @Override
   public boolean isToggled() {
      return this.toggled;
   }

   @Override
   public void setToggled(boolean toggled) {
      if (this.toggled != toggled) {
         try {
            if (toggled) {
               this.onEnable();
            } else {
               this.onDisable();
            }
         } catch (Throwable t) {
            this.getLogger().error("Error while toggling hud element {0}", this.getName(), t);
         }
      }
   }

   @Override
   public boolean isListening() {
      return this.toggled;
   }

   public AbstractCommand createCommand() {
      return new ToggleableFeatureCommand(this);
   }

   @Override
   public void setX(double x) {
      Window window = mc.getWindow();
      x /= window.getGuiScaledWidth();
      super.setX(x);
   }

   @Override
   public void setY(double y) {
      Window window = mc.getWindow();
      y /= window.getGuiScaledHeight();
      super.setY(y);
   }

   @Override
   public double getX() {
      int screenWidth = mc.getWindow().getGuiScaledWidth();
      return super.getX() * screenWidth;
   }

   @Override
   public double getY() {
      int screenHeight = mc.getWindow().getGuiScaledHeight();
      return super.getY() * screenHeight;
   }

   public double getStartX() {
      double x = this.getX();
      switch (this.getAlignment()) {
         case TOP_LEFT:
         case TOP_CENTER:
         case BOTTOM_LEFT:
            x += 0.0;
            break;
         case TOP_RIGHT:
         case BOTTOM_RIGHT:
            x -= this.getScaledWidth();
      }

      return x;
   }

   public double getStartY() {
      double y = this.getY();
      switch (this.getAlignment()) {
         case TOP_LEFT:
         case TOP_CENTER:
         case TOP_RIGHT:
            y += 0.0;
            break;
         case BOTTOM_LEFT:
         case BOTTOM_RIGHT:
            y -= this.getScaledHeight();
      }

      return y;
   }

   public void setStartX(double x) {
      switch (this.getAlignment()) {
         case TOP_LEFT:
         case TOP_CENTER:
         case BOTTOM_LEFT:
            this.setX(x);
            break;
         case TOP_RIGHT:
         case BOTTOM_RIGHT:
            this.setX(x + this.getScaledWidth());
      }
   }

   public void setStartY(double y) {
      switch (this.getAlignment()) {
         case TOP_LEFT:
         case TOP_CENTER:
         case TOP_RIGHT:
            this.setY(y);
            break;
         case BOTTOM_LEFT:
         case BOTTOM_RIGHT:
            this.setY(y + this.getScaledHeight());
      }
   }

   @Override
   public double getScale() {
      return RusherHackAPI.getHudManager().getScale();
   }

   @Override
   public IFontRenderer getFontRenderer() {
      return RusherHackAPI.fonts().getHudFontRenderer();
   }

   public HudElement.SnapPoint getSnapPoint() {
      return this.snapPoint;
   }

   public void setSnapPoint(HudElement.SnapPoint snapPoint) {
      this.snapPoint = snapPoint;
   }

   public JsonElement serialize() {
      JsonObject obj = new JsonObject();
      obj.addProperty("name", this.name);
      obj.addProperty("toggled", this.toggled);
      obj.addProperty("x", super.getX());
      obj.addProperty("y", super.getY());
      obj.addProperty("snap", this.snapPoint != null ? this.snapPoint.name() : "null");
      obj.addProperty("alignment", this.alignment != null ? this.alignment.name() : "null");
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
         if (obj.has("name") && obj.get("name").getAsString().equalsIgnoreCase(this.name)) {
            if (obj.has("toggled")) {
               this.toggled = obj.get("toggled").getAsBoolean();
            }

            if (obj.has("x")) {
               super.setX(obj.get("x").getAsDouble());
            }

            if (obj.has("y")) {
               super.setY(obj.get("y").getAsDouble());
            }

            if (obj.has("snap")) {
               String snap = obj.get("snap").getAsString();

               try {
                  this.snapPoint = HudElement.SnapPoint.valueOf(snap);
               } catch (IllegalArgumentException e) {
                  this.snapPoint = null;
               }
            }

            if (obj.has("alignment")) {
               String alignment = obj.get("alignment").getAsString();

               try {
                  this.alignment = HudElement.Alignment.valueOf(alignment);
               } catch (IllegalArgumentException e) {
                  this.alignment = null;
               }
            } else {
               this.alignment = calculateAlignment(this.getX(), this.getY());
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
      } else {
         return false;
      }
   }

   @Override
   public boolean reset() {
      boolean reset = IHudElement.super.reset();
      this.setStartX(100.0);
      this.setStartY(100.0);
      return reset;
   }

   protected boolean shouldUpdateAlignment() {
      return true;
   }

   public HudElement.Alignment getAlignment() {
      return this.alignment;
   }

   public static HudElement.Alignment calculateAlignment(HudElement element) {
      if (element.snapPoint != null) {
         return switch (element.snapPoint) {
            case TOP_LEFT -> HudElement.Alignment.TOP_LEFT;
            case TOP_CENTER -> HudElement.Alignment.TOP_CENTER;
            case TOP_RIGHT -> HudElement.Alignment.TOP_RIGHT;
            case BOTTOM_LEFT -> HudElement.Alignment.BOTTOM_LEFT;
            case BOTTOM_RIGHT -> HudElement.Alignment.BOTTOM_RIGHT;
         };
      } else {
         return element.getScaledHeight() > mc.getWindow().getGuiScaledHeight()
            ? element.alignment
            : calculateAlignment(element.getX(), element.getStartY() + element.getScaledHeight() / 2.0);
      }
   }

   public static HudElement.Alignment calculateAlignment(double x, double y) {
      Window window = mc.getWindow();
      boolean left = x < window.getGuiScaledWidth() / 2.0F;
      boolean top = y <= window.getGuiScaledHeight() / 2.0F;
      if (left && top) {
         return HudElement.Alignment.TOP_LEFT;
      } else if (left) {
         return HudElement.Alignment.BOTTOM_LEFT;
      } else {
         return top ? HudElement.Alignment.TOP_RIGHT : HudElement.Alignment.BOTTOM_RIGHT;
      }
   }

   public enum Alignment {
      TOP_LEFT,
      TOP_CENTER,
      TOP_RIGHT,
      BOTTOM_LEFT,
      BOTTOM_RIGHT;
   }

   public enum SnapPoint {
      TOP_LEFT(0.0, 0.0),
      TOP_CENTER(0.5, 0.0),
      TOP_RIGHT(1.0, 0.0),
      BOTTOM_LEFT(0.0, 1.0),
      BOTTOM_RIGHT(1.0, 1.0);

      public final double x;
      public final double y;

      SnapPoint(double x, double y) {
         this.x = x;
         this.y = y;
      }
   }
}
