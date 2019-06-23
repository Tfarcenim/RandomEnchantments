package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;

@Mod.EventBusSubscriber(modid = RandomEnchants.MOD_ID)

public class EnchantmentFastPlace extends Enchantment {
  public EnchantmentFastPlace() {
    super(Rarity.RARE, EnchantmentType.ARMOR_CHEST, new EquipmentSlotType[]{
            EquipmentSlotType.CHEST
    });
    this.setRegistryName("fast_place");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 30;
  }

  @Override
  public int getMaxLevel() {
    return 5;
  }

  @Override
  public boolean canApply(ItemStack stack){
    return weapons.enableFastPlacing != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableFastPlacing == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableFastPlacing != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return weapons.enableDeflect == NORMAL;
  }

  }



