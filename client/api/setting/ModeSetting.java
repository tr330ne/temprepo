package org.rusherhack.client.api.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import org.rusherhack.core.interfaces.INamed;
import org.rusherhack.core.setting.OptionSetting;

public class ModeSetting<T extends INamed> extends OptionSetting<T> {
   public ModeSetting(String name, T value, T... options) {
      super(name, value, options);
   }

   public ModeSetting(String name, String description, T value, T... options) {
      super(name, description, value, options);
   }

   public T parseValue(String string, boolean set) {
      for (T option : this.options) {
         if (option != null) {
            for (String alias : option.getAliases()) {
               if (string.equalsIgnoreCase(alias)) {
                  if (set) {
                     this.setValue(option);
                  }

                  return option;
               }
            }
         }
      }

      return null;
   }

   @Override
   public String getDisplayValue() {
      T value = this.getValue();
      return value == null ? "null" : value.getDisplayName();
   }

   @Override
   public JsonElement serializeValue() {
      T value = this.getValue();
      return new JsonPrimitive(value == null ? "null" : value.getName());
   }

   @Override
   public boolean deserializeValue(JsonElement json) {
      return json.isJsonPrimitive() && json.getAsJsonPrimitive().isString() ? this.parseValue(json.getAsString(), true) != null : false;
   }

   public ModeSetting<T> setDescription(String description) {
      return (ModeSetting<T>)super.setDescription(description);
   }

   public ModeSetting<T> setVisibility(BooleanSupplier tester) {
      return (ModeSetting<T>)super.setVisibility(tester);
   }

   public ModeSetting<T> onChange(Runnable run) {
      return (ModeSetting<T>)super.onChange(run);
   }

   public ModeSetting<T> onChange(Consumer<T> consumer) {
      return (ModeSetting<T>)super.onChange(consumer);
   }

   public ModeSetting<T> setHidden(boolean hidden) {
      return (ModeSetting<T>)super.setHidden(hidden);
   }

   public ModeSetting<T> setShouldSerialize(boolean shouldSerialize) {
      return (ModeSetting<T>)super.setShouldSerialize(shouldSerialize);
   }
}
