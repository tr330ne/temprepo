package org.rusherhack.client.api.accessors.world.level.storage.loot.entries;

import java.util.List;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootPoolSingletonContainer.class)
public interface IMixinLootPoolSingletonContainer {
   @Accessor("functions")
   List<LootItemFunction> getFunctions();
}
