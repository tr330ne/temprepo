package org.rusherhack.client.api.accessors.world.level.storage.loot.predicates;

import java.util.List;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemEnchantmentsPredicate.class)
public interface IMixinItemEnchantmentsPredicate {
   @Accessor("enchantments")
   List<EnchantmentPredicate> getEnchantments();
}
