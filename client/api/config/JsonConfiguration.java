package org.rusherhack.client.api.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.rusherhack.core.serialize.ISerializable;
import org.rusherhack.core.serialize.JsonSerializable;

public class JsonConfiguration extends Configuration {
   public static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

   public JsonConfiguration(String name, File file) {
      super(name, file);
   }

   public JsonConfiguration(File file) {
      super(file);
   }

   public static JsonConfiguration createConfiguration(String name) {
      return new JsonConfiguration(name, new File(CONFIG_DIRECTORY.toFile(), name + ".json"));
   }

   public static JsonConfiguration createConfiguration(String parentDirectory, String name) {
      return new JsonConfiguration(parentDirectory + "/" + name, new File(CONFIG_DIRECTORY.resolve(parentDirectory).toFile(), name + ".json"));
   }

   @Override
   public void write(ISerializable<?> serializable) {
      if (serializable instanceof JsonSerializable jsonSerializable) {
         File file = this.getFile();
         if (!file.exists()) {
            file.getParentFile().mkdirs();
         }

         File tempFile = this.createTempFile();
         if (tempFile == null) {
            this.logger.error("Could not create temp file for " + file.getName());
            return;
         }

         try {
            JsonWriter writer = GSON.newJsonWriter(new FileWriter(tempFile));

            try {
               GSON.toJson(jsonSerializable.serialize(), writer);
            } catch (Throwable var11) {
               if (writer != null) {
                  try {
                     writer.close();
                  } catch (Throwable var8) {
                     var11.addSuppressed(var8);
                  }
               }

               throw var11;
            }

            if (writer != null) {
               writer.close();
            }
         } catch (Exception e) {
            this.logger.error("Failed to write temp config file: {0}", file.getName(), e);
            return;
         }

         try {
            try {
               Files.move(tempFile.toPath(), file.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException e) {
               Files.move(tempFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
         } catch (Exception e) {
            this.logger.error("Failed to write config file: {0}", file.getName(), e);
         }

         tempFile.delete();
      }
   }

   @Override
   public void read(ISerializable<?> serializable) {
      if (serializable instanceof JsonSerializable jsonSerializable) {
         File file = this.getFile();
         if (!file.exists()) {
            this.write(serializable);
         }

         try {
            JsonReader reader = GSON.newJsonReader(new FileReader(file));

            try {
               jsonSerializable.deserialize(JsonParser.parseReader(reader));
            } catch (Throwable var8) {
               if (reader != null) {
                  try {
                     reader.close();
                  } catch (Throwable var7) {
                     var8.addSuppressed(var7);
                  }
               }

               throw var8;
            }

            if (reader != null) {
               reader.close();
            }
         } catch (Exception e) {
            this.logger.error("Failed to read config file: {0}", file.getName(), e);
         }
      }
   }
}
