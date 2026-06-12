package org.rusherhack.client.api.events.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.world.level.Level;
import org.rusherhack.client.api.Globals;
import org.rusherhack.client.api.render.IRenderer3D;

public class EventRender3D extends EventRender {
   private final IRenderer3D renderer;
   private final Camera camera;

   public EventRender3D(IRenderer3D renderer, PoseStack poseStack, Camera camera, float partialTicks) {
      super(poseStack, partialTicks);
      this.renderer = renderer;
      this.camera = camera;
   }

   public IRenderer3D getRenderer() {
      return this.renderer;
   }

   public Camera getCamera() {
      return this.camera;
   }

   public Level getLevel() {
      return Globals.mc.level;
   }
}
