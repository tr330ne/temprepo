package org.rusherhack.client.api.ui.window.view;

import java.util.ArrayList;
import java.util.List;
import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.ui.window.content.WindowContent;

public class TabbedView extends SimpleView {
   private WindowView activeTabView = null;

   public TabbedView(Window window, List<WindowContent> contentList) {
      super(window, contentList);
   }

   public TabbedView(String name, Window window, List<WindowContent> contentList) {
      super(name, window, contentList);
   }

   @Override
   public void renderViewContent(double mouseX, double mouseY) {
      super.renderViewContent(mouseX, mouseY);
   }

   @Override
   public List<WindowContent> getContent() {
      List<WindowContent> persistentContent = new ArrayList<>();

      for (WindowContent content : this.contentList) {
         if (!(content instanceof WindowView)) {
            persistentContent.add(content);
         }
      }

      return persistentContent;
   }

   public void setActiveTabView(WindowView activeTabView) {
      this.activeTabView = activeTabView;
   }

   public WindowView getActiveTabView() {
      return this.activeTabView;
   }

   public List<WindowView> getTabs() {
      List<WindowView> views = new ArrayList<>();

      for (WindowContent content : this.contentList) {
         if (content instanceof WindowView) {
            views.add((WindowView)content);
         }
      }

      return views;
   }

   public double getTabViewHeight() {
      return this.getHeight() - this.getPersistentContentHeight();
   }

   public double getPersistentContentHeight() {
      double height = this.topPadding;

      for (WindowContent content : this.getContent()) {
         height += content.getHeight() + this.contentPadding;
      }

      return height;
   }

   @Override
   protected double getTopOffset() {
      return this.getTabViewHeight();
   }

   @Override
   protected double getLeftOffset() {
      return super.getLeftOffset();
   }
}
