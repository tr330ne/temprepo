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

public interface IRusherHack {
   IEventBus getEventBus();

   IConfigManager getConfigManager();

   IRusherHackRegistry getRegistry();

   IFieldRegistry getFieldRegistry();

   IFeatureManager<IModule> getModuleManager();

   ICommandManager getCommandManager();

   IHudManager getHudManager();

   IWindowManager getWindowManager();

   IThemeManager getThemeManager();

   IBindManager getBindManager();

   IRelationManager getRelationManager();

   IWaypointManager getWaypointManager();

   IRotationManager getRotationManager();

   INotificationManager getNotificationManager();

   IChunkProcessor getChunkProcessor();

   IServerState getServerState();

   IInteractions interactions();

   ILogger createLogger(String var1);

   String getVersion();

   Path getPath();

   IRenderer2D getRenderer2D();

   IRenderer3D getRenderer3D();

   Fonts fonts();

   Colors colors();

   Entities entities();

   Language getLanguage();
}
