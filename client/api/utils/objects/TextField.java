package org.rusherhack.client.api.utils.objects;

import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rusherhack.core.interfaces.ITypeable;

public class TextField implements ITypeable {
   private String value = "";
   private boolean shiftPressed;
   private final int maxLength;
   private int cursorPos = 0;
   private int highlightPos;
   private Predicate<Character> characterFilter = c -> c != 167 && c >= ' ' && c != 127;
   @Nullable
   private Consumer<String> consumer;

   public TextField() {
      this(null);
   }

   public TextField(Consumer<String> consumer) {
      this(consumer, 128);
   }

   public TextField(Consumer<String> consumer, int maxLength) {
      this.consumer = consumer;
      this.maxLength = maxLength;
   }

   public String getDisplayText() {
      return this.value;
   }

   public void setConsumer(Consumer<String> consumer) {
      this.consumer = consumer;
   }

   public void setCharacterFilter(Predicate<Character> characterFilter) {
      this.characterFilter = characterFilter;
   }

   public void reset() {
      this.value = "";
      this.cursorPos = 0;
      this.highlightPos = 0;
   }

   private boolean isAllowedCharacter(char character) {
      return this.characterFilter == null || this.characterFilter.test(character);
   }

   private String filterText(@NotNull String text) {
      StringBuilder stringBuilder = new StringBuilder();

      for (char c : text.toCharArray()) {
         if (this.isAllowedCharacter(c)) {
            stringBuilder.append(c);
         }
      }

      return stringBuilder.toString();
   }

   @Override
   public boolean charTyped(char character) {
      if (this.isAllowedCharacter(character)) {
         this.insertText(Character.toString(character));
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean keyTyped(int keyCode, int scanCode, int modifiers) {
      this.shiftPressed = Screen.hasShiftDown();
      if (Screen.isSelectAll(keyCode)) {
         this.moveCursorToEnd();
         this.setHighlightPos(0);
         return true;
      }

      if (Screen.isCopy(keyCode)) {
         Minecraft.getInstance().keyboardHandler.setClipboard(this.getHighlighted());
         return true;
      }

      if (Screen.isPaste(keyCode)) {
         this.insertText(Minecraft.getInstance().keyboardHandler.getClipboard());
         return true;
      }

      if (Screen.isCut(keyCode)) {
         Minecraft.getInstance().keyboardHandler.setClipboard(this.getHighlighted());
         this.insertText("");
         return true;
      }

      switch (keyCode) {
         case 259:
            this.shiftPressed = false;
            this.deleteText(-1);
            this.shiftPressed = Screen.hasShiftDown();
            return true;
         case 260:
         case 264:
         case 265:
         case 266:
         case 267:
         default:
            return false;
         case 261:
            this.shiftPressed = false;
            this.deleteText(1);
            this.shiftPressed = Screen.hasShiftDown();
            return true;
         case 262:
            if (Screen.hasControlDown()) {
               this.moveCursorTo(this.getWordPosition(1));
            } else {
               this.moveCursor(1);
            }

            return true;
         case 263:
            if (Screen.hasControlDown()) {
               this.moveCursorTo(this.getWordPosition(-1));
            } else {
               this.moveCursor(-1);
            }

            return true;
         case 268:
            this.moveCursorToStart();
            return true;
         case 269:
            this.moveCursorToEnd();
            return true;
      }
   }

   public void insertText(String text) {
      int i = Math.min(this.cursorPos, this.highlightPos);
      int j = Math.max(this.cursorPos, this.highlightPos);
      int k = this.maxLength - this.value.length() - (i - j);
      text = this.filterText(text);
      int l = text.length();
      if (k < l) {
         text = text.substring(0, k);
         l = k;
      }

      this.value = new StringBuilder(this.value).replace(i, j, text).toString();
      this.setCursorPosition(i + l);
      this.setHighlightPos(this.cursorPos);
      this.onValueChange(this.value);
   }

   private void deleteText(int count) {
      if (Screen.hasControlDown()) {
         this.deleteWords(count);
      } else {
         this.deleteChars(count);
      }
   }

   public void deleteWords(int num) {
      if (!this.value.isEmpty()) {
         if (this.highlightPos != this.cursorPos) {
            this.insertText("");
         } else {
            this.deleteChars(this.getWordPosition(num) - this.cursorPos);
         }
      }
   }

   public void deleteChars(int num) {
      if (!this.value.isEmpty()) {
         if (this.highlightPos != this.cursorPos) {
            this.insertText("");
         } else {
            int j = this.getCursorPos(num);
            int k = Math.min(j, this.cursorPos);
            int l = Math.max(j, this.cursorPos);
            if (k != l) {
               this.value = new StringBuilder(this.value).delete(k, l).toString();
               this.moveCursorTo(k);
            }
         }
      }
   }

   public String getHighlighted() {
      int i = Math.min(this.cursorPos, this.highlightPos);
      int j = Math.max(this.cursorPos, this.highlightPos);
      return this.value.substring(i, j);
   }

   public int getHighlightStartPos() {
      return Math.min(this.cursorPos, this.highlightPos);
   }

   public int getHighlightEndPos() {
      return Math.max(this.cursorPos, this.highlightPos);
   }

   public void setHighlightPos(int position) {
      int j = this.value.length();
      this.highlightPos = Mth.clamp(position, 0, j);
   }

   public void moveCursor(int delta) {
      this.moveCursorTo(this.getCursorPos(delta));
   }

   private int getCursorPos(int delta) {
      return Util.offsetByCodepoints(this.value, this.cursorPos, delta);
   }

   public void moveCursorTo(int pos) {
      this.setCursorPosition(pos);
      if (!this.shiftPressed) {
         this.setHighlightPos(this.cursorPos);
      }

      this.onValueChange(this.value);
   }

   public void setCursorPosition(int pos) {
      this.cursorPos = Mth.clamp(pos, 0, this.value.length());
   }

   public void moveCursorToStart() {
      this.moveCursorTo(0);
   }

   public void moveCursorToEnd() {
      this.moveCursorTo(this.value.length());
   }

   private void onValueChange(String newText) {
      if (this.consumer != null) {
         this.consumer.accept(newText);
      }
   }

   public int getCursorPosition() {
      return this.cursorPos;
   }

   public int getWordPosition(int numWords) {
      return this.getWordPosition(numWords, this.getCursorPosition());
   }

   private int getWordPosition(int n, int pos) {
      return this.getWordPosition(n, pos, true);
   }

   private int getWordPosition(int n, int pos, boolean skipWs) {
      int length = this.value.length();
      int cursor = Mth.clamp(pos, 0, length);

      for (int i = 0; i < Math.abs(n); i++) {
         if (n > 0) {
            while (cursor < length && !Character.isWhitespace(this.value.charAt(cursor))) {
               cursor++;
            }

            while (skipWs && cursor < length && Character.isWhitespace(this.value.charAt(cursor))) {
               cursor++;
            }
         } else if (n < 0) {
            while (skipWs && cursor > 0 && Character.isWhitespace(this.value.charAt(cursor - 1))) {
               cursor--;
            }

            while (cursor > 0 && !Character.isWhitespace(this.value.charAt(cursor - 1))) {
               cursor--;
            }
         }
      }

      return cursor;
   }
}
