package org.rusherhack.core.logging.output;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.rusherhack.core.logging.ILog;
import org.rusherhack.core.utils.IOUtils;

public class FileOutput implements ILog {
   public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   private File file = null;

   public FileOutput(Path path) {
      this.createNewFile(path);
   }

   public FileOutput(File file) {
      this.file = file;
   }

   @Override
   public void info(String message) {
      this.logToFile("INFO", message);
   }

   @Override
   public void warn(String message) {
      this.logToFile("WARN", message);
   }

   @Override
   public void error(String message) {
      this.logToFile("ERROR", message);
   }

   @Override
   public void debug(String message) {
      this.logToFile("DEBUG", message);
   }

   private void logToFile(String prefix, String message) {
      if (this.file != null) {
         if (!this.file.exists()) {
            this.createNewFile(this.file.getParentFile().toPath());
         }

         if (prefix != null && !prefix.isEmpty()) {
            message = String.format("[%s] %s", prefix, message);
         }

         Date date = Date.from(Instant.now());
         message = String.format("[%s] %s", this.getTimestampFormat().format(date), message);

         try {
            IOUtils.writeStringToFile(this.file, message, true, false);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   public SimpleDateFormat getTimestampFormat() {
      return DATE_FORMAT;
   }

   private void createNewFile(Path logDirectory) {
      File dir;
      if (logDirectory != null && ((dir = logDirectory.toFile()).exists() || dir.mkdirs())) {
         String fileName = "latest.log";
         File file = new File(dir, "latest.log");
         if (file.exists()) {
            String newFileName = String.format("%s.log", file.lastModified());
            File newFile = new File(dir, newFileName);
            if (file.renameTo(newFile) && IOUtils.createFile(file)) {
               this.file = file;
            }
         } else if (IOUtils.createFile(file)) {
            this.file = file;
         }

         File archiveDir = new File(dir, "archive");
         if (archiveDir.exists() || archiveDir.mkdirs()) {
            for (File f : dir.listFiles()) {
               long timeSinceModified = System.currentTimeMillis() - f.lastModified();
               if (f.getName().endsWith(".log") && timeSinceModified > TimeUnit.DAYS.toMillis(5L)) {
                  f.renameTo(new File(archiveDir, f.getName()));
               }
            }
         }
      }
   }
}
