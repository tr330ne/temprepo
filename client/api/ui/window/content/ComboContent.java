package org.rusherhack.client.api.ui.window.content;

import it.unimi.dsi.fastutil.Pair;
import java.util.ArrayList;
import java.util.List;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.ui.window.content.component.TextFieldComponent;
import org.rusherhack.client.api.ui.window.view.WindowView;

public class ComboContent extends WindowContent {
   private final List<Pair<WindowContent, ComboContent.AnchorSide>> contents = new ArrayList<>();
   private WindowView parent;

   public ComboContent(Window window) {
      super(window);
   }

   public void addContent(WindowContent content) {
      this.addContent(content, ComboContent.AnchorSide.LEFT);
   }

   public void addContent(WindowContent content, ComboContent.AnchorSide anchor) {
      this.contents.add(Pair.of(content, anchor));
   }

   @Override
   public void renderContent(double mouseX, double mouseY, WindowView parent) {
      this.parent = parent;
      double padding = 2.0;
      double leftOffset = 0.0;
      double rightOffset = 0.0;
      double remainingWidth = this.getWidth();
      double textFieldWidth = this.getWidth();
      int textFields = 0;

      for (Pair<WindowContent, ComboContent.AnchorSide> c : this.contents) {
         if (!(c.left() instanceof TextFieldComponent)) {
            textFieldWidth -= ((WindowContent)c.left()).getWidth() + 2.0;
         } else {
            textFields++;
         }
      }

      textFieldWidth /= textFields;

      for (Pair<WindowContent, ComboContent.AnchorSide> pair : this.contents) {
         WindowContent content = (WindowContent)pair.left();
         ComboContent.AnchorSide anchor = (ComboContent.AnchorSide)pair.right();
         if (content instanceof TextFieldComponent textField) {
            textField.setWidth(textFieldWidth);
         }

         double width = content.getWidth();
         double height = content.getHeight();
         if (anchor == ComboContent.AnchorSide.LEFT) {
            content.setX(this.x + leftOffset);
            leftOffset += width + 2.0;
         } else {
            content.setX(this.x + this.getWidth() - rightOffset - width);
            rightOffset += width + 2.0;
         }

         content.setY(this.y + this.getHeight() / 2.0 - height / 2.0);
         remainingWidth -= width + 2.0;
         RusherHackAPI.getWindowManager().getWindowHandler().getContentHandler().handleRenderContent(content, mouseX, mouseY, parent);
      }
   }

   @Override
   public double getWidth() {
      return this.parent != null ? this.parent.getViewWidth() - 1.0 : 0.0;
   }

   @Override
   public double getHeight() {
      int maxHeight = 0;

      for (Pair<WindowContent, ComboContent.AnchorSide> content : this.contents) {
         double height = ((WindowContent)content.left()).getHeight();
         if (height > maxHeight) {
            maxHeight = (int)height;
         }
      }

      return maxHeight;
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      for (Pair<WindowContent, ComboContent.AnchorSide> content : this.contents) {
         if (RusherHackAPI.getWindowManager()
            .getWindowHandler()
            .getContentHandler()
            .handleMouseClicked((WindowContent)content.left(), mouseX, mouseY, button, this.parent)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
      for (Pair<WindowContent, ComboContent.AnchorSide> content : this.contents) {
         if (((WindowContent)content.left()).mouseScrolled(mouseX, mouseY, delta)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean charTyped(char character) {
      for (Pair<WindowContent, ComboContent.AnchorSide> content : this.contents) {
         if (((WindowContent)content.left()).charTyped(character)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean keyTyped(int key, int scanCode, int modifiers) {
      for (Pair<WindowContent, ComboContent.AnchorSide> content : this.contents) {
         if (((WindowContent)content.left()).keyTyped(key, scanCode, modifiers)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void unfocus() {
      for (Pair<WindowContent, ComboContent.AnchorSide> content : this.contents) {
         ((WindowContent)content.left()).unfocus();
      }
   }

   @Override
   public void mouseReleased(double mouseX, double mouseY, int button) {
      for (Pair<WindowContent, ComboContent.AnchorSide> content : this.contents) {
         ((WindowContent)content.left()).mouseReleased(mouseX, mouseY, button);
      }
   }

   public List<Pair<WindowContent, ComboContent.AnchorSide>> getContents() {
      return this.contents;
   }

   public enum AnchorSide {
      LEFT,
      RIGHT;
   }
}
