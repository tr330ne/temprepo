package org.rusherhack.client.api.accessors.gui;

import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.ResultContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InventoryMenu.class)
public interface IMixinInventoryMenu {
   @Accessor("craftSlots")
   CraftingContainer getCraftSlots();

   @Accessor("resultSlots")
   ResultContainer getResultSlots();
}
