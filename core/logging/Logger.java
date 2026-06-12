package org.rusherhack.core.logging;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.rusherhack.core.logging.output.FileOutput;

public abstract class Logger implements ILogger {
   private final String name;
   private final ILogger parentLogger;
   protected final List<ILog> outputs = new ArrayList<>();

   public Logger(String name) {
      this(name, (Path)null);
   }

   public Logger(String name, Path logDirectory) {
      this(name, null, logDirectory);
   }

   public Logger(String name, ILogger parentLogger) {
      this(name, parentLogger, null);
   }

   public Logger(String name, ILogger parentLogger, Path logDirectory) {
      this.name = name;
      this.parentLogger = parentLogger;
      if (logDirectory != null) {
         this.outputs.add(new FileOutput(logDirectory));
      }
   }

   @Override
   public void info(String message) {
      if (this.parentLogger != null) {
         this.parentLogger.info(String.format("[%s] %s", this.getName(), message));
      } else {
         this.outputs.forEach(output -> output.info(message));
      }
   }

   @Override
   public void warn(String message) {
      if (this.parentLogger != null) {
         this.parentLogger.warn(String.format("[%s] %s", this.getName(), message));
      } else {
         this.outputs.forEach(output -> output.warn(message));
      }
   }

   @Override
   public void error(String message) {
      if (this.parentLogger != null) {
         this.parentLogger.error(String.format("[%s] %s", this.getName(), message));
      } else {
         this.outputs.forEach(output -> output.error(message));
      }
   }

   @Override
   public void debug(String message) {
      if (this.parentLogger != null) {
         this.parentLogger.debug(String.format("[%s] %s", this.getName(), message));
      } else {
         this.outputs.forEach(output -> output.debug(message));
      }
   }

   @Deprecated
   public void log(String msg) {
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public ILogger getParent() {
      return this.parentLogger;
   }
}
