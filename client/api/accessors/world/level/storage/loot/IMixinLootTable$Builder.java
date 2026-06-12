package org.rusherhack.client.api.accessors.world.level.storage.loot;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Builder.class)
public interface IMixinLootTable$Builder {
   @Accessor("pools")
   com.google.common.collect.ImmutableList.Builder<LootPool> getPools();

   @Accessor("functions")
   com.google.common.collect.ImmutableList.Builder<LootItemFunction> getFunctions();
}
