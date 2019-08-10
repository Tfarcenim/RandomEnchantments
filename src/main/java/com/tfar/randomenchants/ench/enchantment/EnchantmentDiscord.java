package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

import static com.tfar.randomenchants.Config.Restriction.*;

public class EnchantmentDiscord extends Enchantment {
  public EnchantmentDiscord() {

    super(Rarity.RARE, RandomEnchants.SWORDS_BOWS, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("discord");
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
    return Config.ServerConfig.discord.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.discord.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.discord.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.discord.get() == NORMAL;
  }

  private static boolean handled = false;

  @Override
  public void onEntityDamaged(LivingEntity user, Entity target, int level) {
    if (handled){
      handled = false;
      return;
    }
    if (!(target instanceof LivingEntity)) return;
    int r = 64;
    double x = target.posX;
    double y = target.posY;
    double z = target.posZ;
    List<Entity> aggro = target.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(x - r, y - r, z - r, x + r, y + r, z + r));
    for (Entity triggered : aggro) {
      ((LivingEntity) triggered).setRevengeTarget((LivingEntity) target);
    }
    handled = true;
  }
}



