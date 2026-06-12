package org.rusherhack.client.api.utils.registry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

public class EntityTypeRegistry {
   public static final Map<String, EntityType<?>> ENTITY_TYPES = new HashMap<>();
   public static final HashMap<String[], EntityType<?>[]> WILD_CARDS = new HashMap<>();

   public static EntityType<?>[] getEntityTypes(String query) {
      query = query.toLowerCase();
      if (query.startsWith("minecraft:")) {
         query = query.substring("minecraft:".length());
      }

      if (ENTITY_TYPES.containsKey(query)) {
         return new EntityType[]{ENTITY_TYPES.get(query)};
      }

      for (Entry<String[], EntityType<?>[]> entry : WILD_CARDS.entrySet()) {
         for (String alias : entry.getKey()) {
            if (query.equalsIgnoreCase(alias)) {
               return entry.getValue();
            }
         }
      }

      return new EntityType[0];
   }

   public static EntityType<?>[] findEntityTypes(String query) {
      query = query.toLowerCase();
      if (query.startsWith("minecraft:")) {
         query = query.substring("minecraft:".length());
      }

      HashSet<EntityType<?>> entityTypes = new HashSet<>();

      for (Entry<String, EntityType<?>> entry : ENTITY_TYPES.entrySet()) {
         String key = entry.getKey();
         if (key.contains(query)) {
            entityTypes.add(entry.getValue());
         }
      }

      for (Entry<String[], EntityType<?>[]> entry : WILD_CARDS.entrySet()) {
         for (String alias : entry.getKey()) {
            if (query.contains(alias)) {
               entityTypes.addAll(Arrays.asList(entry.getValue()));
            }
         }
      }

      return entityTypes.toArray(new EntityType[0]);
   }

   public static String getID(EntityType<?> entityType) {
      return BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getPath();
   }

   static {
      for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
         ENTITY_TYPES.put(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getPath(), entityType);
      }
   }
}
