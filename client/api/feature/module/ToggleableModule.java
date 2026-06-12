package org.rusherhack.client.api.feature.module;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.core.bind.IBindable;
import org.rusherhack.core.interfaces.IToggleable;
import org.rusherhack.core.notification.NotificationType;

public abstract class ToggleableModule extends Module implements IToggleable, IBindable {
   private boolean toggled = false;

   public ToggleableModule(String name, ModuleCategory category) {
      this(name, "", category);
   }

   public ToggleableModule(String name, String description, ModuleCategory category) {
      super(name, description, category);
      RusherHackAPI.getBindManager().register(this);
   }

   @Override
   public void toggle() {
      this.setToggled(!this.toggled);
   }

   @Override
   public boolean isToggled() {
      return this.toggled;
   }

   @Override
   public void setToggled(boolean toggled) {
      if (this.toggled != toggled) {
         this.toggled = toggled;

         try {
            if (toggled) {
               this.onEnable();
            } else {
               this.onDisable();
            }
         } catch (Throwable t) {
            this.getLogger().error("Error while toggling module {0}", this.getName(), t);
         }
      }
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onKeybindEvent() {
      this.toggle();
      if (this.shouldNotify() && RusherHackAPI.getNotificationManager().shouldNotifyModuleToggles()) {
         MutableComponent stateComponent = Component.literal(this.toggled ? "enabled" : "disabled")
            .withStyle(Style.EMPTY.withColor(RusherHackAPI.getHudManager().getModuleToggleColor(this.toggled).getRGB()));
         MutableComponent component = Component.literal(String.format("%s has been ", this.getDisplayName())).append(stateComponent);
         RusherHackAPI.getNotificationManager().send(NotificationType.INFO, component, this.hashCode());
      }
   }

   @Override
   public String getBindReference() {
      return String.format("feature_module_toggle_%s", this.getName().toLowerCase());
   }

   @Override
   public boolean isListening() {
      return super.isListening() && this.toggled;
   }

   @Override
   public JsonElement serialize() {
      JsonElement moduleJson = super.serialize();
      JsonObject obj = moduleJson.getAsJsonObject();
      obj.addProperty("toggled", this.toggled);
      return obj;
   }

   @Override
   public boolean deserialize(JsonElement jsonElement) {
      boolean consumed = super.deserialize(jsonElement);
      JsonObject obj = jsonElement.getAsJsonObject();
      if (consumed && obj.has("toggled")) {
         this.setToggled(obj.get("toggled").getAsBoolean());
         return true;
      } else {
         return false;
      }
   }
}
