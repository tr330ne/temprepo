package org.rusherhack.core.setting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import org.rusherhack.core.command.AbstractCommand;
import org.rusherhack.core.command.annotations.CommandExecutor;
import org.rusherhack.core.feature.IFeature;
import org.rusherhack.core.interfaces.IHideable;
import org.rusherhack.core.serialize.JsonSerializable;

public abstract class Setting<T> implements IFeature, IHideable, JsonSerializable {
   protected final String name;
   protected String displayName = "";
   protected String description = "";
   protected T value;
   protected T defaultValue;
   protected boolean hidden = false;
   protected final List<Setting<?>> subSettings = new ArrayList<>();
   protected Setting<?> parent = null;
   protected IFeature attachedFeature = null;
   protected Consumer<T> consumer = null;
   protected BooleanSupplier visibilityTest = () -> true;
   protected boolean shouldSerialize = true;
   protected boolean shouldHaveCommand = true;

   public Setting(String name, T value) {
      this(name, "", value);
   }

   public Setting(String name, String description, T value) {
      this.displayName = name;
      this.name = name.toLowerCase().replace(" ", "_");
      this.description = description;
      this.value = this.defaultValue = value;
   }

   public abstract T parseValue(String var1, boolean var2);

   public abstract String getDisplayValue();

   public abstract JsonElement serializeValue();

   public abstract boolean deserializeValue(JsonElement var1);

   public T getValue() {
      return this.value;
   }

   public T getDefaultValue() {
      return this.defaultValue;
   }

   public void setValue(T value) {
      if (this.value != value) {
         this.value = value;
         if (this.consumer != null) {
            this.consumer.accept(value);
         }
      }
   }

