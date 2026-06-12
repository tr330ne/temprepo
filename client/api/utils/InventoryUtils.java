package org.rusherhack.client.api.utils;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.rusherhack.client.api.Globals;

public class InventoryUtils implements Globals {
   public static void clickSlot(int slotId, boolean shiftClick) {
      if (mc.player != null && mc.gameMode != null) {
         mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, slotId, 0, shiftClick ? ClickType.QUICK_MOVE : ClickType.PICKUP, mc.player);
         mc.gameMode.tick();
      }
   }

   public static void swapSlots(int inventorySlot, int hotbarSlot) {
      if (mc.player != null && mc.gameMode != null) {
         mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, inventorySlot, hotbarSlot, ClickType.SWAP, mc.player);
         mc.gameMode.tick();
      }
   }

   public static int findItem(Item item, boolean hotbarPriority, boolean includeOffhand) {
      return findItem(stack -> stack.is(item), hotbarPriority, includeOffhand);
   }

   public static int findItem(Predicate<ItemStack> predicate, boolean hotbarPriority, boolean includeOffhand) {
      return findItem(predicate, null, hotbarPriority, includeOffhand);
   }

   public static int findItem(Predicate<ItemStack> predicate, Comparator<ItemStack> comparator, boolean hotbarPriority, boolean includeOffhand) {
      if (predicate.test(mc.player.getMainHandItem())) {
         return getSelectedHotbarSlot() + 36;
      }

      if (includeOffhand && predicate.test(mc.player.getOffhandItem())) {
         return 45;
      }

      AtomicReference<ItemStack> s = new AtomicReference<>();
      AtomicInteger i = new AtomicInteger(-1);
      loopInventory((slot, stack) -> {
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
      }, true, false, true, hotbarPriority);
      return i.get();
   }

   public static int findItemHotbar(Item item) {
      return findItemHotbar(stack -> stack.getItem().equals(item));
   }

   public static int findItemHotbar(Predicate<ItemStack> predicate) {
      return findItemHotbar(predicate, null);
   }

   public static int findItemHotbar(Predicate<ItemStack> predicate, Comparator<ItemStack> comparator) {
      if (predicate.test(mc.player.getMainHandItem())) {
         return getSelectedHotbarSlot();
      }

      AtomicReference<ItemStack> s = new AtomicReference<>();
      AtomicInteger i = new AtomicInteger(-1);
      loopInventory((slot, stack) -> {
         if (predicate.test(stack)) {
            if (comparator == null) {
               i.set(slot - 36);
               return true;
            }

            if (i.get() == -1 || comparator.compare(stack, s.get()) > 0) {
               s.set(stack);
               i.set(slot - 36);
            }
         }

         return false;
      }, false, false, true, false);
      return i.get();
   }

   public static int getItemCount(Item item, boolean hotbarOnly, boolean includeOffhand) {
      return getItemCount(stack -> stack.getItem().equals(item), hotbarOnly, includeOffhand);
   }

   public static int getItemCount(Predicate<ItemStack> predicate, boolean hotbarOnly, boolean includeOffhand) {
      AtomicInteger count = new AtomicInteger(0);
      loopInventory((slot, stack) -> {
         if (predicate.test(stack)) {
            count.addAndGet(stack.getCount());
         }

         return false;
      }, !hotbarOnly, includeOffhand, true, false);
      return count.get();
   }

   public static int getStackCount(Predicate<ItemStack> predicate, boolean hotbarOnly, boolean includeOffhand) {
      AtomicInteger count = new AtomicInteger(0);
      loopInventory((slot, stack) -> {
         if (predicate.test(stack)) {
            count.incrementAndGet();
         }

         return false;
      }, !hotbarOnly, includeOffhand, true, false);
      return count.get();
   }

   public static void loopInventory(
      BiFunction<Integer, ItemStack, Boolean> function, boolean includeInventory, boolean includeOffhand, boolean includeHotbar, boolean hotbarPriority
   ) {
      int minSlot = includeInventory ? 9 : 36;
      int maxSlot = !includeOffhand ? 45 : 46;

      for (int i = hotbarPriority ? maxSlot - 1 : minSlot; hotbarPriority ? i >= minSlot : i < maxSlot; i += hotbarPriority ? -1 : 1) {
         if (includeHotbar || i < 36 || i > 44) {
            ItemStack stack = mc.player.inventoryMenu.getSlot(i).getItem();
            if (function.apply(i, stack)) {
               return;
            }
         }
      }
   }

   public static int getSelectedHotbarSlot() {
      return mc.player.getInventory().selected;
   }

   public static void setHotbarSlot(int slot) {
      mc.player.getInventory().selected = slot;
   }

   public static boolean isInventoryEmpty() {
      AtomicBoolean empty = new AtomicBoolean(true);
      loopInventory((slot, stack) -> {
         if (!stack.isEmpty()) {
            empty.set(false);
            return true;
         } else {
            return false;
         }
      }, true, false, true, false);
      return empty.get();
   }

   public static boolean isInventoryFull() {
      return findItem(ItemStack::isEmpty, false, false) == -1;
   }

   public static int getInventorySlot(EquipmentSlot equipmentSlot) {
      return switch (equipmentSlot) {
         case MAINHAND -> getSelectedHotbarSlot() + 36;
         case OFFHAND -> 45;
         case HEAD -> 5;
         case CHEST -> 6;
         case LEGS -> 7;
         case FEET -> 8;
         default -> -1;
      };
   }

   public static EquipmentSlot getEquipmentSlot(int slot) {
      return switch (slot) {
         case 5 -> EquipmentSlot.HEAD;
         case 6 -> EquipmentSlot.CHEST;
         case 7 -> EquipmentSlot.LEGS;
         case 8 -> EquipmentSlot.FEET;
         case 45 -> EquipmentSlot.OFFHAND;
         default -> EquipmentSlot.MAINHAND;
      };
   }

   public static int invToHotbarSlot(int slot) {
      if (slot == 45) {
         return 40;
      }

      if (InventoryMenu.isHotbarSlot(slot)) {
         return slot % 9;
      }

      return switch (slot) {
         case 5 -> 39;
         case 6 -> 38;
         case 7 -> 37;
         case 8 -> 36;
         default -> slot;
      };
   }

   public static int hotbarToInvSlot(int slot) {
      if (slot == 40) {
         return 45;
      }

      if (Inventory.isHotbarSlot(slot)) {
         return slot + 36;
      }

      return switch (slot) {
         case 36 -> 8;
         case 37 -> 7;
         case 38 -> 6;
         case 39 -> 5;
         default -> slot;
      };
   }
}
