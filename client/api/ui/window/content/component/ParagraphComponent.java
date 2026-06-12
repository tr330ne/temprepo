package org.rusherhack.client.api.ui.window.content.component;

import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.ui.window.view.WindowView;

public class ParagraphComponent extends TextComponent {
   protected double lineSpacing = 1.0;
   protected WindowView parent;
   protected double heightCache = 9.0;
   protected int color = -1;

   public ParagraphComponent(Window window) {
      super(window);
   }

   public ParagraphComponent(Window window, String text) {
      this(window, text, 1.0);
   }

   public ParagraphComponent(Window window, String text, double lineSpacing) {
      super(window, text);
      this.lineSpacing = lineSpacing;
   }

   @Override
   public void renderContent(double mouseX, double mouseY, WindowView parent) {
      this.parent = parent;
      this.heightCache = this.getFontRenderer().drawText(this.getText(), this.x, this.y, this.color, this.getWidth(), this.lineSpacing);
   }

   @Override
   public double getWidth() {
      return this.parent != null ? this.parent.getViewWidth() : this.getWindow().getRootView().getViewWidth();
   }

   @Override
   public double getHeight() {
      return this.heightCache;
   }

   public void updateHeight() {
      this.heightCache = this.getFontRenderer().splitString(this.getText(), this.getWidth()).size() * this.getFontRenderer().getFontHeight() * this.lineSpacing;
   }

   public void setLineSpacing(double lineSpacing) {
      this.lineSpacing = lineSpacing;
   }

   public void setColor(int color) {
      this.color = color;
   }
}
