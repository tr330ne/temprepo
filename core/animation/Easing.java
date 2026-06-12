package org.rusherhack.core.animation;

import java.util.function.Function;

public enum Easing implements Function<Double, Double> {
   LINEAR(x -> x),
   EASE_IN(x -> 1.0 - Math.cos(x * Math.PI / 2.0)),
   EASE_OUT(x -> Math.sin(x * Math.PI / 2.0)),
   EASE_IN_OUT(x -> -(Math.cos(Math.PI * x) - 1.0) / 2.0),
   BEZIER_CURVE(x -> {
      double curve = -1.0 + Math.sqrt(-x + 1.0);
      return curve * curve;
   }),
   SINE_PULSE(x -> (Math.sin((x - 0.5) * Math.PI * 3.0) + 1.0) / 2.0);

   private final Function<Double, Double> function;

   Easing(Function<Double, Double> translator) {
      this.function = translator;
   }

   public Double apply(Double input) {
      return this.function.apply(input);
   }
}
