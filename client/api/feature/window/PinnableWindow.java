package org.rusherhack.client.api.feature.window;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.rusherhack.core.interfaces.IPinnable;

public abstract class PinnableWindow extends Window implements IPinnable {
   private boolean pinned = false;

   public PinnableWindow(String title, double width, double height) {
      super(title, width, height);
   }

   public PinnableWindow(String title, double x, double y, double width, double height) {
      super(title, x, y, width, height);
   }

   @Override
   public boolean isPinned() {
      return this.pinned;
   }

   @Override
   public void setPinned(boolean pinned) {
      this.pinned = pinned;
   }

   @Override
   public JsonElement serialize() {
      JsonElement windowElement = super.serialize();
      JsonObject obj = windowElement.getAsJsonObject();
      obj.addProperty("pinned", this.pinned);
      return obj;
   }

   @Override
   public boolean deserialize(JsonElement jsonElement) {
      boolean consumed = super.deserialize(jsonElement);
      if (consumed) {
         JsonObject obj = jsonElement.getAsJsonObject();
         if (obj.has("pinned")) {
            this.pinned = obj.get("pinned").getAsBoolean();
         }

         return true;
      } else {
         return false;
      }
   }
}
