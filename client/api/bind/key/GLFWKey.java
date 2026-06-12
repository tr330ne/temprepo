package org.rusherhack.client.api.bind.key;

import org.rusherhack.core.bind.key.IKey;

public abstract class GLFWKey implements IKey {
   private final int keyCode;
   private final int scanCode;

   public GLFWKey(int keyCode, int scanCode) {
      this.keyCode = keyCode;
      this.scanCode = scanCode;
   }

   public GLFWKey(int scanCode) {
      this(-1, scanCode);
   }

   public int getKeyCode() {
      return this.keyCode;
   }

   public int getScanCode() {
      return this.scanCode;
   }
}
