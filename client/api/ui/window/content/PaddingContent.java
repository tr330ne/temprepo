package org.rusherhack.client.api.ui.window.content;

import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.ui.window.view.WindowView;

public class PaddingContent extends WindowContent {
   private final double width;
   private final double height;

   public PaddingContent(Window window, double width, double height) {
      super(window);
      this.width = width;
      this.height = height;
   }

   @Override
   public void renderContent(double mouseX, double mouseY, WindowView parent) {
   }

   @Override
   public double getWidth() {
      return this.width;
   }

   @Override
   public double getHeight() {
      return this.height;
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      return false;
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
      return false;
   }

   @Override
   public boolean charTyped(char character) {
      return false;
   }

   @Override
   public boolean keyTyped(int key, int scanCode, int modifiers) {
      return false;
   }
}
