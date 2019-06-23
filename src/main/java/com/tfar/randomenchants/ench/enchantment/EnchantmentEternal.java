package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.tools;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.ETERNAL;

@Mod.EventBusSubscriber(modid = RandomEnchants.MOD_ID)

public class EnchantmentEternal extends Enchantment {
  public EnchantmentEternal() {
    super(Rarity.VERY_RARE, EnchantmentType.ALL, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("eternal");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 15;
  }

  @Override
  public int getMaxLevel() {
    return 1;
  }

  @Override
  public boolean canApply(ItemStack stack) {
    return tools.enableEternal != DISABLED;
  }

  @Override
  public boolean isTreasureEnchantment() {
    return tools.enableEternal == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return tools.enableEternal != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return tools.enableEternal == NORMAL;
  }

  @SubscribeEvent
  public static void itemDespawn(ItemExpireEvent event) {
    ItemEntity entityItem = event.getEntityItem();
    ItemStack stack = entityItem.getItem();
    if (EnchantmentHelper.getEnchantmentLevel(ETERNAL, stack) > 0) {
      event.setExtraLife(Integer.MAX_VALUE);
      event.setCanceled(true);
    }
  }
}

