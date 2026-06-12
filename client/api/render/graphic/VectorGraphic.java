package org.rusherhack.client.api.render.graphic;

import java.io.IOException;
import java.io.InputStream;
import java.lang.StackWalker.Option;

public class VectorGraphic implements IGraphic {
   private final InputStream inputStream;
   private final int width;
   private final int height;
   private int svgWidth;
   private int svgHeight;

   public VectorGraphic(String path, int width, int height) throws IOException {
      this(StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE).getCallerClass().getClassLoader().getResourceAsStream(path), width, height);
   }

   public VectorGraphic(InputStream inputStream, int width, int height) throws IOException {
      if (inputStream == null) {
         throw new IOException("InputStream is null");
      }

      this.inputStream = inputStream;
      this.width = this.svgWidth = width;
      this.height = this.svgHeight = height;
   }

   @Override
   public InputStream getInputStream() {
      return this.inputStream;
   }

   @Override
   public int getWidth() {
      return this.width;
   }

   @Override
   public int getHeight() {
      return this.height;
   }

   @Override
   public int getXOffset() {
      return 0;
   }

   @Override
   public int getYOffset() {
      return 0;
   }

   public int getSvgWidth() {
      return this.svgWidth;
   }

   public int getSvgHeight() {
      return this.svgHeight;
   }

   public void setSvgWidth(int svgWidth) {
      this.svgWidth = svgWidth;
   }

   public void setSvgHeight(int svgHeight) {
      this.svgHeight = svgHeight;
   }
}
