package org.rusherhack.client.api.accessors.world;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.LevelEntityGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Level.class)
public interface IMixinLevel {
   @Accessor("rainLevel")
   float getRainLevel();

   @Accessor("thunderLevel")
   float getThunderLevel();

   @Invoker("getEntities")
   LevelEntityGetter<Entity> getEntities();
}
