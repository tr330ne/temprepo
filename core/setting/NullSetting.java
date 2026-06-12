package org.rusherhack.core.setting;

import com.google.gson.JsonElement;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import org.rusherhack.core.command.AbstractCommand;

public class NullSetting extends Setting<Object> {
   public NullSetting(String name) {
      super(name, null);
   }

   public NullSetting(String name, String description) {
      super(name, description, null);
   }

   @Override
   public void setValue(Object value) {
   }

   @Override
   public Object parseValue(String string, boolean set) {
      return null;
   }

   @Override
   public String getDisplayValue() {
      return "null";
   }

   @Override
   public boolean deserializeValue(JsonElement json) {
      return false;
   }

   @Override
   public JsonElement serializeValue() {
      return null;
   }

   @Override
   public AbstractCommand createCommand(AbstractCommand parent) {
      return null;
   }

   public NullSetting setDescription(String description) {
      return (NullSetting)super.setDescription(description);
   }

   public NullSetting setVisibility(BooleanSupplier tester) {
      return (NullSetting)super.setVisibility(tester);
   }

   public NullSetting onChange(Runnable run) {
      return (NullSetting)super.onChange(run);
   }

   public NullSetting onChange(Consumer<Object> consumer) {
      return (NullSetting)super.onChange(consumer);
   }

   public NullSetting setHidden(boolean hidden) {
      return (NullSetting)super.setHidden(hidden);
   }

   public NullSetting setShouldSerialize(boolean shouldSerialize) {
      return (NullSetting)super.setShouldSerialize(shouldSerialize);
   }
}
