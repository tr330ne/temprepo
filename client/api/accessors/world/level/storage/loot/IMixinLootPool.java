package org.rusherhack.client.api.accessors.world.level.storage.loot;

import java.util.List;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootPool.class)
public interface IMixinLootPool {
   @Accessor("entries")
   List<LootPoolEntryContainer> getEntries();

   @Accessor("conditions")
   List<LootItemCondition> getConditions();
}
