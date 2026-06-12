package org.rusherhack.core.bind.key;

public class NullKey implements IKey {
   public static final NullKey INSTANCE = new NullKey();

   @Override
   public boolean isKeyDown() {
      return false;
   }

   @Override
   public String getLabel(boolean includePrefix) {
      return "NONE";
   }
}
