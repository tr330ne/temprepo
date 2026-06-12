package org.rusherhack.client.api.feature.command.arg;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public record EnchantReference(ResourceKey<Enchantment>[] enchantments, int[] levels) {
   public EnchantReference(ResourceKey<Enchantment> enchantment, int level) {
      this(new ResourceKey[]{enchantment}, new int[]{level});
   }
}
