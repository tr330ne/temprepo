package org.rusherhack.client.api.ui.window.content.component;

import java.util.Arrays;
import java.util.function.Consumer;
import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.ui.window.content.WindowContent;
import org.rusherhack.client.api.ui.window.view.WindowView;
import org.rusherhack.core.utils.MathUtils;

public class ComboBoxComponent extends WindowContent {
   private final String[] options;
   private int selected;
   private double height;
   private boolean isOpen = false;
   private Consumer<String> callback;

   public ComboBoxComponent(Window window, String[] options) {
      this(window, options, 0, null);
   }

   public ComboBoxComponent(Window window, Enum<?> enumConstant) {
      this(window, (String[])Arrays.stream((Enum[])enumConstant.getClass().getEnumConstants()).map(Enum::name).toArray(), enumConstant.ordinal(), null);
   }

   public ComboBoxComponent(Window window, String[] options, int selected, Consumer<String> callback) {
      super(window);
      this.options = options;
      this.selected = selected;
      this.height = 12.0;
      this.callback = callback;
   }

   @Override
   public void renderContent(double mouseX, double mouseY, WindowView parent) {
   }

   @Override
   public void unfocus() {
      this.setOpen(false);
   }

   @Override
   public double getWidth() {
      double longest = 0.0;

      for (String option : this.options) {
         double width = this.getFontRenderer().getStringWidth(option);
         if (width > longest) {
            longest = width;
         }
      }

      return longest;
   }

   @Override
   public double getHeight() {
      return this.height;
   }

   public void setHeight(double height) {
      this.height = height;
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
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

   public String[] getOptions() {
      return this.options;
   }

   public String getSelectedOption() {
      return this.options[MathUtils.clamp(this.selected, 0, this.options.length - 1)];
   }

   public int getSelected() {
      return this.selected;
   }

   public void setSelected(int selected) {
      this.selected = selected;
      if (this.getCallback() != null) {
         this.getCallback().accept(this.getSelectedOption());
      }
   }

   public boolean isOpen() {
      return this.isOpen;
   }

   public void setOpen(boolean open) {
      this.isOpen = open;
   }

   public Consumer<String> getCallback() {
      return this.callback;
   }

   public void setCallback(Consumer<String> callback) {
      this.callback = callback;
   }
}
