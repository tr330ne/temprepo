package org.rusherhack.client.api.utils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.ItemAttributeModifiers.Entry;
import net.minecraft.world.level.block.state.BlockState;

public class ItemUtils {
   public static float getDurability(ItemStack stack) {
      return (float)(stack.getMaxDamage() - stack.getDamageValue()) / stack.getMaxDamage();
   }

   public static boolean isArmor(ItemStack stack) {
      return stack.getItem() instanceof ArmorItem;
   }

   public static boolean isElytra(ItemStack stack) {
      return stack.getItem() instanceof ElytraItem;
   }

   public static boolean isGlideable(ItemStack stack) {
      return isElytra(stack) && ElytraItem.isFlyEnabled(stack);
   }

   public static boolean isItemEquipableInSlot(ItemStack stack, EquipmentSlot slot) {
      return stack.getItem() instanceof Equipable equipable && equipable.getEquipmentSlot().equals(slot);
   }

   public static boolean isMeleeWeapon(ItemStack stack) {
      return isMeleeWeapon(stack, true, true, true, true);
   }

   public static boolean isMeleeWeapon(ItemStack stack, boolean includeSwords, boolean includeAxe, boolean includeTrident, boolean includeMace) {
      return includeSwords && isSword(stack) || includeAxe && isAxe(stack) || includeTrident && isTrident(stack) || includeMace && isMace(stack);
   }

   public static boolean isRangedWeapon(ItemStack stack) {
      return isRangedWeapon(stack, true, true, true);
   }

   public static boolean isRangedWeapon(ItemStack stack, boolean includeBow, boolean includeCrossbow, boolean includeTrident) {
      return includeBow && stack.is(Items.BOW) || includeCrossbow && stack.is(Items.CROSSBOW) || includeTrident && isTrident(stack);
   }

   public static boolean isSword(ItemStack stack) {
      return stack.is(ItemTags.SWORDS);
   }

   public static boolean isAxe(ItemStack stack) {
      return stack.is(ItemTags.AXES);
   }

   public static boolean isTrident(ItemStack stack) {
      return stack.is(Items.TRIDENT);
   }

   public static boolean isMace(ItemStack stack) {
      return stack.is(Items.MACE);
   }

   public static boolean isTool(ItemStack stack) {
      return stack.getItem() instanceof DiggerItem;
   }

   public static boolean isCorrectToolForDrops(ItemStack stack, BlockState blockstate) {
      return stack.getItem() instanceof DiggerItem digger && digger.isCorrectToolForDrops(stack, blockstate);
   }

   public static boolean isPickaxe(ItemStack stack) {
      return stack.is(ItemTags.PICKAXES);
   }

   public static Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
      HashMultimap<Holder<Attribute>, AttributeModifier> map = HashMultimap.create();
      ItemAttributeModifiers attributeModifiers = (ItemAttributeModifiers)stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);

      for (Entry entry : attributeModifiers.modifiers()) {
         if (entry.slot().test(slot)) {
            map.put(entry.attribute(), entry.modifier());
         }
      }

      return map;
   }

   public static Collection<AttributeModifier> getAttributeModifiers(ItemStack stack, Holder<Attribute> attribute, EquipmentSlot slot) {
      List<AttributeModifier> modifiers = new ArrayList<>();
      ItemAttributeModifiers itemAttributeModifiers = (ItemAttributeModifiers)stack.getOrDefault(
         DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY
      );

      for (Entry entry : itemAttributeModifiers.modifiers()) {
         if (entry.slot().test(slot) && entry.attribute().equals(attribute)) {
            modifiers.add(entry.modifier());
         }
      }

      return modifiers;
   }

   public static List<ItemStack> getContainerItemsFromStack(ItemStack stack) {
      if (stack.has(DataComponents.CONTAINER)) {
         ItemContainerContents itemContainerContents = (ItemContainerContents)stack.get(DataComponents.CONTAINER);
         NonNullList<ItemStack> stacks = NonNullList.withSize(itemContainerContents.items.size(), ItemStack.EMPTY);
         itemContainerContents.copyInto(stacks);
         return stacks;
      } else {
         return Collections.emptyList();
      }
   }
}
