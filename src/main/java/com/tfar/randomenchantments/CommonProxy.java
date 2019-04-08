package com.tfar.randomenchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSword;

public class CommonProxy {

  public static EnumEnchantmentType WEAPONS;
  public static EnumEnchantmentType PICKAXE;
  public static EnumEnchantmentType SHIELDS;

  public void init(){
    WEAPONS = RandomEnchantments.addEnchantment("weapons", item -> item instanceof ItemSword || item instanceof ItemBow);
    PICKAXE = RandomEnchantments.addEnchantment("pickaxe", item -> item instanceof ItemPickaxe);
    SHIELDS = RandomEnchantments.addEnchantment("shields", item -> item instanceof ItemShield);
  }
}
