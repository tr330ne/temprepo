package org.rusherhack.client.api.events.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.EventCancellable;

public class EventRenderBlockEntity extends EventCancellable {
   private final BlockEntity blockEntity;
   private final float partialTicks;
   private final PoseStack poseStack;
   private MultiBufferSource bufferSource;

   public EventRenderBlockEntity(BlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource) {
      this.blockEntity = blockEntity;
      this.partialTicks = partialTicks;
      this.poseStack = poseStack;
      this.bufferSource = bufferSource;
   }

   @Override
   public Stage getPreferredStage() {
      return Stage.PRE;
   }

   @Override
   public Stage getStage() {
      return super.getStage();
   }

   public BlockEntity getBlockEntity() {
      return this.blockEntity;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public PoseStack getPoseStack() {
      return this.poseStack;
   }

   public MultiBufferSource getBufferSource() {
      return this.bufferSource;
   }

   public void setBufferSource(MultiBufferSource bufferSource) {
      this.bufferSource = bufferSource;
   }
}
