package org.rusherhack.client.api.ui.window.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.rusherhack.client.api.Globals;
import org.rusherhack.client.api.feature.window.Window;
import org.rusherhack.client.api.ui.window.content.component.ParagraphComponent;
import org.rusherhack.client.api.ui.window.context.ContextAction;
import org.rusherhack.client.api.utils.ChatUtils;

public class RichTextView extends ScrollableView {
   private final List<RichTextView.RichTextComponent> textComponents = new ArrayList<>();
   private final Queue<RichTextView.RichTextComponent> queue = new ConcurrentLinkedQueue<>();

   public RichTextView(Window window) {
      super(window, null);
      this.setContentList(this.textComponents);
   }

   public RichTextView(String name, Window window) {
      super(name, window, null);
      this.setContentList(this.textComponents);
   }

   public void add(String text, int color) {
      if (text.contains("§")) {
         this.add(Component.literal(text), color);
      } else {
         RichTextView.RichTextComponent c = new RichTextView.RichTextComponent(this.getWindow(), text);
         this.newComponent(c, color);
      }
   }

   public void add(Component text, int color) {
      RichTextView.RichTextComponent c = new RichTextView.RichTextComponent(this.getWindow(), text);
      this.newComponent(c, color);
   }

   @Override
   public void renderViewContent(double mouseX, double mouseY) {
      super.renderViewContent(mouseX, mouseY);

      while (!this.queue.isEmpty()) {
         this.textComponents.add(this.queue.poll());
      }
   }

   public void clear() {
      synchronized (this.getContent()) {
         this.textComponents.clear();
      }
   }

   @Override
   protected boolean shouldAutoJumpToBottom() {
      return true;
   }

   protected void newComponent(RichTextView.RichTextComponent c, int color) {
      c.setColor(color);
      this.queue.add(c);
   }

   public static class RichTextComponent extends ParagraphComponent {
      private final Component component;

      public RichTextComponent(Window window, String str) {
         super(window, str);
         this.component = null;
      }

      public RichTextComponent(Window window, Component component) {
         super(window, ChatUtils.stripFormatting(component.getString()));
         this.component = component;
      }

      @Override
      public void renderContent(double mouseX, double mouseY, WindowView parent) {
         if (this.component != null) {
            this.parent = parent;
            this.heightCache = this.getFontRenderer().drawText(this.component, this.x, this.y, this.color, this.parent.getViewWidth(), this.lineSpacing);
         } else {
            super.renderContent(mouseX, mouseY, parent);
         }
      }

      @Nullable
      @Override
      public List<ContextAction> getContextMenu() {
         List<ContextAction> contextMenu = new ArrayList<>();
         contextMenu.add(new ContextAction("Copy", () -> Globals.mc.keyboardHandler.setClipboard(this.getText())));
         return contextMenu;
      }
   }
}
