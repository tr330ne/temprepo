package org.rusherhack.core.utils;

import java.awt.Color;
import java.util.HashMap;

public class ColorUtils {
   public static final HashMap<String[], Color> COLOR_MAP = new HashMap<>();

   public static Color getRainbow(long timeOffsetMs, float speed, float saturation, float brightness) {
      return new Color(getRainbowRGB(timeOffsetMs, speed, saturation, brightness));
   }

   public static int getRainbowRGB(long timeOffsetMs, float speed, float saturation, float brightness) {
      long time = System.currentTimeMillis() + timeOffsetMs;
      float divisor = Math.max(360.0F / (0.05F * speed), 1.0F);
      float hue = (float)(time % (int)divisor) / divisor;
      return Color.HSBtoRGB(hue, saturation, brightness);
   }

   public static Color getGradient(Color[] colors, long timeOffsetMs, float speed) {
      int[] colorsRGB = new int[colors.length];

      for (int i = 0; i < colors.length; i++) {
         colorsRGB[i] = colors[i].getRGB();
      }

      return new Color(getGradientRGB(colorsRGB, timeOffsetMs, speed));
   }

   public static int getGradientRGB(Color[] colors, long timeOffsetMs, float speed) {
      int[] colorsRGB = new int[colors.length];

      for (int i = 0; i < colors.length; i++) {
         colorsRGB[i] = colors[i].getRGB();
      }

      return getGradientRGB(colorsRGB, timeOffsetMs, speed);
   }

   public static int getGradientRGB(int[] colors, long timeOffsetMs, float speed) {
      long time = System.currentTimeMillis() + timeOffsetMs;
      float divisor = Math.max(360.0F / (0.05F * speed), 1.0F);
      float progress = (float)(time % (int)divisor) / divisor * colors.length;
      int index = (int)progress;
      float remain = progress - index;
      int color1 = colors[index];
      int color2 = colors[(index + 1) % colors.length];
      return interpolateColor(color1, color2, remain);
   }

   public static int blendColors(int[] colors, float fraction) {
      float progress = fraction * colors.length;
      int index = (int)progress;
      float remain = progress - index;
      int color1 = colors[Math.min(index, colors.length - 1)];
      int color2 = colors[(index + 1) % colors.length];
      return interpolateColor(color1, color2, remain);
   }

   public static Color transparency(Color c, int alpha) {
      return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
   }

   public static Color transparency(Color c, float alpha) {
      return new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)(alpha * 255.0F));
   }

   public static int transparency(int c, int alpha) {
      return alpha << 24 | (c >> 16 & 0xFF) << 16 | (c >> 8 & 0xFF) << 8 | c & 0xFF;
   }

   public static int transparency(int c, float alpha) {
      return transparency(c, (int)(alpha * 255.0F));
   }

   public static int brightness(int c, float factor) {
      float[] hsb = Color.RGBtoHSB(c >> 16 & 0xFF, c >> 8 & 0xFF, c & 0xFF, null);
      return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2] * factor);
   }

   public static float[] getRGBA(int color) {
      float a = (color >> 24 & 0xFF) / 255.0F;
      float r = (color >> 16 & 0xFF) / 255.0F;
      float g = (color >> 8 & 0xFF) / 255.0F;
      float b = (color & 0xFF) / 255.0F;
      return new float[]{r, g, b, a};
   }

   public static int getHex(int red, int green, int blue, int alpha) {
      return alpha << 24 | red << 16 | green << 8 | blue;
   }

   public static int interpolateColor(int start, int end, double fraction) {
      float[] startRGBA = getRGBA(start);
      float[] endRGBA = getRGBA(end);
      double r = startRGBA[0] + fraction * (endRGBA[0] - startRGBA[0]);
      double g = startRGBA[1] + fraction * (endRGBA[1] - startRGBA[1]);
      double b = startRGBA[2] + fraction * (endRGBA[2] - startRGBA[2]);
      double a = startRGBA[3] + fraction * (endRGBA[3] - startRGBA[3]);
      r *= 255.0;
      g *= 255.0;
      b *= 255.0;
      a *= 255.0;
      return (int)(Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b));
   }

   public static Color parseColor(String col) {
      Color color = null;

      for (String[] colorAliases : COLOR_MAP.keySet()) {
         for (String colorName : colorAliases) {
            if (colorName.equalsIgnoreCase(col)) {
               color = COLOR_MAP.get(colorAliases);
               break;
            }
         }
      }

      if (color == null) {
         boolean hasAlpha = true;
         if (col.startsWith("#") && col.length() == 7) {
            hasAlpha = false;
         } else if (col.toLowerCase().startsWith("0x") && col.length() == 8) {
            hasAlpha = false;
         }

         try {
            color = new Color(Long.decode(col).intValue(), hasAlpha);
         } catch (NumberFormatException var8) {
         }
      }

      return color;
   }

   static {
      COLOR_MAP.put(new String[]{"black"}, Color.BLACK);
      COLOR_MAP.put(new String[]{"blue"}, Color.BLUE);
      COLOR_MAP.put(new String[]{"cyan"}, Color.CYAN);
      COLOR_MAP.put(new String[]{"darkgray", "darkgrey"}, Color.DARK_GRAY);
      COLOR_MAP.put(new String[]{"gray", "grey"}, Color.GRAY);
      COLOR_MAP.put(new String[]{"green"}, Color.GREEN);
      COLOR_MAP.put(new String[]{"yellow"}, Color.YELLOW);
      COLOR_MAP.put(new String[]{"lightgray"}, Color.LIGHT_GRAY);
      COLOR_MAP.put(new String[]{"magenta"}, Color.MAGENTA);
      COLOR_MAP.put(new String[]{"purple"}, new Color(8323327));
      COLOR_MAP.put(new String[]{"orange"}, Color.ORANGE);
      COLOR_MAP.put(new String[]{"pink"}, Color.PINK);
      COLOR_MAP.put(new String[]{"red"}, Color.RED);
      COLOR_MAP.put(new String[]{"white"}, Color.WHITE);
   }
}
