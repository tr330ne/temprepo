package org.rusherhack.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {
   public static double clamp(double num, double min, double max) {
      return num < min ? min : (num > max ? max : num);
   }

   public static float clamp(float num, float min, float max) {
      return num < min ? min : (num > max ? max : num);
   }

   public static long clamp(long num, long min, long max) {
      return num < min ? min : (num > max ? max : num);
   }

   public static int clamp(int num, int min, int max) {
      return num < min ? min : (num > max ? max : num);
   }

   public static double interpolate(double now, double then, double amount) {
      return then + (now - then) * amount;
   }

   public static double round(double num, int places) {
      return places < 0 ? num : new BigDecimal(num).setScale(places, RoundingMode.HALF_UP).doubleValue();
   }

   public static double round(double num, double step) {
      return Math.round(num / step) * step;
   }

   public static float hypot(float a, float b) {
      return (float)Math.sqrt(a * a + b * b);
   }

   public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
      double xDiff = x1 - x2;
      double yDiff = y1 - y2;
      double zDiff = z1 - z2;
      return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
   }

   public static double[] calculateIntersection(double[] line, double[] line2) {
      double a1 = line[3] - line[1];
      double b1 = line[0] - line[2];
      double c1 = a1 * line[0] + b1 * line[1];
      double a2 = line2[3] - line2[1];
      double b2 = line2[0] - line2[2];
      double c2 = a2 * line2[0] + b2 * line2[1];
      double delta = a1 * b2 - a2 * b1;
      return new double[]{(b2 * c1 - b1 * c2) / delta, (a1 * c2 - a2 * c1) / delta};
   }
}
