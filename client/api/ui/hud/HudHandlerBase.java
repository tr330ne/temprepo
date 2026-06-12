package org.rusherhack.client.api.ui.hud;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.util.List;
import org.rusherhack.client.api.Globals;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.hud.HudElement;
import org.rusherhack.client.api.feature.hud.ListHudElement;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.client.api.render.font.IFontRenderer;
import org.rusherhack.client.api.ui.ElementHandlerBase;
import org.rusherhack.client.api.ui.panel.PanelHandlerBase;

public abstract class HudHandlerBase extends ElementHandlerBase<HudElement> {
   private IFontRenderer lastFontRenderer = null;
   public boolean shouldResortLists = false;

   public HudHandlerBase(boolean scaledWithMinecraftGui) {
      super(scaledWithMinecraftGui);
   }

   @Override
   public List<HudElement> getElements() {
      return ImmutableList.copyOf(RusherHackAPI.getHudManager().getFeatures());
   }

   public void moveElementToTop(HudElement element) {
      RusherHackAPI.getHudManager().moveToTop(element);
   }

   @Override
   public void render(RenderContext context, double mouseX, double mouseY) {
      PanelHandlerBase<?> hudPanel = this.getHudManagerPanel();
      if (hudPanel != null) {
         IRenderer2D renderer = this.getRenderer();
         IFontRenderer fontRenderer = this.getFontRenderer();
         PoseStack matrixStack = context.pose();
         boolean building = renderer.isBuilding();
         if (!building) {
            renderer.begin(matrixStack, fontRenderer);
         }

         hudPanel.render(context, mouseX, mouseY);
         if (!building) {
            renderer.end();
         }
      }

      this.renderElements(context, mouseX, mouseY);
   }

   @Override
   public void initialize() {
      PanelHandlerBase<?> hudPanel = this.getHudManagerPanel();
      if (hudPanel != null) {
         hudPanel.initialize();
      }
   }

   @Override
   public void setDefaultPositions() {
      PanelHandlerBase<?> hudPanel = this.getHudManagerPanel();
      if (hudPanel != null) {
         hudPanel.setDefaultPositions();
      }
   }

   @Override
   public void tick() {
      PanelHandlerBase<?> hudPanel = this.getHudManagerPanel();
      if (hudPanel != null) {
         hudPanel.tick();
      }

      super.tick();
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      boolean consumed = this.consumeMouseClick(mouseX, mouseY, button);
      PanelHandlerBase<?> hudPanel = this.getHudManagerPanel();
      if (!consumed && hudPanel != null) {
         consumed = hudPanel.mouseClicked(mouseX, mouseY, button);
      }

      return consumed;
   }

   protected boolean consumeElementMouseClick(HudElement element, double mouseX, double mouseY, int button) {
      return element.isHovered(mouseX, mouseY) && super.consumeElementMouseClick(element, mouseX, mouseY, button);
   }

   @Override
   public void mouseReleased(double mouseX, double mouseY, int button) {
      PanelHandlerBase<?> hudPanel = this.getHudManagerPanel();
      if (hudPanel != null) {
         hudPanel.mouseReleased(mouseX, mouseY, button);
      }

      this.consumeMouseRelease(mouseX, mouseY, button);
   }

   @Override
   public void mouseMoved(double mouseX, double mouseY) {
      PanelHandlerBase<?> hudPanel = this.getHudManagerPanel();
      if (hudPanel != null) {
         hudPanel.mouseMoved(mouseX, mouseY);
      }

      this.consumeMouseMove(mouseX, mouseY);
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
      boolean consumed = this.consumeMouseScroll(mouseX, mouseY, delta);
      PanelHandlerBase<?> hudPanel = this.getHudManagerPanel();
      if (!consumed && hudPanel != null) {
         consumed = hudPanel.mouseScrolled(mouseX, mouseY, delta);
      }

      return consumed;
   }

   @Override
   public boolean charTyped(char character) {
      boolean consumed = super.charTyped(character);
      PanelHandlerBase<?> hudPanel = this.getHudManagerPanel();
      if (!consumed && hudPanel != null) {
         consumed = hudPanel.charTyped(character);
      }

      return consumed;
   }

   @Override
   public boolean keyTyped(int key, int scanCode, int modifiers) {
      boolean consumed = super.keyTyped(key, scanCode, modifiers);
      PanelHandlerBase<?> hudPanel = this.getHudManagerPanel();
      if (!consumed && hudPanel != null) {
         consumed = hudPanel.keyTyped(key, scanCode, modifiers);
      }

      return consumed;
   }

