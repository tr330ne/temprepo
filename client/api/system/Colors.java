package org.rusherhack.client.api.system;

import java.awt.Color;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.rusherhack.client.api.setting.ColorSetting;

public interface Colors {
   ColorSetting primaryColor();

   int getRainbowColor(ColorSetting var1, int var2);

   Color getEntityColor(Entity var1, Colors.ColorMode var2);

   Color getBlockEntityColor(BlockEntity var1, Colors.ColorMode var2);

   enum ColorMode {
      STATIC,
      DISTANCE;
   }
}
