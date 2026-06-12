package org.rusherhack.core.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.text.DecimalFormat;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import org.rusherhack.core.utils.MathUtils;

public class NumberSetting<T extends Number> extends Setting<T> {
   public static final DecimalFormat DISPLAY_FORMAT = new DecimalFormat("0.#####");
   private final T minimum;
   private final T maximum;
   private boolean clampMinimum;
   private boolean clampMaximum;
   private boolean incrementing = false;
   private double incrementStep = 1.0;

   public NumberSetting(String name, T value, T minimum, T maximum) {
      this(name, "", value, minimum, maximum);
   }

   public NumberSetting(String name, String description, T value, T minimum, T maximum) {
      super(name, description, value);
      this.minimum = minimum;
      this.maximum = maximum;
      this.clampMinimum = this.clampMaximum = false;
   }

   public T parseValue(String string, boolean set) {
      try {
         T value = this.convertValue(Double.parseDouble(string));
         if (set) {
            this.setValue(value);
         }

         return value;
      } catch (Throwable e) {
         return null;
      }
   }

   @Override
   public String getDisplayValue() {
      return DISPLAY_FORMAT.format(this.getValue());
   }

   @Override
   public void setValueObj(Object value) {
      if (value instanceof Number number) {
         this.setValue(number.doubleValue());
      }
   }

   public void setValue(double d) {
      this.setValue(d, false, false);
   }

   public void setValue(double d, boolean clamp, boolean round) {
      if (clamp) {
         if (this.clampMaximum && d > this.maximum.doubleValue()) {
            d = this.maximum.doubleValue();
         } else if (this.clampMinimum && d < this.minimum.doubleValue()) {
            d = this.minimum.doubleValue();
         }
      }

      if (round && this.incrementing) {
         d = MathUtils.round(d, this.incrementStep);
      }

      T value = this.convertValue(d);
      if (value != null) {
         this.setValue(value);
      }
   }

   @Override
   public JsonElement serializeValue() {
      return new JsonPrimitive(this.getValue());
   }

   @Override
   public boolean deserializeValue(JsonElement json) {
      if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber()) {
         JsonPrimitive primitive = json.getAsJsonPrimitive();
         Number number = primitive.getAsNumber();
         T value = this.convertValue(number.doubleValue());
         if (value == null) {
            return false;
         }

         this.setValue(value);
         return true;
      } else {
         return false;
      }
   }

   public T getMinimum() {
      return this.minimum;
   }

   public T getMaximum() {
      return this.maximum;
   }

   public NumberSetting<T> clampMin() {
      this.clampMinimum = true;
      return this;
   }

   public NumberSetting<T> clampMax() {
      this.clampMaximum = true;
      return this;
   }

   public NumberSetting<T> incremental(double incrementStep) {
      this.incrementing = true;
      this.incrementStep = incrementStep;
      return this;
   }

   private T convertValue(double value) {
      if (this.getValue() instanceof Double) {
         return (T)value;
      } else if (this.getValue() instanceof Float) {
         return (T)(float)value;
      } else if (this.getValue() instanceof Long) {
         return (T)(long)value;
      } else if (this.getValue() instanceof Integer) {
         return (T)(int)value;
      } else if (this.getValue() instanceof Short) {
         return (T)(short)value;
      } else {
         return (T)(this.getValue() instanceof Byte ? (byte)value : null);
      }
   }

   public NumberSetting<T> setDescription(String description) {
      return (NumberSetting<T>)super.setDescription(description);
   }

   public NumberSetting<T> setVisibility(BooleanSupplier tester) {
      return (NumberSetting<T>)super.setVisibility(tester);
   }

   public NumberSetting<T> onChange(Runnable run) {
      return (NumberSetting<T>)super.onChange(run);
   }

   public NumberSetting<T> onChange(Consumer<T> consumer) {
      return (NumberSetting<T>)super.onChange(consumer);
   }

   public NumberSetting<T> setHidden(boolean hidden) {
      return (NumberSetting<T>)super.setHidden(hidden);
   }

   public NumberSetting<T> setShouldSerialize(boolean shouldSerialize) {
      return (NumberSetting<T>)super.setShouldSerialize(shouldSerialize);
   }
}
