package org.rusherhack.client.api.ui.window.content;

import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.ui.window.view.ListView;
import org.rusherhack.client.api.ui.window.view.WindowView;

public abstract class ListItemContent extends WindowContent {
   protected final ListView<?> listView;
   private long timeSinceLastClick = 0L;

   public ListItemContent(Window window, ListView<?> listView) {
      super(window);
      this.listView = listView;
   }

   public abstract String getAsString(ListView<?>.Column var1);

   public boolean isSelected() {
      return this.listView.getSelectedItem() == this;
   }

   @Override
   public void renderContent(double mouseX, double mouseY, WindowView parent) {
   }

   @Override
   public double getWidth() {
      return this.listView.getWidth();
   }

   @Override
   public double getHeight() {
      return this.getFontRenderer().getFontHeight() + 2.0;
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.isHovered(mouseX, mouseY)) {
         if (button == 0 && this.isSelected() && System.currentTimeMillis() - this.timeSinceLastClick < 500L) {
            this.onDoubleClick();
         }

         this.listView.setSelectedItem(this);
         this.timeSinceLastClick = System.currentTimeMillis();
         if (button == 0) {
            return true;
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

   protected void onDoubleClick() {
   }

   public ListView<?> getListView() {
      return this.listView;
   }
}
