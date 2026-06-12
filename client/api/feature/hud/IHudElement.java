package org.rusherhack.client.api.feature.hud;

import org.rusherhack.client.api.render.IRenderable2D;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.core.feature.IFeatureConfigurable;
import org.rusherhack.core.interfaces.IDraggable;
import org.rusherhack.core.interfaces.ITickable;
import org.rusherhack.core.interfaces.IToggleable;
import org.rusherhack.core.serialize.JsonSerializable;

public interface IHudElement extends IFeatureConfigurable, IRenderable2D, IToggleable, ITickable, IDraggable, JsonSerializable {
   @Deprecated(forRemoval = true)
   default void renderContent(RenderContext context, int mouseX, int mouseY) {
      this.renderContent(context, (double)mouseX, (double)mouseY);
   }

   default void renderContent(RenderContext context, double mouseX, double mouseY) {
      this.renderContent(context, (int)mouseX, (int)mouseY);
   }

   default boolean shouldDrawBackground() {
      return true;
   }
}
