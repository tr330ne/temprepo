package org.rusherhack.core.event.stage;

public enum Stage {
   PRE(-1),
   ON(0),
   POST(1),
   ALL(-99),
   DEFAULT(-99);

   private final int id;

   Stage(int id) {
      this.id = id;
   }

   public int getId() {
      return this.id;
   }

   public static Stage getStage(int id) {
      return switch (id) {
         case -1 -> PRE;
         case 0 -> ON;
         case 1 -> POST;
         default -> null;
      };
   }
}
