package org.rusherhack.client.api.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.rusherhack.core.serialize.ISerializable;
import org.rusherhack.core.serialize.TextSerializable;
import org.rusherhack.core.utils.IOUtils;

public class TextConfiguration extends Configuration {
   private boolean newLines = false;

   public TextConfiguration(String name, File file) {
      super(name, file);
   }

   public TextConfiguration(File file) {
      super(file);
   }

   public static TextConfiguration createConfiguration(String name) {
      return new TextConfiguration(name, new File(CONFIG_DIRECTORY.toFile(), name + ".txt"));
   }

   public static TextConfiguration createConfiguration(String parentDirectory, String name) {
      return new TextConfiguration(parentDirectory + "/" + name, new File(CONFIG_DIRECTORY.resolve(parentDirectory).toFile(), name + ".txt"));
   }

   @Override
   public void write(ISerializable<?> serializable) {
      if (serializable instanceof TextSerializable textSerializable) {
         File file = this.getFile();
         if (!file.exists()) {
            file.getParentFile().mkdirs();
         }

         File tempFile = this.createTempFile();
         if (tempFile == null) {
            this.logger.error("Could not create temp file for " + file.getName());
            return;
         }

         try (FileWriter writer = new FileWriter(tempFile, false)) {
            writer.write(textSerializable.serialize());
            writer.flush();
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
      if (serializable instanceof TextSerializable textSerializable) {
         File file = this.getFile();
         if (!file.exists()) {
            this.write(serializable);
         }

         try (FileInputStream fis = new FileInputStream(file)) {
            textSerializable.deserialize(IOUtils.toString(fis, this.newLines));
         } catch (Exception e) {
            this.logger.error("Failed to read configuration from file: {0}", file.getName(), e);
         }
      }
   }

   public TextConfiguration allowNewLines() {
      this.newLines = true;
      return this;
   }
}
