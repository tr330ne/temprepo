package org.rusherhack.client.api.utils.objects;

import org.rusherhack.core.animation.Animation;
import org.rusherhack.core.utils.MathUtils;

public class Scrollbar {
   private final Animation animation;
   private double scrollOffset = 0.0;
   private double targetScrollOffset = 0.0;

   public Scrollbar(Animation animation) {
      this.animation = animation;
   }

   public void update() {
      this.scrollOffset = MathUtils.interpolate(this.targetScrollOffset, this.scrollOffset, this.animation.get());
   }

   public void clamp(boolean strict, double min, double max) {
      double clamped = MathUtils.clamp(this.targetScrollOffset, min, max);
      if (strict) {
         this.targetScrollOffset = clamped;
         this.scrollOffset = MathUtils.clamp(this.scrollOffset, min, max);
      } else {
         this.targetScrollOffset = MathUtils.interpolate(clamped, this.targetScrollOffset, 0.15);
      }
   }

   public void setScrollOffset(double scrollOffset) {
      this.targetScrollOffset = scrollOffset;
   }

   public void scroll(double delta) {
      this.scroll(delta, true);
   }

   public void scroll(double delta, boolean animated) {
      if (animated) {
         this.targetScrollOffset += delta;
         this.animation.restart().play();
      } else {
         this.scrollOffset += delta;
         this.targetScrollOffset = this.scrollOffset;
      }
   }

   public double getScrollOffset() {
      return this.scrollOffset;
   }

   public double getRenderHeight(double viewLength, double contentLength) {
      return this.getRenderHeight(viewLength, contentLength, viewLength);
   }

   public double getRenderHeight(double viewLength, double contentLength, double trackLength) {
      double contentRatio = viewLength / contentLength;
      return trackLength * contentRatio;
   }

   public double getRenderOffset(double viewLength, double contentLength) {
      return this.getRenderOffset(viewLength, contentLength, viewLength);
   }

   public double getRenderOffset(double viewLength, double contentLength, double trackLength) {
      double contentRatio = viewLength / contentLength;
      double contentOffset = this.scrollOffset * contentRatio;
      double trackRatio = trackLength / viewLength;
      return contentOffset * trackRatio;
   }
}
