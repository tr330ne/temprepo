package org.rusherhack.core.setting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import org.rusherhack.core.command.AbstractCommand;
import org.rusherhack.core.command.annotations.CommandExecutor;

public abstract class ListSetting<T> extends Setting<ListSetting.ListHolder<T>> implements Iterable<T> {
   private final boolean toggleable;

   public ListSetting(String name, T... defaultItems) {
      this(name, "", new ArrayList<>(List.of(defaultItems)));
   }

   public ListSetting(String name, String description, T... defaultItems) {
      this(name, description, new ArrayList<>(List.of(defaultItems)));
   }

   public ListSetting(String name, String description, boolean toggleable, T... defaultItems) {
      this(name, description, toggleable, new ArrayList<>(List.of(defaultItems)));
   }

   public ListSetting(String name, String description, Collection<T> collection) {
      this(name, description, false, collection);
   }

   public ListSetting(String name, String description, boolean toggleable, Collection<T> collection) {
      super(name, description, new ListSetting.ListHolder<>(new AtomicBoolean(true), collection));
      this.toggleable = toggleable;
      this.defaultValue = new ListSetting.ListHolder<>(new AtomicBoolean(true), List.copyOf(collection));
   }

   public abstract JsonElement serializeElement(T var1);

   public abstract T deserializeElement(JsonElement var1);

   public abstract T parseElement(String var1);

   public abstract String getElementDisplayName(T var1);

   public ListSetting.ListHolder<T> parseValue(String string, boolean set) {
      if (string.startsWith("[") && string.endsWith("]")) {
         string = string.substring(1, string.length() - 1);
      }

      ArrayList<T> result = new ArrayList<>();

      for (String item : string.split(",")) {
         T element = this.parseElement(item);
         if (element != null) {
            result.add(element);
         }
      }

      if (set) {
         for (T element : result) {
            this.add(element);
         }
      }

      return new ListSetting.ListHolder<>(this.getValue().toggled, result);
   }

   @Override
   public String getDisplayValue() {
      return this.getValue().toggled.toString();
   }

   public boolean add(T element) {
      boolean added = this.getList().add(element);
      if (added && this.consumer != null) {
         this.consumer.accept(this.getValue());
      }

      return added;
   }

   public boolean addAll(T... elements) {
      boolean added = this.getList().addAll(Arrays.asList(elements));
      if (added && this.consumer != null) {
         this.consumer.accept(this.getValue());
      }

      return added;
   }

   public boolean remove(T element) {
      boolean removed = this.getList().remove(element);
      if (removed && this.consumer != null) {
         this.consumer.accept(this.getValue());
      }

      return removed;
   }

   public boolean removeAll(T... elements) {
      boolean removed = this.getList().removeAll(Arrays.asList(elements));
      if (removed && this.consumer != null) {
         this.consumer.accept(this.getValue());
      }

      return removed;
   }

   public boolean contains(T element) {
      return this.getList().contains(element);
   }

   public void clear() {
      this.getList().clear();
      if (this.consumer != null) {
         this.consumer.accept(this.getValue());
      }
   }

   public boolean isToggleable() {
      return this.toggleable;
   }

   public boolean isToggled() {
      return !this.toggleable || this.getValue().toggled.get();
   }

   public void setToggled(boolean toggled) {
      this.getValue().toggled.set(toggled);
   }

   public Collection<T> getPossibleElements() {
      return null;
   }

   public Collection<T> getList() {
      return this.getValue().list;
   }

   public String getDisplayString() {
      return this.attachedFeature != null ? this.attachedFeature.getDisplayName() + " " + this.getDisplayName() : this.getDisplayName();
   }

   public String[] getElementAttributes(T element) {
      return element == null ? new String[]{"Name"} : new String[]{this.getElementDisplayName(element)};
   }

   @Override
   public AbstractCommand createCommand(AbstractCommand parent) {
      Setting.SettingCommand<ListSetting<T>> command;
      if (this.toggleable) {
         command = new Setting.SettingCommand<ListSetting<T>>(parent, this) {
            @CommandExecutor
            @CommandExecutor.Argument("true/false")
            private String toggle(Optional<Boolean> value) {
               ListSetting.this.setToggled(!ListSetting.this.isToggled());
               return String.format("Set %s to %s", this.setting.getName(), this.setting.getDisplayValue());
            }
         };
      } else {
         command = new Setting.SettingCommand<ListSetting<T>>(parent, this) {};
      }

      AbstractCommand addCommand = this.createAddCommand(command);
      if (addCommand != null) {
         command.registerSubCommand(addCommand);
      }

      AbstractCommand removeCommand = this.createRemoveCommand(command);
      if (removeCommand != null) {
         command.registerSubCommand(removeCommand);
      }

      AbstractCommand listCommand = this.createListCommand(command);
      if (listCommand != null) {
         command.registerSubCommand(listCommand);
      }

      AbstractCommand clearCommand = this.createClearCommand(command);
      if (clearCommand != null) {
         command.registerSubCommand(clearCommand);
      }

      return command;
   }

