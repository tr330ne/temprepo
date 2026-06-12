package org.rusherhack.core.utils;

public class Timer {
   private long time;

   public Timer() {
      this(System.currentTimeMillis());
   }

   public Timer(long startTime) {
      this.time = startTime;
   }

   public void reset() {
      this.time = System.currentTimeMillis();
   }

   public boolean passed(double ms) {
      return System.currentTimeMillis() - this.time >= ms;
   }

   public boolean ticksPassed(int gameTicks) {
      return System.currentTimeMillis() - this.time >= gameTicks * 50L;
   }

   public long getTime() {
      return System.currentTimeMillis() - this.time;
   }

   public int getTicksPassed() {
      return (int)((System.currentTimeMillis() - this.time) / 50L);
   }

   public void setTime(long time) {
      this.time = time;
   }
}
