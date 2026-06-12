package org.rusherhack.client.api.render.graphic;

import java.io.IOException;
import java.io.InputStream;
import java.lang.StackWalker.Option;

public class TextureGraphic implements IGraphic {
   private final InputStream inputStream;
   private final int u;
   private final int v;
   private final int width;
   private final int height;

   public TextureGraphic(String path, int width, int height) throws IOException {
      this(StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE).getCallerClass().getClassLoader().getResourceAsStream(path), width, height);
   }

   public TextureGraphic(InputStream inputStream, int width, int height) throws IOException {
      if (inputStream == null) {
         throw new IOException("InputStream is null");
      }

      this.inputStream = inputStream;
      this.u = this.v = 0;
      this.width = width;
      this.height = height;
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
      return this.u;
   }

   @Override
   public int getYOffset() {
      return this.v;
   }
}
