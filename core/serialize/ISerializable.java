package org.rusherhack.core.serialize;

public interface ISerializable<T> {
   T serialize();

   boolean deserialize(T var1);

   default boolean shouldSerialize(boolean autosave) {
      return true;
   }

   @Deprecated(forRemoval = true)
   default boolean shouldAutoSave() {
      return true;
   }
}
