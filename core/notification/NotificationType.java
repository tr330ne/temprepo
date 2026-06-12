package org.rusherhack.core.notification;

public enum NotificationType {
   INFO(1, 5000L),
   WARNING(2, 7500L),
   ERROR(3, 10000L),
   HINT(4, 5000L),
   DEBUG(256, 5000L);

   private final int typeID;
   private final long lifeSpan;

   NotificationType(int typeID, long lifeSpan) {
      this.typeID = typeID;
      this.lifeSpan = lifeSpan;
   }

   public int getTypeID() {
      return this.typeID;
   }

   public long getLifeSpan() {
      return this.lifeSpan;
   }

   public static NotificationType fromTypeID(int typeID) {
      for (NotificationType type : values()) {
         if (type.getTypeID() == typeID) {
            return type;
         }
      }

      return null;
   }
}
