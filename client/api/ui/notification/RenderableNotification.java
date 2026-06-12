package org.rusherhack.client.api.ui.notification;

import net.minecraft.network.chat.Component;
import org.rusherhack.client.api.render.IRenderable2D;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.interfaces.IClickable;
import org.rusherhack.core.notification.NotificationType;
import org.rusherhack.core.notification.type.LivingNotification;

public abstract class RenderableNotification extends LivingNotification implements IRenderable2D, IClickable {
   private final Component component;

   public RenderableNotification(String text, NotificationType type) {
      this(Component.literal(text), type);
   }

   public RenderableNotification(String text, NotificationType type, int id) {
      this(Component.literal(text), type, id);
   }

   public RenderableNotification(Component component, NotificationType type) {
      super(ChatUtils.stripFormatting(component.getString()), type);
      this.component = component;
   }

   public RenderableNotification(Component component, NotificationType type, int id) {
      super(ChatUtils.stripFormatting(component.getString()), type, id);
      this.component = component;
   }

   public Component getComponent() {
      return this.component;
   }
}
