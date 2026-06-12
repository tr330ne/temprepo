package org.rusherhack.client.api.accessors.world;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(InstrumentItem.class)
public interface IMixinInstrumentItem {
   @Invoker("getInstrument")
   Optional<? extends Holder<Instrument>> invokeGetInstrument(ItemStack var1);
}
