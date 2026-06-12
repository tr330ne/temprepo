package org.rusherhack.client.api.ui.window.view;

import java.util.ArrayList;
import org.rusherhack.client.api.feature.window.Window;

public class SplitView extends WindowView {
   private static final int PADDING = 1;
   private WindowView left;
   private WindowView center;
   private WindowView right;
   private final double centerWidth;

   public SplitView(Window window, WindowView left, WindowView right) {
      this("unnamed", window, left, right);
   }

   public SplitView(String name, Window window, WindowView left, WindowView right) {
      this(name, window, left, null, 0.0, right);
   }

   public SplitView(String name, Window window, WindowView left, WindowView center, double centerWidth, WindowView right) {
      super(name, window, new ArrayList<>());
      this.left = left;
      this.center = center;
      this.centerWidth = centerWidth;
      this.right = right;
      this.add(this.left);
      if (this.center != null) {
         this.add(this.center);
      }

      this.add(this.right);
   }

   @Override
   public void renderViewContent(double mouseX, double mouseY) {
      double leftAndRightWidth = (this.getViewWidth() - this.centerWidth) / 2.0 - 2.0;
      double y = this.getY() + 1.0;
      double height = this.getViewHeight() - 2.0;
      this.left.setX(this.getX() + 1.0);
      this.left.setY(y);
      this.left.setViewWidth(leftAndRightWidth);
      this.left.setViewHeight(height);
      this.getHandler().getViewHandler().handleRenderViewContent(this.left, mouseX, mouseY);
      if (this.center != null) {
         this.center.setX(this.getX() + 1.0 + leftAndRightWidth + 1.0);
         this.center.setY(y);
         this.center.setViewWidth(this.centerWidth);
         this.center.setViewHeight(height);
         this.getHandler().getViewHandler().handleRenderViewContent(this.center, mouseX, mouseY);
      }

      this.right.setX(this.getX() + this.getViewWidth() - 1.0 - leftAndRightWidth);
      this.right.setY(y);
      this.right.setViewWidth(leftAndRightWidth);
      this.right.setViewHeight(height);
      this.getHandler().getViewHandler().handleRenderViewContent(this.right, mouseX, mouseY);
   }
}
