package org.rusherhack.client.api.accessors.gui;

import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen.TradeOfferButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MerchantScreen.class)
public interface IMixinMerchantScreen {
   @Accessor("shopItem")
   int getShopItem();

   @Accessor("shopItem")
   void setShopItem(int var1);

   @Accessor("tradeOfferButtons")
   TradeOfferButton[] getTradeOfferButtons();

   @Accessor("scrollOff")
   int getScrollOff();
}
