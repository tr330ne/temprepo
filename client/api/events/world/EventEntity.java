package org.rusherhack.client.api.events.world;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.EventCancellable;

public abstract class EventEntity extends EventCancellable {
   private final Entity entity;

   public EventEntity(Entity entity) {
      this.entity = entity;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public static class Add extends EventEntity {
      public Add(Entity entity) {
         super(entity);
      }

      @Override
      public Stage getStage() {
         return super.getStage();
      }

      @Override
      public Stage getPreferredStage() {
         return Stage.POST;
      }
   }

   public static class Remove extends EventEntity {
      private final RemovalReason reason;

      public Remove(Entity entity, RemovalReason reason) {
         super(entity);
         this.reason = reason;
      }

      public RemovalReason getReason() {
         return this.reason;
      }

      @Override
      public Stage getStage() {
         return super.getStage();
      }

      @Override
      public Stage getPreferredStage() {
         return Stage.PRE;
      }
   }
}
