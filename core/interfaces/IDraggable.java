package org.rusherhack.core.interfaces;

public interface IDraggable extends IClickable {
   void mouseMoved(double var1, double var3);

   default boolean isDragging() {
      return false;
   }
}
