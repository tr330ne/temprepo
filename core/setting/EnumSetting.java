package org.rusherhack.core.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import org.rusherhack.core.command.AbstractCommand;
import org.rusherhack.core.command.annotations.CommandExecutor;
import org.rusherhack.core.utils.StringUtils;

public class EnumSetting<T extends Enum<?>> extends Setting<T> {
   public EnumSetting(String name, T value) {
      super(name, value);
   }

   public EnumSetting(String name, String description, T value) {
      super(name, description, value);
   }

   public T parseValue(String string, boolean set) {
      for (Enum<?> enumConstant : (Enum[])this.getValue().getClass().getEnumConstants()) {
         String name = enumConstant.name();
         if (name.equalsIgnoreCase(string) || StringUtils.toTitleCase(name).equalsIgnoreCase(string)) {
            T e = (T)enumConstant;
            if (set) {
               this.setValue(e);
            }

            return e;
         }
      }

      return null;
   }

   public void increment() {
      Enum<?>[] enums = (Enum<?>[])this.getValue().getClass().getEnumConstants();
      int nextIndex = this.getValue().ordinal() + 1;
      if (nextIndex > enums.length - 1) {
         this.setValue((T)enums[0]);
      } else {
         this.setValue((T)enums[nextIndex]);
      }
   }

   public void decrement() {
      Enum<?>[] enums = (Enum<?>[])this.getValue().getClass().getEnumConstants();
      int nextIndex = this.getValue().ordinal() - 1;
      if (nextIndex < 0) {
         this.setValue((T)enums[enums.length - 1]);
      } else {
         this.setValue((T)enums[nextIndex]);
      }
   }

   public String[] getOptions(boolean displayName) {
      Enum<?>[] enums = (Enum<?>[])this.getValue().getClass().getEnumConstants();
      String[] values = new String[enums.length];

      for (int i = 0; i < enums.length; i++) {
         values[i] = displayName ? StringUtils.toTitleCase(enums[i].name()) : enums[i].name();
      }

      return values;
   }

   @Override
   public String getDisplayValue() {
      return StringUtils.toTitleCase(this.getValue().name());
   }

   @Override
   public AbstractCommand createCommand(AbstractCommand parent) {
      return new Setting.SettingCommand<EnumSetting<T>>(parent, this) {
         @CommandExecutor
         @CommandExecutor.Argument("value")
         private String setValue(Optional<Setting.ValueHolder> value) {
            if (value.isPresent()) {
               this.setting.setValueObj(value.get().value());
            } else {
               this.setting.increment();
            }

            return String.format("Set %s to %s", this.setting.getName(), this.setting.getDisplayValue());
         }
      };
   }

   @Override
   public boolean deserializeValue(JsonElement json) {
      return json.isJsonPrimitive() && json.getAsJsonPrimitive().isString() ? this.parseValue(json.getAsString(), true) != null : false;
   }

   @Override
   public JsonElement serializeValue() {
      return new JsonPrimitive(this.getValue().name());
   }

   public EnumSetting<T> setDescription(String description) {
      return (EnumSetting<T>)super.setDescription(description);
   }

   public EnumSetting<T> setVisibility(BooleanSupplier tester) {
      return (EnumSetting<T>)super.setVisibility(tester);
   }

   public EnumSetting<T> onChange(Runnable run) {
      return (EnumSetting<T>)super.onChange(run);
   }

   public EnumSetting<T> onChange(Consumer<T> consumer) {
      return (EnumSetting<T>)super.onChange(consumer);
   }

   public EnumSetting<T> setHidden(boolean hidden) {
      return (EnumSetting<T>)super.setHidden(hidden);
   }

   public EnumSetting<T> setShouldSerialize(boolean shouldSerialize) {
      return (EnumSetting<T>)super.setShouldSerialize(shouldSerialize);
   }
}
