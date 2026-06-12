package org.rusherhack.client.api.events.render;

import com.mojang.blaze3d.vertex.PoseStack;
import org.rusherhack.core.event.type.Event;

public abstract class EventRender extends Event {
   private final PoseStack matrixStack;
   private final float partialTicks;

   public EventRender(PoseStack poseStack, float partialTicks) {
      this.matrixStack = poseStack;
      this.partialTicks = partialTicks;
   }

   public PoseStack getMatrixStack() {
      return this.matrixStack;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }
}
