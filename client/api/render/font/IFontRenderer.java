package org.rusherhack.client.api.render.font;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import org.rusherhack.client.api.render.IRenderer;
import org.rusherhack.client.api.render.IScissorable;

public interface IFontRenderer extends IRenderer, IScissorable {
   default double drawString(String text, double x, double y, int color) {
      return this.drawString(null, text, x, y, color, this.getDefaultShadowState());
   }

   default double drawString(FormattedText text, double x, double y, int color) {
      return this.drawString(null, Language.getInstance().getVisualOrder(text), x, y, color, this.getDefaultShadowState());
   }

   default double drawString(FormattedCharSequence text, double x, double y, int color) {
      return this.drawString(null, text, x, y, color, this.getDefaultShadowState());
   }

   default double drawString(String text, double x, double y, int color, boolean shadow) {
      return this.drawString(null, text, x, y, color, shadow);
   }

   default double drawString(FormattedText text, double x, double y, int color, boolean shadow) {
      return this.drawString(null, Language.getInstance().getVisualOrder(text), x, y, color, shadow);
   }

   default double drawString(FormattedCharSequence text, double x, double y, int color, boolean shadow) {
      return this.drawString(null, text, x, y, color, shadow);
   }

   double drawString(PoseStack var1, String var2, double var3, double var5, int var7, boolean var8);

   double drawString(PoseStack var1, FormattedCharSequence var2, double var3, double var5, int var7, boolean var8);

   default double drawText(String text, double x, double y, int color, double maxWidth, double spacing) {
      return this.drawText(null, text, x, y, color, this.getDefaultShadowState(), maxWidth, spacing);
   }

   default double drawText(String text, double x, double y, int color, boolean shadow, double maxWidth, double spacing) {
      return this.drawText(null, text, x, y, color, shadow, maxWidth, spacing);
   }

   default double drawText(FormattedText text, double x, double y, int color, double maxWidth, double spacing) {
      return this.drawText(null, text, x, y, color, this.getDefaultShadowState(), maxWidth, spacing);
   }

   default double drawText(FormattedText text, double x, double y, int color, boolean shadow, double maxWidth, double spacing) {
      return this.drawText(null, text, x, y, color, shadow, maxWidth, spacing);
   }

   double drawText(PoseStack var1, String var2, double var3, double var5, int var7, boolean var8, double var9, double var11);

   double drawText(PoseStack var1, FormattedText var2, double var3, double var5, int var7, boolean var8, double var9, double var11);

   double getStringWidth(String var1);

   double getFontHeight();

   boolean getDefaultShadowState();

   void setDefaultShadowState(boolean var1);

   default List<String> splitString(String string, double maxWidth) {
      List<String> lines = new ArrayList<>();
      if (!string.contains("\n")) {
         String[] words = string.split(" ");
         StringBuilder currentLine = new StringBuilder();

         for (String word : words) {
            if (this.getStringWidth(currentLine + word) > maxWidth) {
               lines.add(currentLine.toString());
               currentLine = new StringBuilder();
            }

            currentLine.append(word).append(" ");
         }

         lines.add(currentLine.toString());
         return lines;
      } else {
         for (String line : string.split("\n")) {
            lines.addAll(this.splitString(line, maxWidth));
         }

         return lines;
      }
   }

   default String trimStringToWidth(String string, double width) {
      if (this.getStringWidth(string) <= width) {
         return string;
      }

      StringBuilder builder = new StringBuilder();

      for (int i = 0; i < string.length(); i++) {
         char c = string.charAt(i);
         if (this.getStringWidth(builder.toString() + c) > width) {
            return builder.toString();
         }

         builder.append(c);
      }

      return builder.toString();
   }
}
