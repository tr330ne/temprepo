package org.rusherhack.core.bind;

public interface IBindable {
   default String getBindReference() {
      return "unknown";
   }

   void onKeybindEvent();
}
