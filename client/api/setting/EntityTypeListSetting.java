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
import net.minecraft.world.entity.EntityType;
import org.rusherhack.client.api.utils.registry.EntityTypeRegistry;
import org.rusherhack.core.command.AbstractCommand;
import org.rusherhack.core.command.annotations.CommandExecutor;
import org.rusherhack.core.setting.ListSetting;

public class EntityTypeListSetting extends ListSetting<EntityType<?>> {
   public EntityTypeListSetting(String name, EntityType<?>... defaultItems) {
      super(name, "", new HashSet<>(List.of(defaultItems)));
   }

   public EntityTypeListSetting(String name, String description, EntityType<?>... defaultItems) {
      super(name, description, new HashSet<>(List.of(defaultItems)));
   }

   public EntityTypeListSetting(String name, String description, boolean toggleable, EntityType<?>... defaultItems) {
      super(name, description, toggleable, new HashSet<>(List.of(defaultItems)));
   }

   public JsonElement serializeElement(EntityType<?> entityType) {
      return new JsonPrimitive(EntityTypeRegistry.getID(entityType));
   }

   public EntityType<?> deserializeElement(JsonElement element) {
      return element instanceof JsonPrimitive primitive && primitive.isString() ? this.parseElement(primitive.getAsString()) : null;
   }

   public EntityType<?> parseElement(String string) {
      EntityType<?>[] entityTypes = EntityTypeRegistry.getEntityTypes(string);
      return entityTypes.length == 0 ? null : entityTypes[0];
   }

   public String getElementDisplayName(EntityType<?> entityType) {
      return Component.translatable(entityType.getDescriptionId()).getString();
   }

   public String[] getElementAttributes(EntityType<?> element) {
      return element == null ? new String[]{"Entity Type"} : new String[]{this.getElementDisplayName(element)};
   }

   @Override
   public AbstractCommand createCommand(AbstractCommand parent) {
      return super.createCommand(parent);
   }

   @Override
   public Collection<EntityType<?>> getPossibleElements() {
      return EntityTypeRegistry.ENTITY_TYPES.values();
   }

   @Override
   protected AbstractCommand createAddCommand(AbstractCommand parent) {
      return new AbstractCommand(parent, "add", "Adds an entity type to the list") {
         @CommandExecutor
         @CommandExecutor.Argument("entity type")
         public String add(EntityType<?> entityType) {
            EntityTypeListSetting.this.add(entityType);
            String name = EntityTypeListSetting.this.getElementDisplayName(entityType);
            return String.format("Added entity type %s to the %s list", name, EntityTypeListSetting.this.getDisplayString());
         }
      };
   }

   @Override
   protected AbstractCommand createRemoveCommand(AbstractCommand parent) {
      AbstractCommand removeCommand = new AbstractCommand(parent, "remove", "Removes an entity type from the list") {
         @CommandExecutor
         @CommandExecutor.Argument("entity type")
         public String remove(EntityType<?> entityType) {
            String name = EntityTypeListSetting.this.getElementDisplayName(entityType);
            return EntityTypeListSetting.this.remove(entityType)
               ? String.format("Removed entity type %s from the %s list", name, EntityTypeListSetting.this.getDisplayString())
               : String.format("%s was not found in the %s list", name, EntityTypeListSetting.this.getDisplayString());
         }
      };
      removeCommand.addAliases("rm", "delete", "del");
      return removeCommand;
   }

   @Override
   protected AbstractCommand createListCommand(AbstractCommand parent) {
      return new AbstractCommand(parent, "list", "List the entity types in the list") {
         @CommandExecutor
         private Component listElements() {
            MutableComponent delimiter = Component.literal(", ");
            ArrayList<EntityType<?>> entityTypes = new ArrayList<>(EntityTypeListSetting.this.getList());
            entityTypes.sort(Comparator.comparing(EntityTypeListSetting.this::getElementDisplayName));
            MutableComponent component = Component.literal(String.format("%s {%s}: ", EntityTypeListSetting.this.getDisplayString(), entityTypes.size()));

            for (int i = 0; i < entityTypes.size(); i++) {
               EntityType<?> item = entityTypes.get(i);
               if (i != 0) {
                  component.append(delimiter);
               }

               HoverEvent hoverEvent = new HoverEvent(Action.SHOW_TEXT, Component.literal(item.getDescriptionId()));
               Style style = Style.EMPTY.withHoverEvent(hoverEvent).withColor(ChatFormatting.GREEN);
               MutableComponent entityTypeComponent = Component.literal(EntityTypeListSetting.this.getElementDisplayName(item)).withStyle(style);
               component.append(entityTypeComponent);
            }

            return component;
         }
      };
   }
}
