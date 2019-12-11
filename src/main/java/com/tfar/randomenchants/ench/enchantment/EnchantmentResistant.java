package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.tools;
import static com.tfar.randomenchants.init.ModEnchantment.RESISTANCE;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentResistant extends Enchantment {
  public EnchantmentResistant() {
    super(Rarity.RARE, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("resistance");
    this.setName("resistance");
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
  public static void itemDamaged(ItemTossEvent event) {
    EntityItem entityItem = event.getEntityItem();
    ItemStack stack = entityItem.getItem();
    if (EnchantmentHelper.getEnchantmentLevel(RESISTANCE, stack) > 0) {
      entityItem.setEntityInvulnerable(true);
    }
  }
}

