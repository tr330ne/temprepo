package org.rusherhack.core.event;

import org.rusherhack.core.event.stage.Stage;
import org.rusherhack.core.event.type.Event;

public interface IEventBus {
   void subscribe(Object var1);

   void unsubscribe(Object var1);

   void post(Event var1, Stage var2);

   void post(Event var1, Runnable var2, Stage var3);

   void post(Event var1);

   void post(Event var1, Runnable var2);
}
