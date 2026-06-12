package org.rusherhack.client.api.utils.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ItemRegistry {
   public static final Map<String, Item> ITEMS = new HashMap<>();
   public static final HashMap<String[], Item[]> WILD_CARDS = new HashMap<>();

   public static Item[] getItems(String query) {
      query = query.toLowerCase();
      if (query.startsWith("minecraft:")) {
         query = query.substring("minecraft:".length());
      }

      if (ITEMS.containsKey(query)) {
         return new Item[]{ITEMS.get(query)};
      }

      for (Entry<String[], Item[]> entry : WILD_CARDS.entrySet()) {
         for (String alias : entry.getKey()) {
            if (query.equalsIgnoreCase(alias)) {
               return entry.getValue();
            }
         }
      }

      return new Item[0];
   }

   public static Item[] findItems(String query) {
      query = query.toLowerCase();
      if (query.startsWith("minecraft:")) {
         query = query.substring("minecraft:".length());
      }

      HashSet<Item> items = new HashSet<>();

      for (Entry<String, Item> entry : ITEMS.entrySet()) {
         String key = entry.getKey();
         if (key.contains(query)) {
            items.add(entry.getValue());
         }
      }

      for (Entry<String[], Item[]> entry : WILD_CARDS.entrySet()) {
         for (String alias : entry.getKey()) {
            if (query.contains(alias)) {
               items.addAll(Arrays.asList(entry.getValue()));
            }
         }
      }

      return items.toArray(new Item[0]);
   }

   public static String getID(Item item) {
      return BuiltInRegistries.ITEM.getKey(item).getPath();
   }

   static {
      for (Item item : BuiltInRegistries.ITEM) {
         ITEMS.put(BuiltInRegistries.ITEM.getKey(item).getPath(), item);
      }

      BlockRegistry.WILD_CARDS.forEach((wildcard, blocks) -> {
         List<Item> items = new ArrayList<>();

         for (Block block : blocks) {
            items.add(block.asItem());
         }

         WILD_CARDS.put(wildcard, items.toArray(new Item[0]));
      });
   }
}
