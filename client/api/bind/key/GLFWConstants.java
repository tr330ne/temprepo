package org.rusherhack.client.api.bind.key;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.rusherhack.core.bind.key.IKey;
import org.rusherhack.core.bind.key.NullKey;

public class GLFWConstants {
   public static final Int2ObjectMap<String[]> KEYBOARD_MAP = new Int2ObjectOpenHashMap();
   public static final Int2ObjectMap<String[]> MOUSE_MAP = new Int2ObjectOpenHashMap();
   public static final Int2ObjectMap<String[]> GAMEPAD_MAP = new Int2ObjectOpenHashMap();
   public static final String KEYBOARD_PREFIX = "KEY";
   public static final String MOUSE_PREFIX = "MOUSE";
   public static final String GAMEPAD_PREFIX = "GAMEPAD";

   public static IKey fromLabel(String label) {
      label = label.replace("_", "").toUpperCase();
      if (label.startsWith("MOUSE")) {
         label = label.substring("MOUSE".length());
         int mouseKey = getMouseKey(label);
         if (mouseKey != -1) {
            return new MouseKey(mouseKey);
         }
      } else {
         if (label.startsWith("KEY")) {
            label = label.substring("KEY".length());
         }

         int keyCode = getKeyboardKey(label);
         if (keyCode != -1) {
            return new KeyboardKey(keyCode);
         }

         try {
            keyCode = Integer.parseInt(label);
            return new KeyboardKey(keyCode);
         } catch (NumberFormatException var3) {
         }
      }

      return NullKey.INSTANCE;
   }

   public static int getKeyboardKey(String key) {
      key = key.replace("_", "");
      ObjectIterator var1 = KEYBOARD_MAP.int2ObjectEntrySet().iterator();

      while (var1.hasNext()) {
         Entry<String[]> entry = (Entry<String[]>)var1.next();

         for (String s : (String[])entry.getValue()) {
            s = s.replace("_", "");
            if (s.equalsIgnoreCase(key)) {
               return entry.getIntKey();
            }
         }
      }

      return -1;
   }

   public static String getKeyboardKeyString(int key, boolean prefix) {
      String[] array = (String[])KEYBOARD_MAP.get(key);
      if (array == null) {
         return getKeyboardKeyString(-1, prefix);
      }

      String name = array[0];
      return prefix ? "KEY_" + name : name;
   }

   public static int getMouseKey(String key) {
      key = key.replace("_", "");
      ObjectIterator var1 = MOUSE_MAP.int2ObjectEntrySet().iterator();

      while (var1.hasNext()) {
         Entry<String[]> entry = (Entry<String[]>)var1.next();

         for (String s : (String[])entry.getValue()) {
            s = s.replace("_", "");
            if (s.equalsIgnoreCase(key)) {
               return entry.getIntKey();
            }
         }
      }

      return -1;
   }

   public static String getMouseKeyString(int key, boolean prefix) {
      String name = ((String[])MOUSE_MAP.get(key))[0];
      return prefix ? "MOUSE_" + name : name;
   }

