package org.rusherhack.core.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class StringSetting extends Setting<String> {
   private final LinkedHashSet<String> options = new LinkedHashSet<>();
   private boolean isNameVisible = true;

   public StringSetting(String name, String value) {
      this(name, "", value);
   }

   public StringSetting(String name, String description, String value) {
      this(name, description, value);
   }

   public StringSetting(String name, String description, String value, String... options) {
      super(name, description, value);
      this.options.addAll(List.of(options));
   }

   public LinkedHashSet<String> getOptions() {
      return this.options;
   }

   public void addOptions(String... options) {
      this.options.addAll(List.of(options));
   }

   public void setValue(String value) {
      boolean hasOptions = !this.options.isEmpty();
      if (hasOptions) {
         for (String option : this.options) {
            if (option.equalsIgnoreCase(value)) {
               super.setValue(option);
               return;
            }
         }
      } else {
         super.setValue(value);
      }
   }

   public String parseValue(String string, boolean set) {
      boolean hasOptions = !this.options.isEmpty();
      if (hasOptions) {
         for (String option : this.options) {
            if (option.equalsIgnoreCase(string)) {
               if (set) {
                  super.setValue(option);
               }

               return option;
            }
         }

         return null;
      } else {
         if (set) {
            super.setValue(string);
         }

         return string;
      }
   }

   @Override
   public String getDisplayValue() {
      return this.getValue();
   }

   @Override
   public boolean deserializeValue(JsonElement json) {
      if (!json.isJsonPrimitive()) {
         return false;
      }

      if (!json.getAsJsonPrimitive().isString()) {
         return false;
      }

      String string = json.getAsString();
      if (string == null) {
         return false;
      }

      this.setValue(string);
      return true;
   }

   @Override
   public JsonElement serializeValue() {
      return new JsonPrimitive(this.getValue());
   }

   public boolean isNameVisible() {
      return this.isNameVisible;
   }

   public StringSetting setNameVisible(boolean isNameVisible) {
      this.isNameVisible = isNameVisible;
      return this;
   }

   public StringSetting setDescription(String description) {
      return (StringSetting)super.setDescription(description);
   }

   public StringSetting setVisibility(BooleanSupplier tester) {
      return (StringSetting)super.setVisibility(tester);
   }

   public StringSetting onChange(Runnable run) {
      return (StringSetting)super.onChange(run);
   }

   public StringSetting onChange(Consumer<String> consumer) {
      return (StringSetting)super.onChange(consumer);
   }

   public StringSetting setHidden(boolean hidden) {
      return (StringSetting)super.setHidden(hidden);
   }

   public StringSetting setShouldSerialize(boolean shouldSerialize) {
      return (StringSetting)super.setShouldSerialize(shouldSerialize);
   }
}
