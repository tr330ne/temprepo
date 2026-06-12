package org.rusherhack.client.api.feature.hud;

import java.awt.Color;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.client.api.setting.ColorSetting;
import org.rusherhack.core.setting.BooleanSetting;
import org.rusherhack.core.utils.ColorUtils;

public abstract class TextHudElement extends HudElement {
   public static final double PADDING = 1.0;
   private final boolean labeled;
   private double widthCache = 0.0;
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
   private final BooleanSetting labelVisible = new BooleanSetting("Label", true);
   private final ColorSetting labelColor = (new ColorSetting("Color", new Color(0, 100, 255)) {
         @Override
         public Color getValue() {
            return RusherHackAPI.getHudManager().isGlobalColorSynced()
               ? ColorUtils.transparency(RusherHackAPI.getHudManager().getSecondaryColor(), this.getAlpha())
               : super.getValue();
         }

         @Override
         public boolean isThemeSync() {
            return RusherHackAPI.getHudManager().isThemeColorSynced() ? true : super.isThemeSync();
         }
      })
      .setVisibility(() -> !RusherHackAPI.getHudManager().isThemeColorSynced())
      .setAlphaAllowed(false);
   private final BooleanSetting colon = new BooleanSetting("Colon", true);

   public TextHudElement(String name) {
      this(name, true);
   }

   public TextHudElement(String name, boolean labeled) {
      super(name);
      this.labeled = labeled;
      this.registerSettings(this.color);
      if (this.labeled) {
         this.labelVisible.addSubSettings(this.labelColor, this.colon);
         this.registerSettings(this.labelVisible);
      }
   }

   @Override
   public void renderContent(RenderContext context, double mouseX, double mouseY) {
      double x = 1.0;
      double labelWidth = 0.0;
      if (this.labeled && this.labelVisible.getValue()) {
         x = this.getFontRenderer().drawString(this.getLabel() + (this.colon.getValue() ? ": " : " "), x, 1.0, this.labelColor.getValueRGB());
      }

      String text = this.getText();
      this.widthCache = this.getFontRenderer().drawString(text != null ? text : "null", x, 1.0, this.color.getValueRGB()) + 1.0;
   }

   public String getLabel() {
      return this.getName();
   }

   public abstract String getText();

   @Override
   public double getWidth() {
      return mc.player != null && mc.level != null ? this.widthCache : this.getFontRenderer().getStringWidth(this.getDisplayName());
   }

   @Override
   public double getHeight() {
      return this.getFontRenderer().getFontHeight() + 2.0;
   }

   @Override
   protected boolean shouldUpdateAlignment() {
      return true;
   }

   @Override
   public boolean shouldSkipPostRender() {
      return true;
   }
}
