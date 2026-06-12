package org.rusherhack.client.api.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.core.setting.Setting;
import org.rusherhack.core.utils.ColorUtils;

public class ColorSetting extends Setting<Color> {
   private boolean rainbow = false;
   private boolean hasRainbowOption = true;
   private ColorSetting.RainbowMode rainbowMode = ColorSetting.RainbowMode.DEFAULT;
   private int rainbowOffset = 0;
   private boolean hasAlpha = true;
   private boolean themeSync = false;
   private boolean themeSyncAllowed = true;

   public ColorSetting(String name, Color value) {
      this(name, "", value);
   }

   public ColorSetting(String name, String description, int value) {
      this(name, description, new Color(value));
   }

   public ColorSetting(String name, String description, Color value) {
      super(name, description, value);
   }

   public Color getRealValue() {
      return (Color)super.getValue();
   }

   public Color getValue() {
      if (this.isThemeSync()) {
         return ColorUtils.transparency(RusherHackAPI.colors().primaryColor().getValue(), this.getAlpha());
      } else {
         return this.isRainbow() ? new Color(this.getRainbowWithOffset(this.rainbowOffset), true) : (Color)super.getValue();
      }
   }

   @Override
   public String getDisplayValue() {
      return this.isRainbow() ? "Rainbow" : Integer.toHexString(this.getValue().getRGB());
   }

   public int getRainbowWithOffset(int offset) {
      int color;
      if (this.isThemeSync()) {
         color = RusherHackAPI.colors().primaryColor().getRainbowWithOffset(offset);
      } else {
         color = RusherHackAPI.colors().getRainbowColor(this, offset);
      }

      return ColorUtils.transparency(color, this.getAlpha());
   }

   public int getValueRGB() {
      return this.getValue().getRGB();
   }

   public boolean isRainbow() {
      return this.isThemeSync() ? RusherHackAPI.colors().primaryColor().isRainbow() : this.hasRainbowOption && this.rainbow;
   }

   public ColorSetting setRainbow(boolean rainbow) {
      this.rainbow = rainbow;
      return this;
   }

   public boolean isRainbowAllowed() {
      return this.hasRainbowOption;
   }

   public ColorSetting setRainbowAllowed(boolean hasRainbowOption) {
      this.hasRainbowOption = hasRainbowOption;
      return this;
   }

   public ColorSetting.RainbowMode getRainbowMode() {
      return this.rainbowMode;
   }

   public void setRainbowMode(ColorSetting.RainbowMode rainbowMode) {
      this.rainbowMode = rainbowMode;
   }

   public int getRainbowOffset() {
      return this.rainbowOffset;
   }

   public void setRainbowOffset(int rainbowOffset) {
      this.rainbowOffset = rainbowOffset;
   }

   public boolean isAlphaAllowed() {
      return this.hasAlpha;
   }

   public ColorSetting setAlphaAllowed(boolean hasAlpha) {
      this.hasAlpha = hasAlpha;
      return this;
   }

   public boolean isThemeSync() {
      return this.themeSyncAllowed && this.themeSync;
   }

   public ColorSetting setThemeSync(boolean themeSync) {
      this.themeSync = themeSync;
      return this;
   }

   public boolean isThemeSyncAllowed() {
      return this.themeSyncAllowed;
   }

   public ColorSetting setThemeSyncAllowed(boolean themeSyncAllowed) {
      this.themeSyncAllowed = themeSyncAllowed;
      return this;
   }

   public Color parseValue(String string, boolean set) {
      Color color = ColorUtils.parseColor(string);
      if (color != null && set) {
         this.setValue(color);
      }

      return color;
   }

