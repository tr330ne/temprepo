package org.rusherhack.client.api.events.player;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.EventCancellable;

public class EventMove extends EventCancellable {
   private double motionX;
   private double motionY;
   private double motionZ;

   public EventMove(Vec3 motion) {
      this.setMotion(motion);
   }

   public double getX() {
      return this.motionX;
   }

   public void setX(double x) {
      this.motionX = x;
   }

   public double getY() {
      return this.motionY;
   }

   public void setY(double y) {
      this.motionY = y;
   }

   public double getZ() {
      return this.motionZ;
   }

   public void setZ(double z) {
      this.motionZ = z;
   }

   public Vec3 getMotion() {
      return new Vec3(this.motionX, this.motionY, this.motionZ);
   }

   public void setMotion(Vec3 motion) {
      this.setMotion(motion.x, motion.y, motion.z);
   }

   public void setMotion(double x, double y, double z) {
      this.motionX = x;
      this.motionY = y;
      this.motionZ = z;
   }

   @Override
   public Stage getPreferredStage() {
      return Stage.PRE;
   }

   public static class FallOnBlock extends EventMove {
      private final Block block;

      public FallOnBlock(Block block, Vec3 motion) {
         super(motion);
         this.block = block;
      }

      public Block getBlock() {
         return this.block;
      }
   }

   public static class Mount extends EventMove {
      private final Entity entity;

      public Mount(Entity entity, Vec3 motion) {
         super(motion);
         this.entity = entity;
      }

      public Entity getEntity() {
         return this.entity;
      }
   }
}
