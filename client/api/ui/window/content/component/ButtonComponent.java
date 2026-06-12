package org.rusherhack.client.api.ui.window.content.component;

import java.util.function.Predicate;
import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.ui.window.content.WindowContent;
import org.rusherhack.client.api.ui.window.view.WindowView;
import org.rusherhack.core.interfaces.INamed;

public class ButtonComponent extends WindowContent implements INamed {
   private String label;
   private double width;
   private double height;
   private Predicate<ButtonComponent> enabledPredicate = button -> true;
   private boolean pressed = false;
   private Runnable clickAction;

   public ButtonComponent(Window window, String label, Runnable clickAction) {
      this(window, label, -1.0, -1.0, clickAction);
   }

   public ButtonComponent(Window window, String label, double width, double height, Runnable clickAction) {
      super(window);
      this.label = label;
      this.width = width;
      this.height = height;
      this.clickAction = clickAction;
   }

   @Override
   public void renderContent(double mouseX, double mouseY, WindowView parent) {
   }

   @Override
   public double getWidth() {
      return this.width > 0.0 ? this.width : this.getFontRenderer().getStringWidth(this.label) + 4.0;
   }

   @Override
   public double getHeight() {
      return this.height > 0.0 ? this.height : this.getFontRenderer().getFontHeight() + 3.0;
   }

   public void setWidth(double width) {
      this.width = width;
   }

   public void setHeight(double height) {
      this.height = height;
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.isHovered(mouseX, mouseY) && button == 0 && this.enabledPredicate.test(this)) {
         this.pressed = true;
         this.onClick();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void mouseReleased(double mouseX, double mouseY, int button) {
      super.mouseReleased(mouseX, mouseY, button);
      this.pressed = false;
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

   @Override
   public String getName() {
      return this.label;
   }

   public void setLabel(String name) {
      this.label = name;
   }

   public void setAction(Runnable action) {
      this.clickAction = action;
   }

   public void onClick() {
      if (this.clickAction != null && this.enabledPredicate.test(this)) {
         this.clickAction.run();
      }
   }

   public boolean isPressed() {
      return this.pressed;
   }

   public void setPressed(boolean pressed) {
      this.pressed = pressed;
   }

   public void setPredicate(Predicate<ButtonComponent> enabledPredicate) {
      this.enabledPredicate = enabledPredicate == null ? button -> true : enabledPredicate;
   }

   public boolean isEnabled() {
      return this.enabledPredicate.test(this);
   }
}
