package org.rusherhack.client.api.ui.window.content.component;

import java.util.function.Consumer;
import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.ui.window.content.WindowContent;
import org.rusherhack.client.api.ui.window.view.WindowView;

public class CheckBoxComponent extends WindowContent {
   protected final double size;
   protected String label;
   protected boolean value;
   protected Consumer<Boolean> valueConsumer;

   public CheckBoxComponent(Window window, String label, boolean value) {
      this(window, 12.0, label, value, null);
   }

   public CheckBoxComponent(Window window, double size, String label, boolean value) {
      this(window, size, label, value, null);
   }

   public CheckBoxComponent(Window window, double size, String label, boolean value, Consumer<Boolean> callback) {
      super(window);
      this.size = size;
      this.label = label;
      this.value = value;
      this.valueConsumer = callback;
   }

   @Override
   public void renderContent(double mouseX, double mouseY, WindowView parent) {
   }

   @Override
   public double getWidth() {
      return this.size + 2.0 + this.getFontRenderer().getStringWidth(this.label);
   }

   @Override
   public double getHeight() {
      return this.size;
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.isHovered(mouseX, mouseY) && button == 0) {
         this.setValue(!this.getValue());
         if (this.valueConsumer != null) {
            this.valueConsumer.accept(this.getValue());
         }
      }

      return false;
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
      return false;
   }

   @Override
   public boolean charTyped(char character) {
      return false;
   }

   @Override
   public boolean keyTyped(int key, int scanCode, int modifiers) {
      return false;
   }

   public String getLabel() {
      return this.label;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public boolean getValue() {
      return this.value;
   }

   public void setValue(boolean value) {
      this.value = value;
   }

   public void setValueConsumer(Consumer<Boolean> valueConsumer) {
      this.valueConsumer = valueConsumer;
   }

   public double getSize() {
      return this.size;
   }
}
