package org.rusherhack.core.logging;

public interface ILogger extends ILog {
   String getName();

   ILogger getParent();
}
