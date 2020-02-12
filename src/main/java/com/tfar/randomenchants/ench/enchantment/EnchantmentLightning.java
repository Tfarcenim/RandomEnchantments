package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.server.ServerWorld;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.SWORDS_BOWS;


public class EnchantmentLightning extends Enchantment {
  public EnchantmentLightning() {

    super(Rarity.RARE, SWORDS_BOWS, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("lightning");
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
    return Config.ServerConfig.lightning.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.lightning.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.lightning.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.lightning.get() == NORMAL;
  }

  @Override
  public void onEntityDamaged(LivingEntity user, Entity target, int level) {
    if (!user.world.isRemote)
      ((ServerWorld) user.world).addLightningBolt(new LightningBoltEntity(user.world, target.getPosX(), target.getPosY(), target.getPosZ(), false));
  }
}

