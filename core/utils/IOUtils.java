package org.rusherhack.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

public class IOUtils {
   public static final SimpleDateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   public static String toString(InputStream stream, boolean newLines) throws IOException {
      StringBuilder resultStringBuilder = new StringBuilder();

      String line;
      try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
         while ((line = br.readLine()) != null) {
            resultStringBuilder.append(line);
            if (newLines) {
               resultStringBuilder.append("\n");
            }
         }
      }

      return resultStringBuilder.toString();
   }

   public static String readStringFromFile(File file, boolean newLines) throws IOException {
      try (FileInputStream stream = new FileInputStream(file)) {
         return toString(stream, newLines);
      }
   }

   public static void writeStringToFile(File file, String message, boolean lineSeparator, boolean clean) throws IOException {
      if (lineSeparator) {
         message = message + System.lineSeparator();
      }

      try (FileWriter writer = new FileWriter(file, !clean)) {
         writer.write(message);
         writer.flush();
      }
   }

   public static void writeToFile(File file, InputStream inputStream) throws IOException {
      try (FileOutputStream fos = new FileOutputStream(file)) {
         fos.write(inputStream.readAllBytes());
      }
   }

   public static boolean createFile(File file) {
      try {
         file.getParentFile().mkdirs();
         return file.createNewFile();
      } catch (IOException e) {
         e.printStackTrace();
         return false;
      }
   }
}