   public void setValueObj(Object value) {
      this.setValue((T)value);
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

   public String getFullName() {
      return this.parent != null ? this.parent.getFullName() + "." + this.name : this.name;
   }

   @Override
   public String getDescription() {
      return this.description;
   }

   public Setting<T> setDescription(String description) {
      this.description = description;
      return this;
   }

   @Override
   public String getReferenceKey() {
      StringBuilder str = new StringBuilder();
      if (this.attachedFeature != null) {
         str.append(this.attachedFeature.getReferenceKey()).append(".");
      }

      return str.append("setting.").append(this.getFullName()).toString();
   }

   public Setting<T> setVisibility(BooleanSupplier tester) {
      this.visibilityTest = tester;
      return this;
   }

   public Setting<T> setHidden(boolean hidden) {
      this.hidden = hidden;
      return this;
   }

   public boolean isHiddenByDefault() {
      return this.hidden;
   }

   @Override
   public boolean isHidden() {
      return this.hidden || !this.visibilityTest.getAsBoolean();
   }

   @Override
   public boolean reset() {
      this.reset(true);
      return true;
   }

   public void reset(boolean includeSubSettings) {
      this.setValue(this.defaultValue);
      if (includeSubSettings) {
         for (Setting<?> setting : this.subSettings) {
            setting.reset(true);
         }
      }
   }

   public void addSubSettings(Setting<?>... settings) {
      this.subSettings.addAll(Arrays.asList(settings));

      for (Setting<?> subSetting : settings) {
         subSetting.parent = this;
      }
   }

   public List<Setting<?>> getSubSettings() {
      return this.subSettings;
   }

   public List<Setting<?>> getAllSubSettings() {
      List<Setting<?>> settings = new ArrayList<>(this.subSettings);

      for (Setting<?> setting : this.subSettings) {
         settings.addAll(setting.getAllSubSettings());
      }

      return settings;
   }

   public Setting<?> getSubSetting(String name) {
      String[] settings = name.replace(":", ".").split("\\.");
      String baseSettingName = settings[0];

      for (Setting<?> setting : this.subSettings) {
         if (setting.getName().equalsIgnoreCase(baseSettingName)) {
            if (settings.length > 1) {
               return setting.getSubSetting(name.substring(baseSettingName.length() + 1));
            }

            return setting;
         }
      }

      return null;
   }

   @Deprecated
   public Setting<T> setChangeAction(Runnable run) {
      return this.onChange(run);
   }

   public Setting<T> onChange(Runnable run) {
      return this.onChange(t -> run.run());
   }

   public Setting<T> onChange(Consumer<T> consumer) {
      this.consumer = consumer;
      return this;
   }

   public Consumer<T> getConsumer() {
      return this.consumer;
   }

   public Setting<T> setShouldHaveCommand(boolean shouldHaveCommand) {
      this.shouldHaveCommand = shouldHaveCommand;
      return this;
   }

   public boolean shouldHaveCommand() {
      return this.shouldHaveCommand;
   }

   public Setting<T> setShouldSerialize(boolean shouldSerialize) {
      this.shouldSerialize = shouldSerialize;
      return this;
   }

   @Override
   public boolean shouldSerialize(boolean autosave) {
      return this.shouldSerialize;
   }

   public JsonElement serialize() {
      JsonObject obj = new JsonObject();
      obj.addProperty("name", this.getName());
      obj.add("value", this.serializeValue());
      if (!this.subSettings.isEmpty()) {
         obj.add("subSettings", this.serializeSubSettings());
      }

      return obj;
   }

   public boolean deserialize(JsonElement jsonElement) {
      if (jsonElement.isJsonObject() && !jsonElement.isJsonNull()) {
         JsonObject obj = jsonElement.getAsJsonObject();
         boolean consumed = obj.has("value") && this.deserializeValue(obj.get("value"));
         if (obj.has("subSettings") && this.deserializeSubSettings(obj.get("subSettings"))) {
            consumed = true;
         }

         return consumed;
      } else {
         return false;
      }
   }

   private JsonElement serializeSubSettings() {
      JsonArray array = new JsonArray();

      for (Setting<?> setting : this.subSettings) {
         if (setting.shouldSerialize(false)) {
            array.add(setting.serialize());
         }
      }

      return array;
   }

   private boolean deserializeSubSettings(JsonElement subSettings) {
      if (!subSettings.isJsonArray()) {
         return false;
      }

      boolean consumed = false;

      for (JsonElement element : subSettings.getAsJsonArray()) {
         if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            if (obj.has("name")) {
               String name = obj.get("name").getAsString();

               for (Setting<?> subSetting : this.subSettings) {
                  if (subSetting.getName().equalsIgnoreCase(name)) {
                     if (subSetting.deserialize(element)) {
                        consumed = true;
                     }
                     break;
                  }
               }
            }
         }
      }

      return consumed;
   }

   public AbstractCommand createCommand(AbstractCommand parent) {
      return new Setting.SettingCommand<Setting<T>>(parent, this) {
         @CommandExecutor
         @CommandExecutor.Argument("value")
         private String setValue(Setting.ValueHolder value) {
            this.setting.setValueObj(value.value());
            return String.format("Set %s to %s", this.setting.getName(), this.setting.getDisplayValue());
         }
      };
   }

   public void setAttachedFeature(IFeature attachedFeature) {
      this.attachedFeature = attachedFeature;

      for (Setting<?> subSetting : this.getSubSettings()) {
         subSetting.setAttachedFeature(attachedFeature);
      }
   }

   public void setParent(Setting<?> parent) {
      this.parent = parent;
   }

   public Setting<?> getParent() {
      return this.parent;
   }

   public static class SettingCommand<T extends Setting<?>> extends AbstractCommand {
      protected final T setting;

      public SettingCommand(AbstractCommand parent, T setting) {
         super(parent, setting.getFullName().toLowerCase(), setting.getDescription());
         this.setting = setting;
      }

      @Override
      public boolean isHidden() {
         return true;
      }

      public T getSetting() {
         return this.setting;
      }
   }

   public record ValueHolder(Setting<?> setting, Object value) {
   }
}
