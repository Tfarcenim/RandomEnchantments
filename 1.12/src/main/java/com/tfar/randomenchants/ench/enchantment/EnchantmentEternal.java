package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.tools;
import static com.tfar.randomenchants.init.ModEnchantment.ETERNAL;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentEternal extends Enchantment {
  public EnchantmentEternal() {
    super(Rarity.VERY_RARE, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("eternal");
    this.setName("eternal");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 15;
  }

  @Override
  public int getMaxEnchantability(int level) {
    return 100;
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
    EntityItem entityItem = event.getEntityItem();
    ItemStack stack = entityItem.getItem();
    if (EnchantmentHelper.getEnchantmentLevel(ETERNAL, stack) > 0) {
      event.setExtraLife(Integer.MAX_VALUE);
      event.setCanceled(true);
    }
  }
}

