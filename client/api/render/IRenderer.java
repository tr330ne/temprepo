package org.rusherhack.client.api.render;

import com.mojang.blaze3d.vertex.PoseStack;

public interface IRenderer {
   default void begin() {
      this.begin(new PoseStack());
   }

   void begin(PoseStack var1);

   void end();

   boolean isBuilding();

   PoseStack getMatrixStack();
}
