package org.rusherhack.client.api.events.client.input;

import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.EventCancellable;

public class EventKeyboard extends EventCancellable {
   private final int key;
   private final int scanCode;
   private final int action;
   private final int modifiers;

   public EventKeyboard(int key, int scanCode, int action, int modifiers) {
      this.key = key;
      this.scanCode = scanCode;
      this.action = action;
      this.modifiers = modifiers;
   }

   @Override
   public Stage getStage() {
      return super.getStage();
   }

   public int getKey() {
      return this.key;
   }

   public int getScanCode() {
      return this.scanCode;
   }

   public int getAction() {
      return this.action;
   }

   public int getModifiers() {
      return this.modifiers;
   }
}
