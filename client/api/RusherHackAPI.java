package org.rusherhack.client.api;

import java.nio.file.Path;
import org.rusherhack.client.api.bind.IBindManager;
import org.rusherhack.client.api.feature.module.IModule;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.render.IRenderer3D;
import org.rusherhack.client.api.system.Colors;
import org.rusherhack.client.api.system.Entities;
import org.rusherhack.client.api.system.Fonts;
import org.rusherhack.client.api.system.IChunkProcessor;
import org.rusherhack.client.api.system.IConfigManager;
import org.rusherhack.client.api.system.IFieldRegistry;
import org.rusherhack.client.api.system.IHudManager;
import org.rusherhack.client.api.system.IInteractions;
import org.rusherhack.client.api.system.INotificationManager;
import org.rusherhack.client.api.system.IRelationManager;
import org.rusherhack.client.api.system.IRotationManager;
import org.rusherhack.client.api.system.IRusherHackRegistry;
import org.rusherhack.client.api.system.IServerState;
import org.rusherhack.client.api.system.IWindowManager;
import org.rusherhack.client.api.system.waypoint.IWaypointManager;
import org.rusherhack.client.api.ui.theme.IThemeManager;
import org.rusherhack.core.command.ICommandManager;
import org.rusherhack.core.event.IEventBus;
import org.rusherhack.core.feature.IFeatureManager;
import org.rusherhack.core.locale.Language;
import org.rusherhack.core.logging.ILogger;
import org.rusherhack.core.notification.NotificationType;

public class RusherHackAPI {
   public static final boolean PLUGINS_ENABLED = System.getProperty("rusherhack.enablePlugins", "false").equals("true");
   private static IRusherHack rusherhack = null;

   public static IEventBus getEventBus() {
      return rusherhack.getEventBus();
   }

   public static ILogger createLogger(String name) {
      return rusherhack.createLogger(name);
   }

   public static String getVersion() {
      return rusherhack.getVersion();
   }

   public static Path getPath() {
      return rusherhack.getPath();
   }

   public static Path getConfigPath() {
      return getPath().resolve("config");
   }

   public static IConfigManager getConfigManager() {
      return rusherhack.getConfigManager();
   }

   public static IRusherHackRegistry getRegistry() {
      return rusherhack.getRegistry();
   }

   public static IFieldRegistry getFieldRegistry() {
      return rusherhack.getFieldRegistry();
   }

   public static IFeatureManager<IModule> getModuleManager() {
      return rusherhack.getModuleManager();
   }

   public static ICommandManager getCommandManager() {
      return rusherhack.getCommandManager();
   }

   public static IHudManager getHudManager() {
      return rusherhack.getHudManager();
   }

   public static IWindowManager getWindowManager() {
      return rusherhack.getWindowManager();
   }

   public static IThemeManager getThemeManager() {
      return rusherhack.getThemeManager();
   }

   public static IBindManager getBindManager() {
      return rusherhack.getBindManager();
   }

   public static IRelationManager getRelationManager() {
      return rusherhack.getRelationManager();
   }

   public static IWaypointManager getWaypointManager() {
      return rusherhack.getWaypointManager();
   }

   public static IRotationManager getRotationManager() {
      return rusherhack.getRotationManager();
   }

   public static INotificationManager getNotificationManager() {
      return rusherhack.getNotificationManager();
   }

   public static IChunkProcessor getChunkProcessor() {
      return rusherhack.getChunkProcessor();
   }

   public static IServerState getServerState() {
      return rusherhack.getServerState();
   }

   public static IInteractions interactions() {
      return rusherhack.interactions();
   }

   public static IRenderer2D getRenderer2D() {
      return rusherhack.getRenderer2D();
   }

   public static IRenderer3D getRenderer3D() {
      return rusherhack.getRenderer3D();
   }

   public static Fonts fonts() {
      return rusherhack.fonts();
   }

   public static Colors colors() {
      return rusherhack.colors();
   }

   public static Entities entities() {
      return rusherhack.entities();
   }

   public static void schedule(Runnable runnable) {
      Globals.mc.tell(runnable);
   }

   public static Language getLanguage() {
      return rusherhack.getLanguage();
   }

   @Deprecated
   public static void sendNotification(NotificationType type, String text) {
      getNotificationManager().send(type, "", text);
   }

   @Deprecated
   public static void sendNotification(NotificationType type, String prefix, String text) {
      getNotificationManager().send(type, prefix, text);
   }
}
