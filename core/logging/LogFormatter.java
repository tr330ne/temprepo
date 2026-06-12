package org.rusherhack.core.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class LogFormatter extends Formatter {
   static LogFormatter INSTANCE = new LogFormatter();

   @Override
   public String formatMessage(LogRecord record) {
      return super.formatMessage(record);
   }

   @Override
   public String format(LogRecord record) {
      String message = this.formatMessage(record);
      String throwable = "";
      if (record.getThrown() != null) {
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);
         pw.println();
         record.getThrown().printStackTrace(pw);
         pw.close();
         throwable = sw.toString();
      }

      return String.format("%s%s", message, throwable);
   }
}
