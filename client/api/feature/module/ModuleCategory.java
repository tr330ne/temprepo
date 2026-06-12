package org.rusherhack.client.api.feature.module;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.core.interfaces.INamed;
import org.rusherhack.core.interfaces.IReferenceable;

public class ModuleCategory implements INamed, IReferenceable {
   private static final List<ModuleCategory> CATEGORY_REGISTRY = new ArrayList<>();
   public static final ModuleCategory CHAT = new ModuleCategory("Chat");
   public static final ModuleCategory COMBAT = new ModuleCategory("Combat");
   public static final ModuleCategory MISC = new ModuleCategory("Miscellaneous");
   public static final ModuleCategory MOVEMENT = new ModuleCategory("Movement");
   public static final ModuleCategory PLAYER = new ModuleCategory("Player");
   public static final ModuleCategory RENDER = new ModuleCategory("Render");
   public static final ModuleCategory WORLD = new ModuleCategory("World");
   public static final ModuleCategory CLIENT = new ModuleCategory("Client");
   public static final ModuleCategory EXTERNAL = new ModuleCategory("External");
   private final String name;
   private String displayName;

   ModuleCategory(String name) {
      this.name = this.displayName = name;
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String getDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }

   public boolean isDefaultCategory() {
      return this == CHAT || this == COMBAT || this == MISC || this == MOVEMENT || this == PLAYER || this == RENDER || this == WORLD || this == CLIENT;
   }

   public static ModuleCategory getOrRegister(String name) {
      ModuleCategory category = getCategoryByName(name);
      return category != null ? category : register(name);
   }

   public static ModuleCategory register(String name) {
      ModuleCategory category = new ModuleCategory(name);
      register(category);
      return category;
   }

   private static void register(ModuleCategory category) {
      RusherHackAPI.getRegistry().register(category);
      CATEGORY_REGISTRY.add(category);
   }

   public static ModuleCategory getCategoryByName(String name) {
      for (ModuleCategory category : getCategories()) {
         for (String alias : category.getAliases()) {
            if (alias.equalsIgnoreCase(name)) {
               return category;
            }
         }
      }

      return null;
   }

   public static List<ModuleCategory> getCategories() {
      return ImmutableList.copyOf(CATEGORY_REGISTRY);
   }

   @Deprecated
   public static ModuleCategory[] values() {
      return CATEGORY_REGISTRY.toArray(new ModuleCategory[0]);
   }

   public static void initializeCategories() {
      CATEGORY_REGISTRY.clear();
      register(CHAT);
      register(COMBAT);
      register(MISC);
      register(MOVEMENT);
      register(PLAYER);
      register(RENDER);
      register(WORLD);
      register(CLIENT);
      if (RusherHackAPI.PLUGINS_ENABLED) {
         CATEGORY_REGISTRY.add(EXTERNAL);
      }
   }

   @Override
   public String getReferenceKey() {
      return String.format("module.category.%s", this.getName().replace(" ", "_").toLowerCase());
   }
}
