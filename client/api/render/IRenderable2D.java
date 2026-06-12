package org.rusherhack.client.api.render;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.render.font.IFontRenderer;

public interface IRenderable2D {
   default void render(RenderContext context) {
      this.render(context, -1.0, -1.0);
   }

   void render(RenderContext var1, double var2, double var4);

   default IRenderer2D getRenderer() {
      return RusherHackAPI.getRenderer2D();
   }

   default IFontRenderer getFontRenderer() {
      return RusherHackAPI.fonts().getFontRenderer();
   }
}
