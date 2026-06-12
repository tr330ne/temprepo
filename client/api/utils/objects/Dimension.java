package org.rusherhack.client.api.utils.objects;

import net.minecraft.core.Holder;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import org.rusherhack.client.api.Globals;

public enum Dimension {
   OVERWORLD("The Overworld"),
   NETHER("The Nether"),
   END("The End");

   private final String displayName;

   Dimension(String displayName) {
      this.displayName = displayName;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public boolean isOverworld() {
      return this == OVERWORLD;
   }

   public boolean isNether() {
      return this == NETHER;
   }

   public boolean isEnd() {
      return this == END;
   }

   public boolean isLoaded() {
      return this.equals(getLoaded());
   }

   public static Dimension getLoaded() {
      if (Globals.mc.level == null) {
         return null;
      } else {
         Holder<DimensionType> dimensionType = Globals.mc.level.dimensionTypeRegistration();
         if (dimensionType.is(BuiltinDimensionTypes.NETHER)) {
            return NETHER;
         } else {
            return dimensionType.is(BuiltinDimensionTypes.END) ? END : OVERWORLD;
         }
      }
   }
}
