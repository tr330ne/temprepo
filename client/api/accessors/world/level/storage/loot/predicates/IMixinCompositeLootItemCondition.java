package org.rusherhack.client.api.accessors.world.level.storage.loot.predicates;

import java.util.List;
import net.minecraft.world.level.storage.loot.predicates.CompositeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CompositeLootItemCondition.class)
public interface IMixinCompositeLootItemCondition {
   @Accessor("terms")
   List<LootItemCondition> getTerms();
}
