package org.rusherhack.client.api.ui.window.view;

import java.util.List;
import org.rusherhack.client.api.feature.window.ResizeableWindow;
import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.ui.window.content.WindowContent;
import org.rusherhack.client.api.utils.objects.Scrollbar;
import org.rusherhack.core.animation.Animation;
import org.rusherhack.core.animation.Easing;

public class ScrollableView extends SimpleView {
   protected boolean isDraggingScrollbarGrip = false;
   public double dragDeltaY = 0.0;
   protected double prevMax = 0.0;
   protected final Scrollbar scrollbar = new Scrollbar(new Animation(Easing.LINEAR, 100L));

   public ScrollableView(Window window, List<? extends WindowContent> contentList) {
      super(window, contentList);
   }

   public ScrollableView(String name, Window window, List<? extends WindowContent> contentList) {
      super(name, window, contentList);
   }

   @Override
   public void renderViewContent(double mouseX, double mouseY) {
      double max = this.getContentHeight() - this.getHeight();
      double scrollOffset = this.scrollbar.getScrollOffset();
      boolean wasNearBottom = scrollOffset >= this.prevMax - this.getHeight() / 2.0;
      super.renderViewContent(mouseX, mouseY);
      if (this.shouldAutoJumpToBottom()
         && (this.prevMax != max && wasNearBottom || !this.canScroll() || this.getWindow() instanceof ResizeableWindow resizeable && resizeable.isResizing())) {
         this.scrollbar.clamp(true, max, max);
      }

      if (this.canScroll()) {
         this.scrollbar.clamp(true, 0.0, max);
      }

      this.prevMax = max;
   }

   protected boolean shouldAutoJumpToBottom() {
      return false;
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
      boolean consumed = super.mouseScrolled(mouseX, mouseY, delta);
      if (!consumed && this.isHovered(mouseX, mouseY) && this.canScroll()) {
         this.scrollbar.scroll(-delta * 20.0, false);
         this.scrollbar.clamp(true, 0.0, this.getContentHeight() - this.getHeight());
      }

      return consumed;
   }

   @Override
   public void mouseReleased(double mouseX, double mouseY, int button) {
      this.isDraggingScrollbarGrip = false;
      super.mouseReleased(mouseX, mouseY, button);
   }

   @Override
   protected double getTopOffset() {
      return -this.scrollbar.getScrollOffset();
   }

   public boolean canScroll() {
      return this.getContentHeight() > this.getHeight();
   }

   public void setDraggingGrip(boolean dragging, double dragDeltaY) {
      this.isDraggingScrollbarGrip = dragging;
      this.dragDeltaY = dragDeltaY;
   }

   public Scrollbar getScrollbar() {
      return this.scrollbar;
   }

   @Override
   public double getContentHeight() {
      double contentHeight = this.topPadding;

      for (WindowContent content : this.contentList) {
         contentHeight += content.getHeight() + this.contentPadding;
      }

      return contentHeight;
   }

   public boolean isDraggingScrollbarGrip() {
      return this.isDraggingScrollbarGrip;
   }

   public void scrollToTop() {
      this.scrollbar.clamp(true, 0.0, 0.0);
   }

   public void scrollToBottom() {
      double max = this.getContentHeight() - this.getHeight();
      this.scrollbar.clamp(true, max, max);
   }
}
