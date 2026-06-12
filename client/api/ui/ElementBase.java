package org.rusherhack.client.api.ui;

public abstract class ElementBase {
   private double x;
   private double y;

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public abstract double getWidth();

   public abstract double getHeight();

   public void setX(double x) {
      this.x = x;
   }

   public void setY(double y) {
      this.y = y;
   }
}
