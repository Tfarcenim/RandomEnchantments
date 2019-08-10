package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.ETERNAL;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)

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
    return Config.ServerConfig.eternal.get() != DISABLED;
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.eternal.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.eternal.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.eternal.get() == NORMAL;
  }

  @SubscribeEvent
  public static void itemDespawn(ItemExpireEvent event) {
    ItemEntity entityItem = event.getEntityItem();
    ItemStack stack = entityItem.getItem();
    if (EnchantUtils.hasEnch(stack, ETERNAL)) {
      event.setExtraLife(Integer.MAX_VALUE);
      event.setCanceled(true);
    }
  }
}

