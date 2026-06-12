package org.rusherhack.client.api.render;

import com.mojang.blaze3d.vertex.PoseStack;
import org.rusherhack.client.api.render.font.IFontRenderer;
import org.rusherhack.client.api.render.graphic.IGraphic;

public interface IRenderer2D extends IRenderer, IScissorable {
   @Override
   default void begin(PoseStack matrixStack) {
      this.begin(matrixStack, this.getFontRenderer());
   }

   default void begin(PoseStack matrixStack, IFontRenderer fontRenderer) {
      this.begin(matrixStack);
   }

   IFontRenderer getFontRenderer();

   default void drawRectangle(double x, double y, double width, double height, int color) {
      this.drawRectangle(x, y, width, height, true, false, 0.0F, color, 0);
   }

   void drawGradientRectangle(double var1, double var3, double var5, double var7, double var9, double var11, int var13, int var14);

   default void drawRectangleOutline(double x, double y, double width, double height, float outlineThickness, int color) {
      this.drawRectangle(x, y, width, height, false, true, outlineThickness, 0, color);
   }

   default void drawOutlinedRectangle(double x, double y, double width, double height, float outlineThickness, int color, int outlineColor) {
      this.drawRectangle(x, y, width, height, true, true, outlineThickness, color, outlineColor);
   }

   @Deprecated(forRemoval = true)
   default void _drawRectangle(
      double x, double y, double width, double height, boolean fill, boolean outline, float outlineThickness, int fillColor, int outlineColor
   ) {
      this.drawRectangle(x, y, width, height, fill, outline, outlineThickness, fillColor, outlineColor);
   }

   void drawRectangle(double var1, double var3, double var5, double var7, boolean var9, boolean var10, float var11, int var12, int var13);

   @Deprecated(forRemoval = true)
   default void _drawRoundedRectangle(
      double x, double y, double width, double height, double radius, boolean fill, boolean outline, float outlineThickness, int fillColor, int outlineColor
   ) {
      this.drawRoundedRectangle(x, y, width, height, radius, fill, outline, outlineThickness, fillColor, outlineColor);
   }

   void drawRoundedRectangle(double var1, double var3, double var5, double var7, double var9, boolean var11, boolean var12, float var13, int var14, int var15);

   default void drawTriangle(double x, double y, double size, double theta, int color) {
      this.drawTriangle(x, y, size, theta, true, false, 0.0F, color, 0);
   }

   default void drawTriangleOutline(double x, double y, double size, double theta, float outlineThickness, int color) {
      this.drawTriangle(x, y, size, theta, false, true, outlineThickness, 0, color);
   }

   default void drawOutlinedTriangle(double x, double y, double size, double theta, float outlineThickness, int color, int outlineColor) {
      this.drawTriangle(x, y, size, theta, true, true, outlineThickness, color, outlineColor);
   }

   @Deprecated(forRemoval = true)
   default void _drawTriangle(
      double x, double y, double size, double theta, boolean fill, boolean outline, float outlineThickness, int fillColor, int outlineColor
   ) {
      this.drawTriangle(x, y, size, theta, fill, outline, outlineThickness, fillColor, outlineColor);
   }

   void drawTriangle(double var1, double var3, double var5, double var7, boolean var9, boolean var10, float var11, int var12, int var13);

   default void drawCircle(double x, double y, double radius, int color) {
      this.drawCircle(x, y, radius, true, false, 0.0F, color, 0);
   }

   default void drawCircleOutline(double x, double y, double radius, float outlineThickness, int color) {
      this.drawCircle(x, y, radius, false, true, outlineThickness, 0, color);
   }

   default void drawOutlinedCircle(double x, double y, double radius, float outlineThickness, int color, int outlineColor) {
      this.drawCircle(x, y, radius, true, true, outlineThickness, color, outlineColor);
   }

   @Deprecated(forRemoval = true)
   default void _drawCircle(double x, double y, double radius, boolean fill, boolean outline, float outlineThickness, int fillColor, int outlineColor) {
      this.drawEllipse(x, y, radius, radius, fill, outline, outlineThickness, fillColor, outlineColor);
   }

   default void drawCircle(double x, double y, double radius, boolean fill, boolean outline, float outlineThickness, int fillColor, int outlineColor) {
      this.drawEllipse(x, y, radius, radius, fill, outline, outlineThickness, fillColor, outlineColor);
   }

   @Deprecated(forRemoval = true)
   default void _drawEllipse(
      double x, double y, double radiusX, double radiusY, boolean fill, boolean outline, float outlineThickness, int fillColor, int outlineColor
   ) {
      this.drawEllipse(x, y, radiusX, radiusY, fill, outline, outlineThickness, fillColor, outlineColor);
   }

   void drawEllipse(double var1, double var3, double var5, double var7, boolean var9, boolean var10, float var11, int var12, int var13);

   void drawLine(double var1, double var3, double var5, double var7, float var9, int var10);

   default void drawGraphicRectangle(IGraphic graphic, double x, double y, double width, double height) {
      this.drawGraphicRectangle(graphic, x, y, width, height, 0.0);
   }

   @Deprecated(forRemoval = true)
   default void _drawGraphicRectangle(IGraphic graphic, double x, double y, double width, double height, double roundedRadius) {
      this.drawGraphicRectangle(graphic, x, y, width, height, 1.0F, roundedRadius);
   }

   default void drawGraphicRectangle(IGraphic graphic, double x, double y, double width, double height, double roundedRadius) {
      this.drawGraphicRectangle(graphic, x, y, width, height, 1.0F, roundedRadius);
   }

   void drawGraphicRectangle(IGraphic var1, double var2, double var4, double var6, double var8, float var10, double var11);

   @Deprecated(forRemoval = true)
   default void _drawTextureRectangle(
      int openGlTextureID, int textureWidth, int textureHeight, double x, double y, double width, double height, double roundedRadius
   ) {
      this.drawTextureRectangle(openGlTextureID, textureWidth, textureHeight, x, y, width, height, 1.0F, roundedRadius);
   }

   default void drawTextureRectangle(
      int openGlTextureID, int textureWidth, int textureHeight, double x, double y, double width, double height, double roundedRadius
   ) {
      this.drawTextureRectangle(openGlTextureID, textureWidth, textureHeight, x, y, width, height, 1.0F, roundedRadius);
   }

   void drawTextureRectangle(int var1, int var2, int var3, double var4, double var6, double var8, double var10, float var12, double var13);

   void queue(Runnable var1);

   void flushQueue();
}