   @Override
   public boolean deserializeValue(JsonElement json) {
      if (!json.isJsonObject()) {
         return false;
      }

      JsonObject object = json.getAsJsonObject();
      if (!object.has("rgba")) {
         return false;
      }

      int rgba = object.get("rgba").getAsInt();
      this.setValue(new Color(rgba, true));
      if (object.has("themeSync")) {
         this.themeSync = object.get("themeSync").getAsBoolean();
      }

      if (object.has("rainbow")) {
         this.rainbow = object.get("rainbow").getAsBoolean();
      }

      if (object.has("rainbowMode")) {
         this.rainbowMode = ColorSetting.RainbowMode.valueOf(object.get("rainbowMode").getAsString());
      }

      if (object.has("rainbowOffset")) {
         this.rainbowOffset = object.get("rainbowOffset").getAsInt();
      }

      return true;
   }

   @Override
   public JsonElement serializeValue() {
      JsonObject object = new JsonObject();
      object.addProperty("rgba", ((Color)super.getValue()).getRGB());
      object.addProperty("themeSync", this.themeSync);
      object.addProperty("rainbow", this.rainbow);
      object.addProperty("rainbowMode", this.rainbowMode.name());
      object.addProperty("rainbowOffset", this.rainbowOffset);
      return object;
   }

   public int getRed() {
      return this.getValue().getRed();
   }

   public void setRed(int red) {
      this.setValue(new Color(red, ((Color)super.getValue()).getGreen(), ((Color)super.getValue()).getBlue(), this.getAlpha()));
   }

   public int getGreen() {
      return this.getValue().getGreen();
   }

   public void setGreen(int green) {
      this.setValue(new Color(((Color)super.getValue()).getRed(), green, ((Color)super.getValue()).getBlue(), this.getAlpha()));
   }

   public int getBlue() {
      return this.getValue().getBlue();
   }

   public void setBlue(int blue) {
      this.setValue(new Color(((Color)super.getValue()).getRed(), ((Color)super.getValue()).getGreen(), blue, this.getAlpha()));
   }

   public int getAlpha() {
      return this.isAlphaAllowed() ? ((Color)super.getValue()).getAlpha() : 255;
   }

   public void setAlpha(int alpha) {
      if (this.isAlphaAllowed()) {
         this.setValue(ColorUtils.transparency((Color)super.getValue(), alpha));
      }
   }

   @Deprecated
   public void setAlphaAllowed(int alpha) {
      this.setAlpha(alpha);
   }

   public void setHue(float hue) {
      float[] hsb = Color.RGBtoHSB(((Color)super.getValue()).getRed(), ((Color)super.getValue()).getGreen(), ((Color)super.getValue()).getBlue(), null);
      this.setValue(ColorUtils.transparency(Color.getHSBColor(hue, hsb[1], hsb[2]), this.getAlpha()));
   }

   public float getHue() {
      return Color.RGBtoHSB(this.getRed(), this.getGreen(), this.getBlue(), null)[0];
   }

   public float getSaturation() {
      return Color.RGBtoHSB(this.getRed(), this.getGreen(), this.getBlue(), null)[1];
   }

   public float getBrightness() {
      return Color.RGBtoHSB(this.getRed(), this.getGreen(), this.getBlue(), null)[2];
   }

   public ColorSetting setDescription(String description) {
      return (ColorSetting)super.setDescription(description);
   }

   public ColorSetting setVisibility(BooleanSupplier tester) {
      return (ColorSetting)super.setVisibility(tester);
   }

   public ColorSetting onChange(Runnable run) {
      return (ColorSetting)super.onChange(run);
   }

   public ColorSetting onChange(Consumer<Color> consumer) {
      return (ColorSetting)super.onChange(consumer);
   }

   public ColorSetting setHidden(boolean hidden) {
      return (ColorSetting)super.setHidden(hidden);
   }

   public ColorSetting setShouldSerialize(boolean shouldSerialize) {
      return (ColorSetting)super.setShouldSerialize(shouldSerialize);
   }

   public enum RainbowMode {
      DEFAULT,
      RAINBOW,
      GRADIENT;
   }
}
