package org.rusherhack.core.interfaces;

public interface IClickable extends IHoverable {
   boolean mouseClicked(double var1, double var3, int var5);

   default void mouseReleased(double mouseX, double mouseY, int button) {
   }
}
