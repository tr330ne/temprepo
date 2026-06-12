package org.rusherhack.client.api.feature.window;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class ResizeableWindow extends PinnableWindow {
   protected double minWidth = 100.0;
   protected double minHeight = 50.0;
   protected double maxWidth = -1.0;
   protected double maxHeight = -1.0;
   protected boolean resizing = false;
   private double resizeDeltaX;
   private double resizeDeltaY;

   public ResizeableWindow(String title, double width, double height) {
      super(title, width, height);
   }

   public ResizeableWindow(String title, double x, double y, double width, double height) {
      super(title, x, y, width, height);
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      return super.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public void mouseReleased(double mouseX, double mouseY, int button) {
      this.resizing = false;
      super.mouseReleased(mouseX, mouseY, button);
   }

   @Override
   public void mouseMoved(double mouseX, double mouseY) {
      if (this.resizing) {
         this.width = mouseX - this.resizeDeltaX;
         this.height = mouseY - this.resizeDeltaY;
         if (this.minWidth > 0.0 && this.width < this.minWidth) {
            this.width = this.minWidth;
         }

         if (this.minHeight > 0.0 && this.height < this.minHeight) {
            this.height = this.minHeight;
         }

         if (this.maxWidth > 0.0 && this.width > this.maxWidth) {
            this.width = this.maxWidth;
         }

         if (this.maxHeight > 0.0 && this.height > this.maxHeight) {
            this.height = this.maxHeight;
         }
      }

      super.mouseMoved(mouseX, mouseY);
   }

   public void setResizing(boolean resizing, double resizeDeltaX, double resizeDeltaY) {
      this.resizing = resizing;
      this.resizeDeltaX = resizeDeltaX;
      this.resizeDeltaY = resizeDeltaY;
   }

   public void setMaxWidth(double maxWidth) {
      this.maxWidth = maxWidth;
   }

   public void setMaxHeight(double maxHeight) {
      this.maxHeight = maxHeight;
   }

   public void setMinWidth(double minWidth) {
      this.minWidth = minWidth;
   }

   public void setMinHeight(double minHeight) {
      this.minHeight = minHeight;
   }

   public boolean isResizing() {
      return this.resizing;
   }

   @Override
   public JsonElement serialize() {
      JsonElement windowElement = super.serialize();
      JsonObject obj = windowElement.getAsJsonObject();
      obj.addProperty("width", this.width);
      obj.addProperty("height", this.height);
      return obj;
   }

   @Override
   public boolean deserialize(JsonElement jsonElement) {
      boolean consumed = super.deserialize(jsonElement);
      if (consumed) {
         JsonObject obj = jsonElement.getAsJsonObject();
         this.width = obj.get("width").getAsDouble();
         this.height = obj.get("height").getAsDouble();
         return true;
      } else {
         return false;
      }
   }
}
