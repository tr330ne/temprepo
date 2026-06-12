package org.rusherhack.core.setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public abstract class OptionSetting<T> extends Setting<T> {
   protected final ArrayList<T> options = new ArrayList<>();

   public OptionSetting(String name, T value, T... options) {
      this(name, "", value, options);
   }

   public OptionSetting(String name, String description, T value, T... options) {
      super(name, description, value);
      if (value != null) {
         this.options.add(value);
      }

      this.options.addAll(Arrays.asList(options));
   }

   public OptionSetting<T> addOption(T option) {
      if (this.options.contains(option)) {
         return this;
      }

      this.options.add(option);
      return this;
   }

   public Collection<T> getOptions() {
      return this.options;
   }

   public void sort(Comparator<T> comparator) {
      this.options.sort(comparator);
   }

   public OptionSetting<T> setDescription(String description) {
      return (OptionSetting<T>)super.setDescription(description);
   }

   public OptionSetting<T> setVisibility(BooleanSupplier tester) {
      return (OptionSetting<T>)super.setVisibility(tester);
   }

   public OptionSetting<T> onChange(Runnable run) {
      return (OptionSetting<T>)super.onChange(run);
   }

   public OptionSetting<T> onChange(Consumer<T> consumer) {
      return (OptionSetting<T>)super.onChange(consumer);
   }

   public OptionSetting<T> setHidden(boolean hidden) {
      return (OptionSetting<T>)super.setHidden(hidden);
   }

   public OptionSetting<T> setShouldSerialize(boolean shouldSerialize) {
      return (OptionSetting<T>)super.setShouldSerialize(shouldSerialize);
   }
}
