package org.rusherhack.client.api.accessors.world.level.storage.loot.entries;

import java.util.List;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootPoolEntryContainer.class)
public interface IMixinLootPoolEntryContainer {
   @Accessor("conditions")
   List<LootItemCondition> getConditions();
}
