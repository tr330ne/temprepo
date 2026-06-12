package org.rusherhack.client.api.utils;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.rusherhack.client.api.Globals;

public class ContainerUtils implements Globals {
   @Nullable
   public static AbstractContainerMenu getContainerMenu() {
      return getContainerMenuFromScreen(mc.screen);
   }

   @Nullable
   public static AbstractContainerMenu getContainerMenuFromScreen(Screen screen) {
      if (mc.player == null || screen == null) {
         return null;
      } else if (screen instanceof AbstractContainerScreen<?> containerScreen) {
         return !mc.player.hasContainerOpen() ? null : containerScreen.getMenu();
      } else {
         return null;
      }
   }

   public static int findItem(AbstractContainerMenu menu, Item item) {
      return findItem(menu, stack -> stack.is(item), null);
   }

   public static int findItem(AbstractContainerMenu menu, Predicate<ItemStack> predicate) {
      return findItem(menu, predicate, null);
   }

   public static int findItem(AbstractContainerMenu menu, Predicate<ItemStack> predicate, Comparator<ItemStack> comparator) {
      AtomicReference<ItemStack> s = new AtomicReference<>();
      AtomicInteger i = new AtomicInteger(-1);
      loopContainer(menu, (slot, stack) -> {
         if (predicate.test(stack)) {
            if (comparator == null) {
               i.set(slot);
               return true;
            }

            if (i.get() == -1 || comparator.compare(stack, s.get()) > 0) {
               s.set(stack);
               i.set(slot);
            }
         }

         return false;
      });
      return i.get();
   }

   public static void loopContainer(AbstractContainerMenu menu, BiFunction<Integer, ItemStack, Boolean> function) {
      for (int i = 0; i < getMenuSize(menu); i++) {
         ItemStack stack = menu.getSlot(i).getItem();
         if (function.apply(i, stack)) {
            return;
         }
      }
   }

   public static int getMenuSize(AbstractContainerMenu menu) {
      if (menu == null) {
         return 0;
      } else {
         return menu instanceof InventoryMenu ? menu.slots.size() : menu.slots.size() - 36;
      }
   }

   public static boolean isContainerEmpty(AbstractContainerMenu menu) {
      AtomicBoolean empty = new AtomicBoolean(true);
      loopContainer(menu, (slot, stack) -> {
         if (!stack.isEmpty()) {
            empty.set(false);
            return true;
         } else {
            return false;
         }
      });
      return empty.get();
   }

   public static boolean isContainerFull(AbstractContainerMenu menu) {
      return findItem(menu, ItemStack::isEmpty) == -1;
   }
}
