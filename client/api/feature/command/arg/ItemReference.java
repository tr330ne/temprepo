package org.rusherhack.client.api.feature.command.arg;

import net.minecraft.world.item.Item;

public record ItemReference(Item[] items, String description) {
   public ItemReference(Item item) {
      this(new Item[]{item}, item.getDescriptionId());
   }
}
