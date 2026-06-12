package org.rusherhack.client.api.feature.hud;

import java.awt.Color;
import net.minecraft.network.chat.Component;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.client.api.render.font.IFontRenderer;
import org.rusherhack.client.api.setting.ColorSetting;
import org.rusherhack.core.setting.EnumSetting;
import org.rusherhack.core.utils.ColorUtils;

public abstract class ShortListHudElement extends HudElement {
   public static final Component COMMA_SEPARATOR_COMPONENT = Component.literal(", ");
   public static final double PADDING = 1.0;
   private final ColorSetting color = (new ColorSetting("Color", new Color(255, 255, 255)) {
         @Override
         public Color getValue() {
            return RusherHackAPI.getHudManager().isGlobalColorSynced()
               ? ColorUtils.transparency(RusherHackAPI.getHudManager().getPrimaryColor(), this.getAlpha())
               : super.getValue();
         }

         @Override
         public boolean isThemeSync() {
            return RusherHackAPI.getHudManager().isThemeColorSynced() ? true : super.isThemeSync();
         }
      })
      .setVisibility(() -> !RusherHackAPI.getHudManager().isThemeColorSynced())
      .setAlphaAllowed(false);
   protected final EnumSetting<ShortListHudElement.TextAxis> axis = new EnumSetting<>("Axis", ShortListHudElement.TextAxis.X);
   private Component[] list;
   private double width;
   private double height;

   public ShortListHudElement(String name) {
      super(name);
      this.registerSettings(this.color, this.axis);
   }

   public abstract Component[] getComponents();

   @Override
   public void renderContent(RenderContext context, double mouseX, double mouseY) {
      if (this.list != null) {
         IRenderer2D renderer = this.getRenderer();
         IFontRenderer fr = this.getFontRenderer();
         int color = this.color.getValue().getRGB();
         switch ((ShortListHudElement.TextAxis)this.axis.getValue()) {
            case X:
               double x = 1.0;

               for (int i = 0; i < this.list.length; i++) {
                  Component component = this.list[i];
                  if (component != null) {
                     if (i != 0) {
                        component = COMMA_SEPARATOR_COMPONENT.copy().append(component);
                     }

                     x = fr.drawString(component, x, 1.0, color);
                  }
               }

               this.width = x + 1.0;
               this.height = fr.getFontHeight() + 2.0;
               break;
            case Y:
               HudElement.Alignment alignment = this.getAlignment();
               double x = 1.0;
               double y = 1.0;
               double width = 0.0;

               for (Component s : this.list) {
                  if (s != null) {
                     double strWidth = fr.getStringWidth(s.getString());
                     if (alignment != null) {
                        x = switch (alignment) {
                           case TOP_LEFT, BOTTOM_LEFT -> 1.0;
                           case TOP_RIGHT, BOTTOM_RIGHT -> this.getWidth() - strWidth - 1.0;
                           case TOP_CENTER -> this.getWidth() / 2.0 - strWidth / 2.0;
                        };
                     }

                     fr.drawString(s, x, y, color);
                     y += fr.getFontHeight() + 1.0;
                     width = Math.max(width, strWidth);
                  }
               }

               this.width = width + 2.0;
               this.height = y;
         }
      }
   }

   @Override
   public void tick() {
      this.list = this.getComponents();
   }

   @Override
   public double getWidth() {
      return !(this.width <= 0.0) || mc.player != null && mc.level != null ? this.width : this.getFontRenderer().getStringWidth(this.getDisplayName());
   }

   @Override
   public double getHeight() {
      return !(this.height <= 0.0) || mc.player != null && mc.level != null ? this.height : this.getFontRenderer().getFontHeight() + 2.0;
   }

   @Override
   public boolean shouldSkipPostRender() {
      return true;
   }

   public enum TextAxis {
      X,
      Y;
   }
}