   static {
      KEYBOARD_MAP.put(32, new String[]{"SPACE"});
      KEYBOARD_MAP.put(39, new String[]{"APOSTROPHE", "QUOTE", "\"", "'"});
      KEYBOARD_MAP.put(44, new String[]{"COMMA", ",", "<"});
      KEYBOARD_MAP.put(45, new String[]{"MINUS", "-"});
      KEYBOARD_MAP.put(46, new String[]{"PERIOD", ".", ">"});
      KEYBOARD_MAP.put(47, new String[]{"SLASH", "/", "?"});
      KEYBOARD_MAP.put(48, new String[]{"0", ")"});
      KEYBOARD_MAP.put(49, new String[]{"1", "!"});
      KEYBOARD_MAP.put(50, new String[]{"2", "@"});
      KEYBOARD_MAP.put(51, new String[]{"3", "#"});
      KEYBOARD_MAP.put(52, new String[]{"4", "$"});
      KEYBOARD_MAP.put(53, new String[]{"5", "%"});
      KEYBOARD_MAP.put(54, new String[]{"6", "^"});
      KEYBOARD_MAP.put(55, new String[]{"7", "&"});
      KEYBOARD_MAP.put(56, new String[]{"8", "*"});
      KEYBOARD_MAP.put(57, new String[]{"9", "("});
      KEYBOARD_MAP.put(59, new String[]{"SEMICOLON", "COLON", ";", ":"});
      KEYBOARD_MAP.put(61, new String[]{"EQUAL", "=", "+"});
      KEYBOARD_MAP.put(65, new String[]{"A"});
      KEYBOARD_MAP.put(66, new String[]{"B"});
      KEYBOARD_MAP.put(67, new String[]{"C"});
      KEYBOARD_MAP.put(68, new String[]{"D"});
      KEYBOARD_MAP.put(69, new String[]{"E"});
      KEYBOARD_MAP.put(70, new String[]{"F"});
      KEYBOARD_MAP.put(71, new String[]{"G"});
      KEYBOARD_MAP.put(72, new String[]{"H"});
      KEYBOARD_MAP.put(73, new String[]{"I"});
      KEYBOARD_MAP.put(74, new String[]{"J"});
      KEYBOARD_MAP.put(75, new String[]{"K"});
      KEYBOARD_MAP.put(76, new String[]{"L"});
      KEYBOARD_MAP.put(77, new String[]{"M"});
      KEYBOARD_MAP.put(78, new String[]{"N"});
      KEYBOARD_MAP.put(79, new String[]{"O"});
      KEYBOARD_MAP.put(80, new String[]{"P"});
      KEYBOARD_MAP.put(81, new String[]{"Q"});
      KEYBOARD_MAP.put(82, new String[]{"R"});
      KEYBOARD_MAP.put(83, new String[]{"S"});
      KEYBOARD_MAP.put(84, new String[]{"T"});
      KEYBOARD_MAP.put(85, new String[]{"U"});
      KEYBOARD_MAP.put(86, new String[]{"V"});
      KEYBOARD_MAP.put(87, new String[]{"W"});
      KEYBOARD_MAP.put(88, new String[]{"X"});
      KEYBOARD_MAP.put(89, new String[]{"Y"});
      KEYBOARD_MAP.put(90, new String[]{"Z"});
      KEYBOARD_MAP.put(91, new String[]{"LEFT_BRACKET", "LBRACKET", "[", "{"});
      KEYBOARD_MAP.put(92, new String[]{"BACKSLASH", "\\", "|"});
      KEYBOARD_MAP.put(93, new String[]{"RIGHT_BRACKET", "RBRACKET", "]", "}"});
      KEYBOARD_MAP.put(96, new String[]{"GRAVE", "`", "~"});
      KEYBOARD_MAP.put(256, new String[]{"ESCAPE", "ESC"});
      KEYBOARD_MAP.put(257, new String[]{"ENTER", "RETURN"});
      KEYBOARD_MAP.put(258, new String[]{"TAB"});
      KEYBOARD_MAP.put(259, new String[]{"BACKSPACE", "BACK"});
      KEYBOARD_MAP.put(260, new String[]{"INSERT"});
      KEYBOARD_MAP.put(261, new String[]{"DELETE", "DEL"});
      KEYBOARD_MAP.put(262, new String[]{"RIGHT"});
      KEYBOARD_MAP.put(263, new String[]{"LEFT"});
      KEYBOARD_MAP.put(264, new String[]{"DOWN"});
      KEYBOARD_MAP.put(265, new String[]{"UP"});
      KEYBOARD_MAP.put(266, new String[]{"PAGE_UP"});
      KEYBOARD_MAP.put(267, new String[]{"PAGE_DOWN"});
      KEYBOARD_MAP.put(268, new String[]{"HOME"});
      KEYBOARD_MAP.put(269, new String[]{"END"});
      KEYBOARD_MAP.put(280, new String[]{"CAPS_LOCK", "CAPS"});
      KEYBOARD_MAP.put(281, new String[]{"SCROLL_LOCK"});
      KEYBOARD_MAP.put(282, new String[]{"NUM_LOCK"});
      KEYBOARD_MAP.put(284, new String[]{"PAUSE"});
      KEYBOARD_MAP.put(290, new String[]{"F1"});
      KEYBOARD_MAP.put(291, new String[]{"F2"});
      KEYBOARD_MAP.put(292, new String[]{"F3"});
      KEYBOARD_MAP.put(293, new String[]{"F4"});
      KEYBOARD_MAP.put(294, new String[]{"F5"});
      KEYBOARD_MAP.put(295, new String[]{"F6"});
      KEYBOARD_MAP.put(296, new String[]{"F7"});
      KEYBOARD_MAP.put(297, new String[]{"F8"});
      KEYBOARD_MAP.put(298, new String[]{"F9"});
      KEYBOARD_MAP.put(299, new String[]{"F10"});
      KEYBOARD_MAP.put(300, new String[]{"F11"});
      KEYBOARD_MAP.put(301, new String[]{"F12"});
      KEYBOARD_MAP.put(302, new String[]{"F13"});
      KEYBOARD_MAP.put(303, new String[]{"F14"});
      KEYBOARD_MAP.put(304, new String[]{"F15"});
      KEYBOARD_MAP.put(305, new String[]{"F16"});
      KEYBOARD_MAP.put(306, new String[]{"F17"});
      KEYBOARD_MAP.put(307, new String[]{"F18"});
      KEYBOARD_MAP.put(308, new String[]{"F19"});
      KEYBOARD_MAP.put(309, new String[]{"F20"});
      KEYBOARD_MAP.put(310, new String[]{"F21"});
      KEYBOARD_MAP.put(311, new String[]{"F22"});
      KEYBOARD_MAP.put(312, new String[]{"F23"});
      KEYBOARD_MAP.put(313, new String[]{"F24"});
      KEYBOARD_MAP.put(314, new String[]{"F25"});
      KEYBOARD_MAP.put(320, new String[]{"KP_0"});
      KEYBOARD_MAP.put(321, new String[]{"KP_1"});
      KEYBOARD_MAP.put(322, new String[]{"KP_2"});
      KEYBOARD_MAP.put(323, new String[]{"KP_3"});
      KEYBOARD_MAP.put(324, new String[]{"KP_4"});
      KEYBOARD_MAP.put(325, new String[]{"KP_5"});
      KEYBOARD_MAP.put(326, new String[]{"KP_6"});
      KEYBOARD_MAP.put(327, new String[]{"KP_7"});
      KEYBOARD_MAP.put(328, new String[]{"KP_8"});
      KEYBOARD_MAP.put(329, new String[]{"KP_9"});
      KEYBOARD_MAP.put(330, new String[]{"KP_DECIMAL"});
      KEYBOARD_MAP.put(331, new String[]{"KP_DIVIDE"});
      KEYBOARD_MAP.put(332, new String[]{"KP_MULTIPLY"});
      KEYBOARD_MAP.put(333, new String[]{"KP_SUBTRACT"});
      KEYBOARD_MAP.put(334, new String[]{"KP_ADD"});
      KEYBOARD_MAP.put(335, new String[]{"KP_ENTER"});
      KEYBOARD_MAP.put(336, new String[]{"KP_EQUAL"});
      KEYBOARD_MAP.put(340, new String[]{"LEFT_SHIFT", "LSHIFT"});
      KEYBOARD_MAP.put(341, new String[]{"LEFT_CONTROL", "LCTRL"});
      KEYBOARD_MAP.put(342, new String[]{"LEFT_ALT", "LALT"});
      KEYBOARD_MAP.put(343, new String[]{"LEFT_SUPER", "WINDOWS", "LWIN"});
      KEYBOARD_MAP.put(344, new String[]{"RIGHT_SHIFT", "RSHIFT"});
      KEYBOARD_MAP.put(345, new String[]{"RIGHT_CONTROL", "RCTRL"});
      KEYBOARD_MAP.put(346, new String[]{"RIGHT_ALT", "RALT"});
      KEYBOARD_MAP.put(347, new String[]{"RIGHT_SUPER", "RWIN"});
      KEYBOARD_MAP.put(348, new String[]{"MENU"});
      KEYBOARD_MAP.put(161, new String[]{"WORLD_1"});
      KEYBOARD_MAP.put(162, new String[]{"WORLD_2"});
      KEYBOARD_MAP.put(-1, new String[]{"UNKNOWN"});
      MOUSE_MAP.put(0, new String[]{"1", "LEFT"});
      MOUSE_MAP.put(1, new String[]{"2", "RIGHT"});
      MOUSE_MAP.put(2, new String[]{"3", "MIDDLE"});
      MOUSE_MAP.put(3, new String[]{"4"});
      MOUSE_MAP.put(4, new String[]{"5"});
      MOUSE_MAP.put(5, new String[]{"6"});
      MOUSE_MAP.put(6, new String[]{"7"});
      MOUSE_MAP.put(7, new String[]{"8"});
      GAMEPAD_MAP.put(0, new String[]{"A", "CROSS"});
      GAMEPAD_MAP.put(1, new String[]{"B", "CIRCLE"});
      GAMEPAD_MAP.put(2, new String[]{"X", "SQUARE"});
      GAMEPAD_MAP.put(3, new String[]{"Y", "TRIANGLE"});
      GAMEPAD_MAP.put(4, new String[]{"LEFT_BUMPER", "LB"});
      GAMEPAD_MAP.put(5, new String[]{"RIGHT_BUMPER", "RB"});
      GAMEPAD_MAP.put(6, new String[]{"BACK", "SELECT"});
      GAMEPAD_MAP.put(7, new String[]{"START", "OPTIONS"});
      GAMEPAD_MAP.put(8, new String[]{"GUIDE"});
      GAMEPAD_MAP.put(9, new String[]{"LEFT_THUMB", "LTHUMB"});
      GAMEPAD_MAP.put(10, new String[]{"RIGHT_THUMB", "RTHUMB"});
      GAMEPAD_MAP.put(11, new String[]{"DPAD_UP", "DPAD_UP"});
      GAMEPAD_MAP.put(12, new String[]{"DPAD_RIGHT", "DPAD_RIGHT"});
      GAMEPAD_MAP.put(13, new String[]{"DPAD_DOWN", "DPAD_DOWN"});
      GAMEPAD_MAP.put(14, new String[]{"DPAD_LEFT", "DPAD_LEFT"});
      GAMEPAD_MAP.put(0, new String[]{"LEFT_X", "LX"});
      GAMEPAD_MAP.put(1, new String[]{"LEFT_Y", "LY"});
      GAMEPAD_MAP.put(2, new String[]{"RIGHT_X", "RX"});
      GAMEPAD_MAP.put(3, new String[]{"RIGHT_Y", "RY"});
      GAMEPAD_MAP.put(4, new String[]{"LEFT_TRIGGER", "LT"});
      GAMEPAD_MAP.put(5, new String[]{"RIGHT_TRIGGER", "RT"});
   }
}
