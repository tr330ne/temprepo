package org.rusherhack.client.api.render;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface IRenderer3D extends IRenderer {
   void drawShape(VoxelShape var1, double var2, double var4, double var6, boolean var8, boolean var9, int var10);

   default void drawLine(Vec3 start, Vec3 end, int color) {
      this.drawLine(start.x, start.y, start.z, end.x, end.y, end.z, color);
   }

   void drawLine(double var1, double var3, double var5, double var7, double var9, double var11, int var13);

   default void lineStrip(Vec3 point, int color) {
      this.lineStrip(point.x, point.y, point.z, color);
   }

   void lineStrip(double var1, double var3, double var5, int var7);

   void drawPlane(double var1, double var3, double var5, double var7, double var9, Direction var11, boolean var12, boolean var13, int var14);

   void drawBox(BlockPos var1, boolean var2, boolean var3, int var4);

   void drawBox(Entity var1, float var2, boolean var3, boolean var4, int var5);

   void drawBox(double var1, double var3, double var5, double var7, double var9, double var11, boolean var13, boolean var14, int var15);

   void setDepthTest(boolean var1);

   void setLineWidth(float var1);

   BufferBuilder getQuadsBuffer();

   BufferBuilder getLinesBuffer();

   BufferBuilder getLineStripBuffer();

   BufferBuilder getTrianglesBuffer();

   BufferBuilder getTriangleFanBuffer();

   Vec2 projectToScreen(Vec3 var1);
}
