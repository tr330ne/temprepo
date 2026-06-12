package org.rusherhack.core.animation;

import org.rusherhack.core.utils.MathUtils;

public class Animation {
   private Easing tween = Easing.LINEAR;
   private boolean playing = false;
   private boolean reverse = false;
   private boolean loop = false;
   private long duration = 0L;
   private long lastTime = 0L;
   private long timePassed = 0L;
   private double progress = 0.0;

   public Animation() {
   }

   public Animation(Easing tween, long durationMs) {
      this.tween = tween;
      this.duration = durationMs * 1000000L;
   }

   public double get() {
      if (this.playing) {
         long time = System.nanoTime() - this.lastTime;
         if (this.reverse) {
            time *= -1L;
         }

         this.timePassed += time;
         if (this.timePassed >= this.duration || this.timePassed <= 0L) {
            if (this.loop) {
               this.timePassed = 0L;
            } else {
               this.timePassed = MathUtils.clamp(this.timePassed, 0L, this.duration);
               this.playing = false;
            }
         }

         double p = (double)this.timePassed / this.duration;
         this.progress = this.tween.apply(p);
         this.lastTime = System.nanoTime();
      }

      return this.progress;
   }

   public Animation play() {
      this.playing = true;
      this.lastTime = System.nanoTime();
      return this;
   }

   public Animation restart() {
      this.lastTime = System.nanoTime();
      this.timePassed = 0L;
      return this;
   }

   public Animation reverse() {
      this.reverse = !this.reverse;
      return this;
   }

   public Animation setLoop(boolean loop) {
      this.loop = loop;
      return this;
   }

   public Animation setDirection(boolean reverse) {
      this.reverse = reverse;
      return this;
   }

   public Animation setDuration(long durationMs) {
      this.duration = durationMs * 1000000L;
      return this;
   }

   public Animation setTween(Easing tween) {
      this.tween = tween;
      return this;
   }

   public boolean isPlaying() {
      return this.playing;
   }

   public boolean isReversed() {
      return this.reverse;
   }
}
