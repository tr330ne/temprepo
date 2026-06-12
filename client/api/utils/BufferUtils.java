package org.rusherhack.client.api.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.MemoryUtil.MemoryAllocator;

public class BufferUtils {
   public static final MemoryAllocator ALLOCATOR = MemoryUtil.getAllocator(false);

   public static ByteBuffer create(int size) {
      long l = ALLOCATOR.malloc(size);
      if (l == 0L) {
         throw new OutOfMemoryError("Failed to allocate " + size + " bytes");
      } else {
         return MemoryUtil.memByteBuffer(l, size);
      }
   }

   public static ByteBuffer resize(ByteBuffer buffer, int size) {
      long l = ALLOCATOR.realloc(MemoryUtil.memAddress0(buffer), size);
      if (l == 0L) {
         throw new OutOfMemoryError("Failed to resize buffer from " + buffer.capacity() + " bytes to " + size + " bytes");
      } else {
         return MemoryUtil.memByteBuffer(l, size);
      }
   }

   public static ByteBuffer fileToByteBuffer(File file) throws IOException {
      return inputStreamToByteBuffer(Files.newInputStream(file.toPath()));
   }

   public static ByteBuffer resourceToByteBuffer(ClassLoader cl, String resource) throws IOException {
      InputStream in = cl.getResourceAsStream(resource);
      return inputStreamToByteBuffer(in);
   }

   public static ByteBuffer inputStreamToByteBuffer(InputStream inputStream) throws IOException {
      ByteBuffer data = MemoryUtil.memAlloc(1024);

      int c;
      try (ReadableByteChannel rbc = Channels.newChannel(inputStream)) {
         while ((c = rbc.read(data)) != -1) {
            if (c == 0) {
               data = MemoryUtil.memRealloc(data, data.capacity() * 3 >> 1);
            }
         }
      }

      data.put((byte)0);
      data.flip();
      return data;
   }
}
