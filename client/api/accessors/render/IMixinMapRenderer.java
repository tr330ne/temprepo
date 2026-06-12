package org.rusherhack.client.api.accessors.render;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.gui.MapRenderer;
import net.minecraft.client.gui.MapRenderer.MapInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MapRenderer.class)
public interface IMixinMapRenderer {
   @Accessor("maps")
   Int2ObjectMap<MapInstance> getMaps();
}
