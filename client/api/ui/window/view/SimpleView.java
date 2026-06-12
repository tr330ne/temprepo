package org.rusherhack.client.api.ui.window.view;

import java.util.List;
import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.ui.window.content.WindowContent;

public class SimpleView extends WindowView {
   protected SimpleView.Alignment horizontalAlignment = SimpleView.Alignment.LEFT;
   protected SimpleView.VerticalAlignment verticalAlignment = SimpleView.VerticalAlignment.TOP;
   protected double topPadding = 2.0;
   protected double leftPadding = 2.0;
   protected double contentPadding = 1.0;
   private double viewWidthModifier = 0.0;

   public SimpleView(Window window, List<? extends WindowContent> contentList) {
      super(window, contentList);
   }

   public SimpleView(String name, Window window, List<? extends WindowContent> contentList) {
      super(name, window, contentList);
   }

   @Override
   public void renderViewContent(double mouseX, double mouseY) {
      double yOffset = switch (this.verticalAlignment) {
         case TOP -> this.getTopOffset() + this.topPadding;
         case CENTER -> this.getViewHeight() / 2.0 - this.getContentHeight() / 2.0 - 1.0;
         case BOTTOM -> this.getViewHeight() - this.getContentHeight() - 1.0;
      };

      for (WindowContent content : this.getContent()) {
         double x = this.getX() + this.getLeftOffset() + this.leftPadding;
         switch (this.horizontalAlignment) {
            case LEFT:
               content.setX(x);
               break;
            case CENTER:
               content.setX(x + (this.getViewWidth() - content.getWidth()) / 2.0);
               break;
            case RIGHT:
               content.setX(x + this.getViewWidth() - content.getWidth());
         }

         content.setY(this.getY() + yOffset);
         boolean shouldCull = content.getY() + content.getHeight() < this.getY() || content.getY() > this.getY() + this.getHeight();
         if (!shouldCull) {
            if (content instanceof WindowView view) {
               view.setViewWidth(this.getViewWidth());
               view.setViewHeight(this.getViewHeight() - yOffset);
            }

            this.getHandler().getContentHandler().handleRenderContent(content, mouseX, mouseY, this);
         }

         yOffset += content.getHeight() + this.contentPadding;
      }
   }

   @Override
   public double getViewWidth() {
      return super.getViewWidth() + this.viewWidthModifier - this.leftPadding * 2.0 - this.getLeftOffset();
   }

   @Override
   public double getViewHeight() {
      return super.getViewHeight() - this.topPadding * 2.0 - this.getTopOffset();
   }

   protected double getTopOffset() {
      return 0.0;
   }

   protected double getLeftOffset() {
      return 0.0;
   }

   public void setTopPadding(double topPadding) {
      this.topPadding = topPadding;
   }

   public void setLeftPadding(double leftPadding) {
      this.leftPadding = leftPadding;
   }

   public void setContentPadding(double contentPadding) {
      this.contentPadding = contentPadding;
   }

   public void setViewWidthModifier(double viewWidthModifier) {
      this.viewWidthModifier = viewWidthModifier;
   }

   public void setAlignment(SimpleView.Alignment alignment) {
      this.horizontalAlignment = alignment;
   }

   public void setVerticalAlignment(SimpleView.VerticalAlignment verticalAlignment) {
      this.verticalAlignment = verticalAlignment;
   }

   public double getContentWidth() {
      double width = 0.0;

      for (WindowContent content : this.contentList) {
         if (content.getWidth() > width) {
            width = content.getWidth();
         }
      }

      return width;
   }

   public double getContentHeight() {
      double height = 0.0;

      for (WindowContent content : this.contentList) {
         height += content.getHeight() + this.contentPadding;
      }

      return height;
   }

   public enum Alignment {
      LEFT,
      CENTER,
      RIGHT;
   }

   public enum VerticalAlignment {
      TOP,
      CENTER,
      BOTTOM;
   }
}
