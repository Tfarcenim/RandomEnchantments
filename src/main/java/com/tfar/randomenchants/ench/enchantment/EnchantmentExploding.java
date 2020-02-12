package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;

import static com.tfar.randomenchants.Config.Restriction.*;

public class EnchantmentExploding extends Enchantment {
  public EnchantmentExploding() {
    super(Rarity.RARE, RandomEnchants.SHOOTABLE, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("exploding");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 5 + 10 * (level - 1);
  }

  @Override
  public int getMaxLevel() {
    return 5;
  }

  @Override
  public boolean canApply(ItemStack stack) {
    return Config.ServerConfig.exploding.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.exploding.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.exploding.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.exploding.get() == NORMAL;
  }

  private static boolean handled = false;

  @Override
  public void onEntityDamaged(LivingEntity user, Entity target, int level) {
    if (handled) {
      handled = false;
      return;
    }
    if (!(user instanceof PlayerEntity) || !EnchantUtils.isArrow((LivingEntity) target)) return;
    PlayerEntity player = (PlayerEntity) user;
    if (!player.world.isRemote) {
      target.world.createExplosion(null, target.getPosX(), target.getPosY(), target.getPosZ(), level, Explosion.Mode.DESTROY);
    }
    handled = true;
  }
}


