package org.rusherhack.client.api.render;

public interface IScissorable {
   void beginScissor();

   void endScissor();

   void scissorBox(double var1, double var3, double var5, double var7);

   void popScissorBox();
}
