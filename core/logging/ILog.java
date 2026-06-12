package org.rusherhack.core.logging;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public interface ILog {
   String SEPARATOR = "--------------------------------------------------------------";

   void info(String var1);

   void warn(String var1);

   void error(String var1);

   void debug(String var1);

   default void info(String message, Object... args) {
      LogRecord record = constructLogRecord(Level.INFO, message, args);
      this.info(LogFormatter.INSTANCE.format(record));
   }

   default void warn(String message, Object... args) {
      LogRecord record = constructLogRecord(Level.WARNING, message, args);
      this.warn(LogFormatter.INSTANCE.format(record));
   }

   default void error(String message, Object... args) {
      LogRecord record = constructLogRecord(Level.SEVERE, message, args);
      this.error(LogFormatter.INSTANCE.format(record));
   }

   default void debug(String message, Object... args) {
      LogRecord record = constructLogRecord(Level.FINE, message, args);
      this.debug(LogFormatter.INSTANCE.format(record));
   }

   @Deprecated
   default void info(String message, boolean separators) {
      if (separators) {
         this.info("--------------------------------------------------------------");
         this.info(message);
         this.info("--------------------------------------------------------------");
      } else {
         this.info(message);
      }
   }

   @Deprecated
   default void warn(String message, boolean separators) {
      if (separators) {
         this.warn("--------------------------------------------------------------");
         this.warn(message);
         this.warn("--------------------------------------------------------------");
      } else {
         this.warn(message);
      }
   }

   @Deprecated
   default void error(String message, boolean separators) {
      if (separators) {
         this.error("--------------------------------------------------------------");
         this.error(message);
         this.error("--------------------------------------------------------------");
      } else {
         this.error(message);
      }
   }

   @Deprecated
   default void debug(String message, boolean separators) {
      if (separators) {
         this.debug("--------------------------------------------------------------");
         this.debug(message);
         this.debug("--------------------------------------------------------------");
      } else {
         this.debug(message);
      }
   }

   private static LogRecord constructLogRecord(Level level, String message, Object... args) {
      LogRecord record = new LogRecord(level, message);
      if (args.length > 0 && args[args.length - 1] instanceof Throwable) {
         record.setThrown((Throwable)args[args.length - 1]);
         args = Arrays.copyOfRange(args, 0, args.length - 1);
      }

      record.setParameters(args);
      return record;
   }
}
