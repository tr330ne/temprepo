package org.rusherhack.client.api.events.render;

import net.minecraft.client.gui.GuiGraphics;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.core.event.stage.Stage;

public class EventRender2D extends EventRender {
   private final RenderContext renderContext;

   public EventRender2D(GuiGraphics graphics, float partialTicks) {
      super(graphics.pose(), partialTicks);
      this.renderContext = new RenderContext(graphics, partialTicks);
   }

   public RenderContext getRenderContext() {
      return this.renderContext;
   }

   public GuiGraphics getGraphics() {
      return this.renderContext.graphics();
   }

   @Override
   public Stage getStage() {
      return super.getStage();
   }
}
