package org.rusherhack.client.api.events.client.input;

import org.rusherhack.client.api.utils.InputUtils;
import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.EventCancellable;

public abstract class EventMouse extends EventCancellable {
   private final double xPos;
   private final double yPos;

   public EventMouse(double xPos, double yPos) {
      this.xPos = xPos;
      this.yPos = yPos;
   }

   public double getMouseX() {
      return this.xPos;
   }

   public double getMouseY() {
      return this.yPos;
   }

   public static class Key extends EventMouse {
      private final int button;
      private final int action;
      private final int modifiers;

      public Key(int button, int action, int modifiers) {
         super(InputUtils.getMouseX(), InputUtils.getMouseY());
         this.button = button;
         this.action = action;
         this.modifiers = modifiers;
      }

      public int getButton() {
         return this.button;
      }

      public int getAction() {
         return this.action;
      }

      public int getModifiers() {
         return this.modifiers;
      }

      @Override
      public Stage getStage() {
         return super.getStage();
      }
   }

   public static class Move extends EventMouse {
      public Move(double xPos, double yPos) {
         super(xPos, yPos);
      }
   }

   public static class Scroll extends EventMouse {
      private final double xOffset;
      private final double yOffset;

      public Scroll(double xOffset, double yOffset) {
         super(InputUtils.getMouseX(), InputUtils.getMouseY());
         this.xOffset = xOffset;
         this.yOffset = yOffset;
      }

      public double getScrollDeltaX() {
         return this.xOffset;
      }

      public double getScrollDeltaY() {
         return this.yOffset;
      }
   }
}
