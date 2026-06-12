package org.rusherhack.client.api.utils;

import org.rusherhack.client.api.Globals;

public class InputUtils implements Globals {
   public static double getMouseX() {
      return mc.mouseHandler.xpos() * mc.getWindow().getGuiScaledWidth() / mc.getWindow().getScreenWidth();
   }

   public static double getMouseY() {
      return mc.mouseHandler.ypos() * mc.getWindow().getGuiScaledHeight() / mc.getWindow().getScreenHeight();
   }
}
