package org.rusherhack.core.interfaces;

public interface IToggleable {
   void toggle();

   void onEnable();

   void onDisable();

   boolean isToggled();

   void setToggled(boolean var1);
}
