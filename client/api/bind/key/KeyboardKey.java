package org.rusherhack.client.api.bind.key;

import org.lwjgl.glfw.GLFW;

public class KeyboardKey extends GLFWKey {
   public KeyboardKey(int keyCode) {
      super(keyCode, GLFW.glfwGetKeyScancode(keyCode));
   }

   @Override
   public boolean isKeyDown() {
      return GLFW.glfwGetKey(GLFW.glfwGetCurrentContext(), this.getKeyCode()) == 1;
   }

   @Override
   public String getLabel(boolean includePrefix) {
      return GLFWConstants.getKeyboardKeyString(this.getKeyCode(), includePrefix);
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof KeyboardKey key) ? false : key.getKeyCode() == this.getKeyCode() && key.getScanCode() == this.getScanCode();
   }

   @Override
   public int hashCode() {
      return this.getLabel(true).hashCode() + this.getKeyCode() + this.getScanCode();
   }

   @Override
   public String getDisplayLabel() {
      return this.getLabel(false).replace("_", "");
   }
}
