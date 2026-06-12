package org.rusherhack.core.notification.type;

import org.rusherhack.core.interfaces.ITickable;
import org.rusherhack.core.notification.NotificationType;
import org.rusherhack.core.utils.Timer;

public abstract class LivingNotification extends Notification implements ITickable {
   private final Timer timer;
   private final long lifeSpan;
   private boolean shouldKill = false;

   public LivingNotification(String text, NotificationType type) {
      super(text, type);
      this.timer = new Timer();
      this.lifeSpan = type.getLifeSpan();
   }

   public LivingNotification(String text, NotificationType type, int id) {
      super(text, type, id);
      this.timer = new Timer();
      this.lifeSpan = type.getLifeSpan();
   }

   public Timer getTimer() {
      return this.timer;
   }

   public long getLifeSpan() {
      return this.lifeSpan;
   }

   public boolean shouldKillSelf() {
      return this.timer.passed(this.lifeSpan) || this.shouldKill;
   }

   public void kill() {
      this.shouldKill = true;
   }
}
