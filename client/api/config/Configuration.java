package org.rusherhack.client.api.config;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.core.interfaces.INamed;
import org.rusherhack.core.logging.ILogger;
import org.rusherhack.core.serialize.ISerializable;

public abstract class Configuration implements INamed {
   public static final Path CONFIG_DIRECTORY = RusherHackAPI.getConfigPath();
   public final ILogger logger;
   private final String name;
   private final File file;
   protected int loadPriority;

   public Configuration(File file) {
      this(file.getName().split("\\.")[0], file);
   }

   public Configuration(String name, File file) {
      this.name = name;
      this.file = file;
      this.logger = RusherHackAPI.createLogger("Config/" + name);
   }

   public abstract void write(ISerializable<?> var1);

   public abstract void read(ISerializable<?> var1);

   public File getFile() {
      return this.file;
   }

   protected File createTempFile() {
      try {
         Path tempPath = RusherHackAPI.getPath().resolve("temp");
         tempPath.toFile().mkdirs();
         Path tempFilePath = Files.createTempFile(tempPath, null, ".tmp");
         File tempFile = tempFilePath.toFile();
         tempFile.deleteOnExit();
         return tempFile;
      } catch (Exception e) {
         this.logger.warn("Failed to create temp config file", e);
         return null;
      }
   }

   @Override
   public String getName() {
      return this.name;
   }

   public void setLoadPriority(int loadPriority) {
      this.loadPriority = loadPriority;
   }

   public int getLoadPriority() {
      return this.loadPriority;
   }
}
