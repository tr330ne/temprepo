package org.rusherhack.client.api.accessors.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import java.util.Deque;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PoseStack.class)
public interface IMixinPoseStack {
   @Accessor("poseStack")
   Deque<Pose> getPoseStack();

   @Accessor("poseStack")
   @Mutable
   void setPoseStack(Deque<Pose> var1);
}
