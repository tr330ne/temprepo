package org.rusherhack.client.api.ui.window.content;

import java.util.ArrayList;
import java.util.List;
import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.render.font.IFontRenderer;
import org.rusherhack.client.api.ui.window.context.ContextAction;
import org.rusherhack.client.api.ui.window.view.WindowView;
import org.rusherhack.core.interfaces.IClickable;
import org.rusherhack.core.interfaces.IScrollable;
import org.rusherhack.core.interfaces.ITickable;
import org.rusherhack.core.interfaces.ITypeable;

public abstract class WindowContent implements IClickable, ITypeable, IScrollable, ITickable {
   protected final Window window;
   protected double x;
   protected double y;
   protected List<ContextAction> contextMenu = new ArrayList<>();

   public WindowContent(Window window) {
      this.window = window;
   }

   public abstract void renderContent(double var1, double var3, WindowView var5);

   public abstract double getWidth();

   public abstract double getHeight();

   public List<ContextAction> getContextMenu() {
      return this.contextMenu;
   }

   public void setContextMenu(List<ContextAction> contextMenu) {
      this.contextMenu = contextMenu;
   }

   public void unfocus() {
   }

   public Window getWindow() {
      return this.window;
   }

   @Override
   public boolean isHovered(double mouseX, double mouseY) {
      return this.window.isHovered(mouseX, mouseY)
         && mouseX >= this.x
         && mouseX <= this.x + this.getWidth()
         && mouseY >= this.y
         && mouseY <= this.y + this.getHeight();
   }

   public double setX(double x) {
      return this.x = x;
   }

   public double setY(double y) {
      return this.y = y;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public IRenderer2D getRenderer() {
      return this.window.getRenderer();
   }

   public IFontRenderer getFontRenderer() {
      return this.window.getFontRenderer();
   }
}
