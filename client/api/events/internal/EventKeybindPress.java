package org.rusherhack.client.api.events.internal;

import org.rusherhack.client.api.bind.key.GLFWKey;
import org.rusherhack.client.api.bind.key.KeyboardKey;
import org.rusherhack.client.api.bind.key.MouseKey;
import org.rusherhack.client.api.events.client.input.EventKeyboard;
import org.rusherhack.client.api.events.client.input.EventMouse;
import org.rusherhack.core.bind.key.IKey;
import org.rusherhack.core.event.type.Event;

public class EventKeybindPress extends Event {
   private final EventKeybindPress.Type type;
   private final int keyCode;

   public EventKeybindPress(EventKeyboard event) {
      this(EventKeybindPress.Type.KEYBOARD, event.getKey());
   }

   public EventKeybindPress(EventMouse.Key event) {
      this(EventKeybindPress.Type.MOUSE, event.getButton());
   }

   public EventKeybindPress(EventKeybindPress.Type type, int keyCode) {
      this.type = type;
      this.keyCode = keyCode;
   }

   public EventKeybindPress.Type getType() {
      return this.type;
   }

   public int getKeyCode() {
      return this.keyCode;
   }

   public boolean doesMatch(IKey key) {
      if (!(key instanceof GLFWKey)) {
         return false;
      } else if (key instanceof KeyboardKey) {
         return this.type == EventKeybindPress.Type.KEYBOARD && this.keyCode == ((KeyboardKey)key).getKeyCode();
      } else {
         return !(key instanceof MouseKey) ? false : this.type == EventKeybindPress.Type.MOUSE && this.keyCode == ((GLFWKey)key).getKeyCode();
      }
   }

   public enum Type {
      KEYBOARD,
      MOUSE;
   }
}
