package org.rusherhack.client.api.accessors.gui;

import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BossHealthOverlay.class)
public interface IMixinBossHealthOverlay {
   @Accessor("events")
   Map<UUID, LerpingBossEvent> getEvents();
}
