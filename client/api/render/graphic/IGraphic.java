package org.rusherhack.client.api.render.graphic;

import java.io.InputStream;

public interface IGraphic {
   InputStream getInputStream();

   int getWidth();

   int getHeight();

   int getXOffset();

   int getYOffset();
}
