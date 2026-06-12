package org.rusherhack.client.api.ui.window.content.component;

import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.render.font.IFontRenderer;
import org.rusherhack.client.api.ui.window.content.WindowContent;
import org.rusherhack.client.api.ui.window.view.WindowView;
import org.rusherhack.client.api.utils.objects.TextField;
import org.rusherhack.core.utils.Timer;

public class TextFieldComponent extends WindowContent {
   protected final TextField textField = new TextField();
   private String label;
   private final boolean censored;
   protected boolean focused = false;
   private final Timer inputDelay = new Timer();
   private double width;
   private final double heightRatio;
   private final Timer cursorBlinkTimer = new Timer();
   private boolean blink = false;
   private Consumer<String> returnCallback = str -> this.focused = false;

   public TextFieldComponent(Window window, double width) {
      this(window, "", width);
   }

   public TextFieldComponent(Window window, String label, double width) {
      this(window, label, width, false);
   }

   public TextFieldComponent(Window window, String label, double width, boolean censored) {
      super(window);
      this.label = label;
      this.width = width;
      this.heightRatio = 1.25;
      this.censored = censored;
   }

   @Override
   public void renderContent(double mouseX, double mouseY, WindowView parent) {
      IRenderer2D renderer = this.getRenderer();
      IFontRenderer fr = this.getFontRenderer();
      double textX = this.x + 2.0;
      double textY = this.y + 0.5 + fr.getFontHeight() * (this.heightRatio - 1.0) / 2.0;
      String value = this.getDisplayValue();
      double cursorX = textX - 1.0;
      renderer.scissorBox(this.x, this.y, this.getWidth(), this.getHeight());
      if (value.isEmpty()) {
         fr.drawString(this.label, textX, textY, Color.GRAY.getRGB());
      } else {
         if (this.isFocused() && !this.textField.getHighlighted().isEmpty()) {
            String preHighlightedText = this.getDisplayValue().substring(0, this.textField.getHighlightStartPos());
            String highlightedText = this.getDisplayValue().substring(this.textField.getHighlightStartPos(), this.textField.getHighlightEndPos());
            String postHighlightedText = this.getDisplayValue().substring(this.textField.getHighlightEndPos());
            double highlightX = cursorX + fr.getStringWidth(preHighlightedText);
            double highlightY = textY;
            double highlightWidth = fr.getStringWidth(highlightedText);
            double highlightHeight = fr.getFontHeight();
            renderer.drawRectangle(highlightX, highlightY, highlightWidth, highlightHeight, -1);
            textX = fr.drawString(preHighlightedText, textX, textY, -1);
            textX = fr.drawString(highlightedText, textX, textY, 0);
            fr.drawString(postHighlightedText, textX, textY, -1);
         } else {
            fr.drawString(value, textX, textY, -1);
         }

         cursorX += fr.getStringWidth(value.substring(0, this.textField.getCursorPosition()));
      }

      if (this.cursorBlinkTimer.passed(500.0)) {
         this.blink = !this.blink;
         this.cursorBlinkTimer.reset();
      }

      if (this.focused && this.blink) {
         double cursorWidth = 1.0;
         double cursorHeight = fr.getFontHeight();
         renderer.drawRectangle(cursorX, textY, 1.0, cursorHeight, -1);
      }

      renderer.popScissorBox();
   }

   @Override
   public void unfocus() {
      this.focused = false;
   }

   @Override
   public double getWidth() {
      return this.width;
   }

   @Override
   public double getHeight() {
      return this.getFontRenderer().getFontHeight() * this.heightRatio;
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.isHovered(mouseX, mouseY)) {
         this.setFocused(true);
         return true;
      } else {
         this.setFocused(false);
         return false;
      }
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
      return false;
   }

   @Override
   public boolean charTyped(char character) {
      if (this.isFocused() && this.inputDelay.passed(200.0)) {
         boolean b = this.textField.charTyped(character);
         if (b) {
            this.cursorBlinkTimer.reset();
            this.blink = false;
         }

         return b;
      } else {
         return false;
      }
   }

   @Override
   public boolean keyTyped(int key, int scanCode, int modifiers) {
      if (!this.isFocused() || !this.inputDelay.passed(200.0)) {
         return false;
      } else if (key == 257 && this.returnCallback != null) {
         this.returnCallback.accept(this.getValue());
         return true;
      } else {
         return this.textField.keyTyped(key, scanCode, modifiers);
      }
   }

   public String getDisplayValue() {
      return this.censored ? this.textField.getDisplayText().replaceAll(".", "*") : this.textField.getDisplayText();
   }

   public String getLabel() {
      return this.label;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public String getValue() {
      return this.textField.getDisplayText();
   }

   public void setValue(String value) {
      this.textField.reset();
      this.textField.insertText(value);
   }

   public void setWidth(double width) {
      this.width = width;
   }

   public void setReturnCallback(Consumer<String> returnCallback) {
      this.returnCallback = returnCallback;
   }

   public void setCharacterFilter(Predicate<Character> characterFilter) {
      this.textField.setCharacterFilter(characterFilter);
   }

   public boolean isFocused() {
      return this.focused;
   }

   public void setFocused(boolean focused) {
      this.focused = focused;
      if (focused) {
         this.inputDelay.reset();
      }
   }

   public TextField getTextField() {
      return this.textField;
   }
}
