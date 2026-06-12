package org.rusherhack.core.command.argument.parser.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.rusherhack.core.command.CommandData;
import org.rusherhack.core.command.argument.parser.IArg;
import org.rusherhack.core.command.exceptions.ArgumentException;
import org.rusherhack.core.command.processing.CommandProcessingSink;
import org.rusherhack.core.utils.ColorUtils;

public class ColorArg implements IArg<Color> {
   public Color parse(CommandProcessingSink context, String arg) throws ArgumentException {
      Color color = ColorUtils.parseColor(arg);
      if (color == null) {
         throw new ArgumentException("Invalid color: " + arg);
      } else {
         return color;
      }
   }

   @Override
   public List<String> getSuggestions(CommandProcessingSink context, CommandData.ArgumentData argument, String typedArgument) {
      List<String> suggestions = new ArrayList<>();

      for (String[] colorAliases : ColorUtils.COLOR_MAP.keySet()) {
         suggestions.add(colorAliases[0]);
      }

      return suggestions;
   }
}
