package org.rusherhack.client.api.accessors.gui;

import java.util.Comparator;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerTabOverlay.class)
public interface IMixinPlayerTabOverlay {
   @Accessor("PLAYER_COMPARATOR")
   static Comparator<PlayerInfo> getPlayerComparator() {
      return null;
   }
}
