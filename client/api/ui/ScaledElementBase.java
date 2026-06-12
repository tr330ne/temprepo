package org.rusherhack.client.api.ui;

public abstract class ScaledElementBase extends ElementBase {
   public double getScale() {
      return 1.0;
   }

   public double getScaledWidth() {
      return this.getWidth() * this.getScale();
   }

   public double getScaledHeight() {
      return this.getHeight() * this.getScale();
   }
}
