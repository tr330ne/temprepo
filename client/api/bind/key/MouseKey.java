package org.rusherhack.client.api.bind.key;

import org.lwjgl.glfw.GLFW;

public class MouseKey extends GLFWKey {
   public MouseKey(int keyCode) {
      super(keyCode, -1);
   }

   @Override
   public boolean isKeyDown() {
      return GLFW.glfwGetMouseButton(GLFW.glfwGetCurrentContext(), this.getKeyCode()) == 1;
   }

   @Override
   public String getLabel(boolean includePrefix) {
      return GLFWConstants.getMouseKeyString(this.getKeyCode(), includePrefix);
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof MouseKey key) ? false : key.getKeyCode() == this.getKeyCode() && key.getScanCode() == this.getScanCode();
   }

   @Override
   public int hashCode() {
      return this.getLabel(true).hashCode() + this.getKeyCode() + this.getScanCode();
   }

   @Override
   public String getDisplayLabel() {
      return this.getLabel(true).replace("_", "");
   }
}
