package org.rusherhack.client.api.events.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.Event;
import org.rusherhack.core.event.type.EventCancellable;

public class EventRenderEntity extends EventCancellable {
   private final Entity entity;
   private final double x;
   private final double y;
   private final double z;
   private final float rotationYaw;
   private final float partialTicks;
   private final PoseStack matrixStack;
   private MultiBufferSource buffer;
   private final int packedLight;
   private boolean shouldRenderModel = true;

   public EventRenderEntity(
      Entity entity, double x, double y, double z, float rotationYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight
   ) {
      this.entity = entity;
      this.x = x;
      this.y = y;
      this.z = z;
      this.rotationYaw = rotationYaw;
      this.partialTicks = partialTicks;
      this.matrixStack = matrixStack;
      this.buffer = buffer;
      this.packedLight = packedLight;
   }

   @Override
   public Stage getPreferredStage() {
      return Stage.PRE;
   }

   @Override
   public Stage getStage() {
      return super.getStage();
   }

   public Entity getEntity() {
      return this.entity;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public float getRotationYaw() {
      return this.rotationYaw;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public PoseStack getMatrixStack() {
      return this.matrixStack;
   }

   public MultiBufferSource getBuffer() {
      return this.buffer;
   }

   public void setBufferSource(MultiBufferSource buffer) {
      this.buffer = buffer;
   }

   public void setRenderModel(boolean shouldRenderModel) {
      this.shouldRenderModel = shouldRenderModel;
   }

   public boolean shouldRenderModel() {
      return this.shouldRenderModel;
   }

   public int getPackedLight() {
      return this.packedLight;
   }

   public static class Armor extends EventCancellable {
   }

   public static class ItemFrameParts extends EventCancellable {
      private final EventRenderEntity.ItemFrameParts.Part part;

      public ItemFrameParts(EventRenderEntity.ItemFrameParts.Part part) {
         this.part = part;
      }

      public EventRenderEntity.ItemFrameParts.Part getPart() {
         return this.part;
      }

      public enum Part {
         ENTITY,
         ITEM;
      }
   }

   public static class Nametag extends EventCancellable {
      private final Entity entity;
      private Component displayName;
      private final PoseStack matrixStack;
      private final MultiBufferSource buffer;
      private final int packedLight;

      public Nametag(Entity entity, Component displayName, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
         this.entity = entity;
         this.displayName = displayName;
         this.matrixStack = matrixStack;
         this.buffer = buffer;
         this.packedLight = packedLight;
      }

      @Override
      public Stage getStage() {
         return super.getStage();
      }

      @Override
      public Stage getPreferredStage() {
         return Stage.PRE;
      }

      public Entity getEntity() {
         return this.entity;
      }

      public Component getDisplayName() {
         return this.displayName;
      }

      public void setDisplayName(Component displayName) {
         this.displayName = displayName;
      }

      public PoseStack getMatrixStack() {
         return this.matrixStack;
      }

      public MultiBufferSource getBuffer() {
         return this.buffer;
      }

      public int getPackedLight() {
         return this.packedLight;
      }

      public static class Check extends Event {
         private final Entity entity;
         private boolean shouldShowName;

         public Check(Entity entity, boolean shouldShowName) {
            this.entity = entity;
            this.shouldShowName = shouldShowName;
         }

         public Entity getEntity() {
            return this.entity;
         }

         public boolean shouldShowName() {
            return this.shouldShowName;
         }

         public void setShouldShowName(boolean shouldShowName) {
            this.shouldShowName = shouldShowName;
         }
      }
   }

   public static class PlayerRotations extends Event {
      private float yaw;
      private float pitch;

      public PlayerRotations(float yaw, float pitch) {
         this.yaw = yaw;
         this.pitch = pitch;
      }

      public float getYaw() {
         return this.yaw;
      }

      public void setYaw(float yaw) {
         this.yaw = yaw;
      }

      public float getPitch() {
         return this.pitch;
      }

      public void setPitch(float pitch) {
         this.pitch = pitch;
      }
   }
}
