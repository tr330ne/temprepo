package org.rusherhack.client.api.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

public record RenderContext(GuiGraphics graphics, float partialTicks) {
   public PoseStack pose() {
      return this.graphics.pose();
   }
}
