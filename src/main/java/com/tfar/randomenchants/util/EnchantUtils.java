package com.tfar.randomenchants.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnchantUtils {

  public static boolean hasEnch(ItemStack stack, Enchantment enchantment){
    return enchantment != null && EnchantmentHelper.getEnchantmentLevel(enchantment, stack) > 0;
  }

  public static boolean hasEnch(LivingEntity entity, Enchantment enchantment){
    return enchantment != null && EnchantmentHelper.getMaxEnchantmentLevel(enchantment,entity) > 0;
  }

  public static boolean isDark(LivingEntity e) {
    BlockPos blockpos = e.getPosition();
    World world = e.world;
    int i = world.getLight(blockpos);
    if (world.isThundering())
    {
    }
    return i < 8;
  }

  public static boolean isArrowAndIsLivingBase(Entity arrow, Entity victim) {
    return  !(arrow instanceof AbstractArrowEntity) || !(arrow.getEntity() instanceof PlayerEntity) || !(victim instanceof LivingEntity);
  }
  public static boolean isArrow(LivingEntity l){
    return l.getLastDamageSource() != null && l.getLastDamageSource().getImmediateSource() instanceof AbstractArrowEntity;
  }
}
