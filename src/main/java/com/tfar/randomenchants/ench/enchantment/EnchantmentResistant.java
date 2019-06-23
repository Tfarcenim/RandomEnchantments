package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nonnull;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.tools;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.RESISTANT;

@Mod.EventBusSubscriber(modid = RandomEnchants.MOD_ID)

public class EnchantmentResistant extends Enchantment {
  public EnchantmentResistant() {
    super(Rarity.RARE, EnchantmentType.ALL, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("resistant");
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
  public boolean canApply(@Nonnull ItemStack stack) {
    return tools.enableResistance != DISABLED;
  }

  @Override
  public boolean isTreasureEnchantment() {
    return tools.enableResistance == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return tools.enableResistance != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return tools.enableResistance == NORMAL;
  }


  @SubscribeEvent
  public static void itemSpawn(ItemTossEvent event) {
    ItemEntity entityItem = event.getEntityItem();
    ItemStack stack = entityItem.getItem();
    if (EnchantUtils.hasEnch(stack,RESISTANT)) {
      ObfuscationReflectionHelper.setPrivateValue(Entity.class,entityItem,true,"field_83001_bt");
    }
  }
}

