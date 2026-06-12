package org.rusherhack.client.api.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.core.bind.key.IKey;
import org.rusherhack.core.setting.Setting;

public class BindSetting extends Setting<IKey> {
   public BindSetting(String name, IKey value) {
      super(name, value);
   }

   public BindSetting(String name, String description, IKey value) {
      super(name, description, value);
   }

   public IKey parseValue(String string, boolean set) {
      IKey value = RusherHackAPI.getBindManager().parseKey(string);
      if (set) {
         this.setValue(value);
      }

      return value;
   }

   @Override
   public String getDisplayValue() {
      IKey key = this.getValue();
      return key.getDisplayLabel();
   }

   @Override
   public boolean deserializeValue(JsonElement json) {
      if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
         this.setValue(RusherHackAPI.getBindManager().parseKey(json.getAsJsonPrimitive().getAsString()));
         return true;
      } else {
         return false;
      }
   }

   @Override
   public JsonElement serializeValue() {
      return new JsonPrimitive(this.getValue().getLabel(true));
   }

   public BindSetting setDescription(String description) {
      return (BindSetting)super.setDescription(description);
   }

   public BindSetting setVisibility(BooleanSupplier tester) {
      return (BindSetting)super.setVisibility(tester);
   }

   public BindSetting onChange(Runnable run) {
      return (BindSetting)super.onChange(run);
   }

   public BindSetting onChange(Consumer<IKey> consumer) {
      return (BindSetting)super.onChange(consumer);
   }

   public BindSetting setHidden(boolean hidden) {
      return (BindSetting)super.setHidden(hidden);
   }

   public BindSetting setShouldSerialize(boolean shouldSerialize) {
      return (BindSetting)super.setShouldSerialize(shouldSerialize);
   }
}
