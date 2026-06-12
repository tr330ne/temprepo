package org.rusherhack.client.api.events.client;

import org.rusherhack.core.event.type.Event;

public class EventTimerSpeed extends Event {
   private boolean overrideTimer = false;
   private float speed;

   public EventTimerSpeed(float speed) {
      this.speed = speed;
   }

   public float getSpeed() {
      return this.speed;
   }

   public void setSpeed(float speed) {
      this.speed = speed;
   }

   public boolean shouldOverrideTimer() {
      return this.overrideTimer;
   }

   public void setOverrideTimer(boolean overrideTimer) {
      this.overrideTimer = overrideTimer;
   }
}
