package org.rusherhack.client.api.accessors.gui;

import java.util.List;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.GuiMessage.Line;
import net.minecraft.client.GuiMessageTag.Icon;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ChatComponent.class)
public interface IMixinChatComponent {
   @Accessor("trimmedMessages")
   List<Line> getTrimmedMessages();

   @Accessor("allMessages")
   List<GuiMessage> getAllMessages();

   @Accessor("chatScrollbarPos")
   int getChatScrollbarPos();

   @Accessor("newMessageSinceScroll")
   boolean getNewMessageSinceScroll();

   @Invoker("getMessageLineIndexAt")
   int invokeGetMessageLineIndexAt(double var1, double var3);

   @Invoker("getMessageEndIndexAt")
   int invokeGetMessageEndIndexAt(double var1, double var3);

   @Invoker("drawTagIcon")
   void invokeDrawTagIcon(GuiGraphics var1, int var2, int var3, Icon var4);

   @Invoker("screenToChatX")
   double invokeScreenToChatX(double var1);

   @Invoker("screenToChatY")
   double invokeScreenToChatY(double var1);

   @Invoker("refreshTrimmedMessages")
   void invokeRefreshTrimmedMessage();
}