   @Override
   public void renderElements(RenderContext renderContext, double mouseX, double mouseY) {
      IRenderer2D renderer = this.getRenderer();
      IFontRenderer fontRenderer = this.getFontRenderer();
      if (!this.shouldResortLists) {
         this.shouldResortLists = this.lastFontRenderer != null && this.lastFontRenderer != fontRenderer && !this.shouldRenderPrimitively();
      }

      this.lastFontRenderer = fontRenderer;
      renderer.begin(renderContext.pose(), fontRenderer);

      for (HudElement hudElement : this.getElements()) {
         if (this.isEnabled(hudElement)) {
            this.renderElement(hudElement, renderContext, mouseX, mouseY);
         }
      }

      renderer.end();
   }

   protected void renderElement(HudElement hudElement, RenderContext context, double mouseX, double mouseY) {
      PoseStack matrixStack = context.pose();
      double elementScale = hudElement.getScale();
      matrixStack.pushPose();
      matrixStack.translate(hudElement.getStartX(), hudElement.getStartY(), 0.0);
      matrixStack.scale((float)elementScale, (float)elementScale, 1.0F);
      if (this.shouldRenderPrimitively()) {
         this.renderPrimitiveHudElement(hudElement, context, mouseX, mouseY);
      } else {
         this.renderHudElement(hudElement, context, mouseX, mouseY);
      }

      matrixStack.popPose();
      if (hudElement instanceof ListHudElement listElement && this.shouldResortLists) {
         listElement.resort();
      }
   }

   public void renderHudElement(HudElement hudElement, RenderContext renderContext, double mouseX, double mouseY) {
      IRenderer2D renderer = hudElement.getRenderer();
      IFontRenderer fr = hudElement.getFontRenderer();
      boolean building = renderer.isBuilding();
      PoseStack matrixStack = renderContext.pose();
      if (!building) {
         renderer.begin(matrixStack, fr);
      }

      int backgroundColor = this.getBackgroundColor(hudElement, mouseX, mouseY).getRGB();
      this.renderHudElementBackground(hudElement, renderContext, renderer, hudElement.getWidth(), hudElement.getHeight(), backgroundColor);
      hudElement.render(renderContext, mouseX, mouseY);
      if (!building) {
         renderer.end();
      }

      if (!hudElement.shouldSkipPostRender()) {
         if (building) {
            renderer.end();
         }

         hudElement.postRender(renderContext, mouseX, mouseY);
         if (building) {
            renderer.begin(matrixStack, fr);
         }
      }
   }

   protected void renderPrimitiveHudElement(HudElement hudElement, RenderContext renderContext, double mouseX, double mouseY) {
      IRenderer2D renderer = hudElement.getRenderer();
      IFontRenderer fr = hudElement.getFontRenderer();
      boolean building = renderer.isBuilding();
      Color backgroundColor = this.getBackgroundColor(hudElement, mouseX, mouseY);
      if (!building) {
         renderer.begin(renderContext.pose(), fr);
      }

      double primitiveWidth = fr.getStringWidth(hudElement.getDisplayName());
      double primitiveHeight = fr.getFontHeight() + 2.0;

      try {
         double elementWidth = hudElement.getWidth();
         double elementHeight = hudElement.getHeight();
         if (elementWidth > primitiveWidth) {
            primitiveWidth = elementWidth;
         }

         if (elementHeight > primitiveHeight) {
            primitiveHeight = elementHeight;
         }
      } catch (Throwable var19) {
      }

      this.renderHudElementBackground(hudElement, renderContext, renderer, primitiveWidth, primitiveHeight, backgroundColor.getRGB());
      fr.drawString(hudElement.getDisplayName(), 0.0, 0.0, -1);
      if (!building) {
         renderer.end();
      }
   }

   protected Color getBackgroundColor(HudElement hudElement, double mouseX, double mouseY) {
      return RusherHackAPI.getHudManager().getBackgroundColor();
   }

   protected void renderHudElementBackground(HudElement hudElement, RenderContext renderContext, IRenderer2D renderer, double width, double height, int color) {
      renderer.drawRectangle(0.0, 0.0, hudElement.getWidth(), hudElement.getHeight(), true, false, 1.0F, color, color);
   }

   public PanelHandlerBase<?> getHudManagerPanel() {
      return RusherHackAPI.getThemeManager().getDefaultTheme().getHudHandler().getHudManagerPanel();
   }

   public boolean isElementHovered(HudElement element, double mouseX, double mouseY) {
      double x = element.getStartX();
      double y = element.getStartY();
      double width = element.getScaledWidth();
      double height = element.getScaledHeight();
      return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
   }

   @Override
   public IFontRenderer getFontRenderer() {
      return RusherHackAPI.fonts().getHudFontRenderer();
   }

   protected boolean shouldRenderPrimitively() {
      return Globals.mc.player == null || Globals.mc.level == null;
   }
}
