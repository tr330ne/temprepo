package org.rusherhack.core.interfaces;

public interface INamed {
   String getName();

   default String getDisplayName() {
      return this.getName();
   }

   default String[] getAliases() {
      String name = this.getName();
      String displayName = this.getDisplayName();
      return name.equals(displayName) ? new String[]{name} : new String[]{name, displayName};
   }
}
