package org.rusherhack.core.utils;

public class Version implements Comparable<Version> {
   private final int major;
   private final int minor;
   private final int patch;

   public Version(int major, int minor, int patch) {
      this.major = major;
      this.minor = minor;
      this.patch = patch;
   }

   public int getMajor() {
      return this.major;
   }

   public int getMinor() {
      return this.minor;
   }

   public int getPatch() {
      return this.patch;
   }

   public String getString(boolean includeNullPatch) {
      StringBuilder builder = new StringBuilder();
      builder.append(this.major);
      builder.append(".");
      builder.append(this.minor);
      if (includeNullPatch || this.patch != 0) {
         builder.append(".");
         builder.append(this.patch);
      }

      return builder.toString();
   }

   @Override
   public String toString() {
      return this.getString(true);
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof Version other) ? false : this.major == other.major && this.minor == other.minor && this.patch == other.patch;
   }

   @Override
   public int hashCode() {
      return this.major * 10000 + this.minor * 100 + this.patch;
   }

   public static Version fromString(String version) {
      String[] split = version.replaceAll("[^0-9.]", "").split("\\.");
      int[] nums = new int[3];

      for (int i = 0; i < split.length; i++) {
         nums[i] = Integer.parseInt(split[i]);
      }

      return new Version(nums[0], nums[1], nums[2]);
   }

   public int compareTo(Version other) {
      if (this.major != other.major) {
         return this.major - other.major;
      } else if (this.minor != other.minor) {
         return this.minor - other.minor;
      } else {
         return this.patch != other.patch ? this.patch - other.patch : 0;
      }
   }
}
