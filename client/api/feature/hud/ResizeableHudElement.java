package org.rusherhack.client.api.feature.hud;

import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.core.setting.NumberSetting;

public abstract class ResizeableHudElement extends HudElement {
   private boolean resizing = false;
   private double resizeStartScale = 1.0;
   private double resizeDeltaX = 0.0;
   private double resizeDeltaY = 0.0;
   protected final NumberSetting<Double> scale = new NumberSetting<>("Scale", "Size of this hud element", 1.0, 0.25, 4.0).clampMin().incremental(0.01);

   public ResizeableHudElement(String name) {
      super(name);
      this.registerSettings(this.scale);
   }

   @Override
   public void mouseMoved(double mouseX, double mouseY) {
      if (this.resizing) {
         double wantedWidth = mouseX - this.getStartX() - this.resizeDeltaX;
         double wantedHeight = mouseY - this.getStartY() - this.resizeDeltaY;
         double scaleDelta = Math.min(wantedWidth / this.getWidth(), wantedHeight / this.getHeight());
         double newScale = Math.max(this.resizeStartScale + scaleDelta, 0.25);
         this.scale.setValue(newScale, true, true);
      }

      super.mouseMoved(mouseX, mouseY);
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.isHoveredOverIndicator(mouseX, mouseY)) {
         this.resizeStartScale = this.scale.getValue();
         this.resizeDeltaX = mouseX - this.getStartX();
         this.resizeDeltaY = mouseY - this.getStartY();
         this.resizing = true;
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   @Override
   public void mouseReleased(double mouseX, double mouseY, int button) {
      if (this.resizing && button == 0) {
         this.resizing = false;
      }

      super.mouseReleased(mouseX, mouseY, button);
   }

   @Override
   public double getScale() {
      return super.getScale() * this.scale.getValue();
   }

   @Override
   public void render(RenderContext context, double mouseX, double mouseY) {
      if (this.isResizing() && !this.getAlignment().equals(HudElement.Alignment.TOP_LEFT)) {
         this.correctPosition(this.getAlignment(), HudElement.Alignment.TOP_LEFT);
         this.alignment = HudElement.Alignment.TOP_LEFT;
      }

      super.render(context, mouseX, mouseY);
   }

   @Override
   public boolean shouldUpdateAlignment() {
      return super.shouldUpdateAlignment() && !this.isResizing();
   }

   public boolean isHoveredOverIndicator(double mouseX, double mouseY) {
      double cornerX = this.getStartX() + this.getScaledWidth() - 6.0;
      double cornerY = this.getStartY() + this.getScaledHeight() - 6.0;
      return mouseX >= cornerX && mouseX <= cornerX + 6.0 && mouseY >= cornerY && mouseY <= cornerY + 6.0;
   }

   public boolean isResizing() {
      return this.resizing;
   }
}