   protected AbstractCommand createAddCommand(AbstractCommand parent) {
      return new AbstractCommand(parent, "add", "Adds to the list") {
         @CommandExecutor
         @CommandExecutor.Argument("element")
         public String add(T element) {
            ListSetting.this.add(element);
            return String.format("Added %s to the %s list", ListSetting.this.getElementDisplayName(element), ListSetting.this.getDisplayString());
         }
      };
   }

   protected AbstractCommand createRemoveCommand(AbstractCommand parent) {
      AbstractCommand removeCommand = new AbstractCommand(parent, "remove", "Removes from the list") {
         @CommandExecutor
         @CommandExecutor.Argument("element")
         public String remove(T element) {
            String name = ListSetting.this.getElementDisplayName(element);
            return ListSetting.this.remove(element)
               ? String.format("Removed %s from the %s list", name, ListSetting.this.getDisplayString())
               : String.format("%s was not found in the %s list", name, ListSetting.this.getDisplayString());
         }
      };
      removeCommand.addAliases("rm", "delete", "del");
      return removeCommand;
   }

   protected AbstractCommand createListCommand(AbstractCommand parent) {
      return new AbstractCommand(parent, "list", "Lists the elements of the list") {
         @CommandExecutor
         private String listElements() {
            StringJoiner joiner = new StringJoiner(", ");
            ArrayList<T> elements = new ArrayList<>(ListSetting.this.getList());
            elements.sort(Comparator.comparing(ListSetting.this::getElementDisplayName));
            StringBuilder result = new StringBuilder(String.format("%s {%s}: ", ListSetting.this.getDisplayString(), elements.size()));

            for (T element : elements) {
               joiner.add(ListSetting.this.getElementDisplayName(element));
            }

            return result.append(joiner).toString();
         }
      };
   }

   protected AbstractCommand createClearCommand(AbstractCommand parent) {
      return new AbstractCommand(parent, "clear", "Clears the list") {
         @CommandExecutor
         private String clear() {
            ListSetting.this.getList().clear();
            return "Cleared search blocks list";
         }
      };
   }

   @Override
   public boolean deserializeValue(JsonElement json) {
      if (json instanceof JsonPrimitive primitive && primitive.isBoolean()) {
         this.setToggled(primitive.getAsBoolean());
      } else {
         if (!(json instanceof JsonObject obj)) {
            return false;
         }

         if (obj.has("toggled")) {
            boolean toggled = obj.get("toggled").getAsBoolean();
            this.setToggled(toggled);
         }

         if (obj.has("list")) {
            this.clear();

            for (JsonElement jsonElement : obj.getAsJsonArray("list")) {
               T deserialized = this.deserializeElement(jsonElement);
               if (deserialized != null) {
                  this.add(deserialized);
               }
            }
         }
      }

      return true;
   }

   @Override
   public JsonElement serializeValue() {
      JsonObject object = new JsonObject();
      object.addProperty("toggled", this.getValue().toggled.get());
      JsonArray array = new JsonArray();

      for (T item : this.getList()) {
         JsonElement serializedElement = this.serializeElement(item);
         if (serializedElement != null) {
            array.add(serializedElement);
         }
      }

      object.add("list", array);
      return object;
   }

   @Override
   public void reset(boolean includeSubSettings) {
      this.setToggled(this.getDefaultValue().toggled.get());
      this.getList().clear();
      this.getList().addAll(this.getDefaultValue().list());
      if (includeSubSettings) {
         for (Setting<?> setting : this.subSettings) {
            setting.reset(true);
         }
      }
   }

   @Override
   public Iterator<T> iterator() {
      return this.getList().iterator();
   }

   public ListSetting<T> setDescription(String description) {
      return (ListSetting<T>)super.setDescription(description);
   }

   public ListSetting<T> setVisibility(BooleanSupplier tester) {
      return (ListSetting<T>)super.setVisibility(tester);
   }

   public ListSetting<T> setHidden(boolean hidden) {
      return (ListSetting<T>)super.setHidden(hidden);
   }

   public ListSetting<T> onChange(Runnable run) {
      return (ListSetting<T>)super.onChange(run);
   }

   public ListSetting<T> onChange(Consumer<ListSetting.ListHolder<T>> consumer) {
      return (ListSetting<T>)super.onChange(consumer);
   }

   public ListSetting<T> setShouldSerialize(boolean shouldSerialize) {
      return (ListSetting<T>)super.setShouldSerialize(shouldSerialize);
   }

   public record ListHolder<T>(AtomicBoolean toggled, Collection<T> list) {
   }
}
