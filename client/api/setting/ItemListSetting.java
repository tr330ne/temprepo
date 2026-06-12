package org.rusherhack.client.api.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.world.item.Item;
import org.rusherhack.client.api.feature.command.arg.ItemReference;
import org.rusherhack.client.api.utils.registry.ItemRegistry;
import org.rusherhack.core.command.AbstractCommand;
import org.rusherhack.core.command.annotations.CommandExecutor;
import org.rusherhack.core.setting.ListSetting;

public class ItemListSetting extends ListSetting<Item> {
   public ItemListSetting(String name, Item... defaultItems) {
      super(name, "", new HashSet<>(List.of(defaultItems)));
   }

   public ItemListSetting(String name, String description, Item... defaultItems) {
      super(name, description, new HashSet<>(List.of(defaultItems)));
   }

   public ItemListSetting(String name, String description, boolean toggleable, Item... defaultItems) {
      super(name, description, toggleable, new HashSet<>(List.of(defaultItems)));
   }

   public JsonElement serializeElement(Item item) {
      return new JsonPrimitive(ItemRegistry.getID(item));
   }

   public Item deserializeElement(JsonElement element) {
      return element instanceof JsonPrimitive primitive && primitive.isString() ? this.parseElement(primitive.getAsString()) : null;
   }

   public Item parseElement(String string) {
      Item[] items = ItemRegistry.getItems(string);
      return items.length == 0 ? null : items[0];
   }

   public String getElementDisplayName(Item item) {
      return Component.translatable(item.getDescriptionId()).getString();
   }

   public String[] getElementAttributes(Item element) {
      return element == null ? new String[]{"Item"} : new String[]{this.getElementDisplayName(element)};
   }

   @Override
   public AbstractCommand createCommand(AbstractCommand parent) {
      return super.createCommand(parent);
   }

   @Override
   public Collection<Item> getPossibleElements() {
      return ItemRegistry.ITEMS.values();
   }

   @Override
   protected AbstractCommand createAddCommand(AbstractCommand parent) {
      return new AbstractCommand(parent, "add", "Adds an item to the list") {
         @CommandExecutor
         @CommandExecutor.Argument("item")
         public String add(ItemReference reference) {
            String itemName = reference.description();
            ItemListSetting.this.addAll(reference.items());
            return String.format("Added item %s to the %s list", itemName, ItemListSetting.this.getDisplayString());
         }
      };
   }

   @Override
   protected AbstractCommand createRemoveCommand(AbstractCommand parent) {
      AbstractCommand removeCommand = new AbstractCommand(parent, "remove", "Removes an item from the list") {
         @CommandExecutor
         @CommandExecutor.Argument("item")
         public String remove(ItemReference reference) {
            String itemName = reference.description();
            boolean removed = ItemListSetting.this.removeAll(reference.items());
            return removed
               ? String.format("Removed item %s from the %s list", itemName, ItemListSetting.this.getDisplayString())
               : String.format("%s was not found in the %s list", itemName, ItemListSetting.this.getDisplayString());
         }
      };
      removeCommand.addAliases("rm", "delete", "del");
      return removeCommand;
   }

   @Override
   protected AbstractCommand createListCommand(AbstractCommand parent) {
      return new AbstractCommand(parent, "list", "List the items in the list") {
         @CommandExecutor
         private Component listElements() {
            MutableComponent delimiter = Component.literal(", ");
            ArrayList<Item> items = new ArrayList<>(ItemListSetting.this.getList());
            items.sort(Comparator.comparing(ItemListSetting.this::getElementDisplayName));
            MutableComponent itemsComponent = Component.literal(String.format("%s {%s}: ", ItemListSetting.this.getDisplayString(), items.size()));

            for (int i = 0; i < items.size(); i++) {
               Item item = items.get(i);
               if (i != 0) {
                  itemsComponent.append(delimiter);
               }

               HoverEvent hoverEvent = new HoverEvent(Action.SHOW_TEXT, Component.literal(item.getDescriptionId()));
               Style style = Style.EMPTY.withHoverEvent(hoverEvent).withColor(ChatFormatting.GREEN);
               MutableComponent itemComponent = Component.literal(ItemListSetting.this.getElementDisplayName(item)).withStyle(style);
               itemsComponent.append(itemComponent);
            }

            return itemsComponent;
         }
      };
   }
}
