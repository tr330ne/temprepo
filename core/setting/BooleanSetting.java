package org.rusherhack.core.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import org.rusherhack.core.command.AbstractCommand;
import org.rusherhack.core.command.annotations.CommandExecutor;

public class BooleanSetting extends Setting<Boolean> {
   public static final String[] TRUE_ALIASES = new String[]{"true", "on", "enable", "enabled", "1", "yes"};
   public static final String[] FALSE_ALIASES = new String[]{"false", "off", "disable", "disabled", "0", "no"};

   public BooleanSetting(String name, Boolean value) {
      super(name, value);
   }

   public BooleanSetting(String name, String description, Boolean value) {
      super(name, description, value);
   }

   @Override
   public boolean deserializeValue(JsonElement json) {
      if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isBoolean()) {
         this.setValue(json.getAsBoolean());
         return true;
      } else {
         return false;
      }
   }

   @Override
   public JsonElement serializeValue() {
      return new JsonPrimitive(this.getValue());
   }

   public Boolean parseValue(String string, boolean set) {
      for (String alias : TRUE_ALIASES) {
         if (alias.equalsIgnoreCase(string)) {
            if (set) {
               this.setValue(true);
            }

            return true;
         }
      }

      for (String alias : FALSE_ALIASES) {
         if (alias.equalsIgnoreCase(string)) {
            if (set) {
               this.setValue(false);
            }

            return false;
         }
      }

      return null;
   }

   @Override
   public String getDisplayValue() {
      return this.getValue().toString();
   }

   @Override
   public AbstractCommand createCommand(AbstractCommand parent) {
      return new Setting.SettingCommand<BooleanSetting>(parent, this) {
         @CommandExecutor
         @CommandExecutor.Argument("true/false")
         private String setValue(Optional<Boolean> value) {
            this.setting.setValue(value.orElse(!this.setting.getValue()));
            return String.format("Set %s to %s", this.setting.getName(), this.setting.getDisplayValue());
         }
      };
   }

   public BooleanSetting setDescription(String description) {
      return (BooleanSetting)super.setDescription(description);
   }

   public BooleanSetting setVisibility(BooleanSupplier tester) {
      return (BooleanSetting)super.setVisibility(tester);
   }

   public BooleanSetting onChange(Runnable run) {
      return (BooleanSetting)super.onChange(run);
   }

   public BooleanSetting onChange(Consumer<Boolean> consumer) {
      return (BooleanSetting)super.onChange(consumer);
   }

   public BooleanSetting setHidden(boolean hidden) {
      return (BooleanSetting)super.setHidden(hidden);
   }

   public BooleanSetting setShouldSerialize(boolean shouldSerialize) {
      return (BooleanSetting)super.setShouldSerialize(shouldSerialize);
   }
}
