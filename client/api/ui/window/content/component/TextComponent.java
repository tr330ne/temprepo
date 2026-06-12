package org.rusherhack.client.api.ui.window.content.component;

import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.ui.window.content.WindowContent;
import org.rusherhack.client.api.ui.window.view.WindowView;

public class TextComponent extends WindowContent {
   protected String text = "";

   public TextComponent(Window window) {
      super(window);
   }

   public TextComponent(Window window, String text) {
      this(window, text, false);
   }

   public TextComponent(Window window, String text, boolean wrap) {
      super(window);
      this.text = text;
   }

   @Override
   public void renderContent(double mouseX, double mouseY, WindowView parent) {
      this.getFontRenderer().drawString(this.getText(), this.x, this.y, -1);
   }

   @Override
   public double getWidth() {
      return this.getFontRenderer().getStringWidth(this.getText());
   }

   @Override
   public double getHeight() {
      return this.getFontRenderer().getFontHeight();
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      return false;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      this.text = text;
   }

   @Override
   public boolean charTyped(char character) {
      return false;
   }

   @Override
   public boolean keyTyped(int key, int scanCode, int modifiers) {
      return false;
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
      return false;
   }
}
