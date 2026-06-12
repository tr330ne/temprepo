package org.rusherhack.client.api.ui.window;

import org.rusherhack.client.api.ui.window.content.ListItemContent;
import org.rusherhack.client.api.ui.window.content.WindowContent;
import org.rusherhack.client.api.ui.window.content.component.ButtonComponent;
import org.rusherhack.client.api.ui.window.content.component.CheckBoxComponent;
import org.rusherhack.client.api.ui.window.content.component.ComboBoxComponent;
import org.rusherhack.client.api.ui.window.content.component.TextFieldComponent;
import org.rusherhack.client.api.ui.window.view.WindowView;

public abstract class WindowContentHandlerBase {
   public boolean handleMouseClicked(WindowContent content, double mouseX, double mouseY, int button, WindowView view) {
      return content.mouseClicked(mouseX, mouseY, button);
   }

   public void handleRenderContent(WindowContent content, double mouseX, double mouseY, WindowView view) {
      if (content instanceof ButtonComponent button) {
         this.renderButton(button, mouseX, mouseY, view);
      } else if (content instanceof CheckBoxComponent checkBox) {
         this.renderCheckBox(checkBox, mouseX, mouseY, view);
      } else if (content instanceof ComboBoxComponent dropdown) {
         this.renderComboBox(dropdown, mouseX, mouseY, view);
      } else if (content instanceof TextFieldComponent textField) {
         this.renderTextField(textField, mouseX, mouseY, view);
      } else if (content instanceof ListItemContent listItem) {
         this.renderListItem(listItem, mouseX, mouseY, view);
      } else {
         content.renderContent(mouseX, mouseY, view);
      }
   }

   public abstract void renderButton(ButtonComponent var1, double var2, double var4, WindowView var6);

   public abstract void renderCheckBox(CheckBoxComponent var1, double var2, double var4, WindowView var6);

   public abstract void renderComboBox(ComboBoxComponent var1, double var2, double var4, WindowView var6);

   public abstract void renderTextField(TextFieldComponent var1, double var2, double var4, WindowView var6);

   public abstract void renderListItem(ListItemContent var1, double var2, double var4, WindowView var6);
}
