package org.rusherhack.client.api.ui.panel;

import org.rusherhack.client.api.render.IRenderable2D;
import org.rusherhack.core.interfaces.IClickable;
import org.rusherhack.core.interfaces.ITypeable;

public interface IPanelItem extends IRenderable2D, IClickable, ITypeable {
   double getWidth();

   double getHeight(boolean var1);

   boolean isHovered(double var1, double var3, boolean var5);

   @Override
   default boolean isHovered(double mouseX, double mouseY) {
      return this.isHovered(mouseX, mouseY, true);
   }

   default boolean isVisible() {
      return true;
   }
}
