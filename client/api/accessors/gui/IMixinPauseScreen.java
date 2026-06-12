package org.rusherhack.client.api.accessors.gui;

import net.minecraft.client.gui.screens.PauseScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PauseScreen.class)
public interface IMixinPauseScreen {
   @Invoker("onDisconnect")
   void disconnect();
}
