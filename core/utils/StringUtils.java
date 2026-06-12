package org.rusherhack.core.utils;

public class StringUtils {
   public static String toTitleCase(String str) {
      str = str.toLowerCase();
      str = str.replace("_", " ");
      String[] words = str.split(" ");
      StringBuilder sb = new StringBuilder();

      for (String word : words) {
         sb.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
      }

      return sb.toString().trim();
   }

   public static int levenshteinDistance(String string1, String string2) {
      char[] s1 = string1.toCharArray();
      char[] s2 = string2.toCharArray();
      int[] prev = new int[s2.length + 1];
      int j = 0;

      while (j < s2.length + 1) {
         prev[j] = j++;
      }

      for (int i = 1; i < s1.length + 1; i++) {
         int[] curr = new int[s2.length + 1];
         curr[0] = i;

         for (int jx = 1; jx < s2.length + 1; jx++) {
            int d1 = prev[jx] + 1;
            int d2 = curr[jx - 1] + 1;
            int d3 = prev[jx - 1];
            if (s1[i - 1] != s2[jx - 1]) {
               d3++;
            }

            curr[jx] = Math.min(Math.min(d1, d2), d3);
         }

         prev = curr;
      }

      return prev[s2.length];
   }
}
