package org.rusherhack.client.api.system;

import java.util.List;
import net.minecraft.world.entity.Entity;
import org.rusherhack.client.api.utils.objects.PlayerRelation;

public interface IRelationManager {
   boolean isFriend(String var1);

   default boolean isFriend(Entity entity) {
      return this.isFriend(entity.getName().getString());
   }

   boolean isEnemy(String var1);

   default boolean isEnemy(Entity entity) {
      return this.isEnemy(entity.getName().getString());
   }

   void addFriend(String var1);

   default void addFriend(Entity entity) {
      this.addFriend(entity.getName().getString());
   }

   void addEnemy(String var1);

   default void addEnemy(Entity entity) {
      this.addEnemy(entity.getName().getString());
   }

   void removeFriend(String var1);

   default void removeFriend(Entity entity) {
      this.removeFriend(entity.getName().getString());
   }

   void removeEnemy(String var1);

   default void removeEnemy(Entity entity) {
      this.removeEnemy(entity.getName().getString());
   }

   List<PlayerRelation> getFriends();

   List<PlayerRelation> getEnemies();
}
