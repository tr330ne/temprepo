package org.rusherhack.core.bind.key;

public interface IKey {
   boolean isKeyDown();

   String getLabel(boolean var1);

   default String getDisplayLabel() {
      return this.getLabel(true);
   }
}
