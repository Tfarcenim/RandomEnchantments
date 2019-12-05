package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.List;

import static com.tfar.randomenchants.Config.Restriction.*;

public class EnchantmentBackToTheChamber extends Enchantment {
  public EnchantmentBackToTheChamber() {
    super(Rarity.RARE, RandomEnchants.SHOOTABLE, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("back_to_the_chamber");
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
  public boolean canApply(ItemStack stack){
    return Config.ServerConfig.backtothechamber.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.backtothechamber.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.backtothechamber.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  protected boolean canApplyTogether(Enchantment ench) {
    return super.canApplyTogether(ench) && !((ench instanceof InfinityEnchantment));
  }

  @Override
  public boolean isAllowedOnBooks() {
      return Config.ServerConfig.backtothechamber.get() == NORMAL;
    }

    private static boolean handled = false;

  @Override
  public void onEntityDamaged(LivingEntity user, Entity target, int level) {
    if (handled){
      handled = false;
      return;
    }
    if (!(user instanceof PlayerEntity) || !(target instanceof LivingEntity))return;
    PlayerEntity player = (PlayerEntity)user;
    if (5 * Math.random() > level) return;
      List<ItemStack> inventory = player.inventory.mainInventory;
      for (ItemStack stack : inventory) {
        if (!(stack.getItem() == Items.ARROW || stack.getItem() == Items.TIPPED_ARROW))
          continue;
        ItemStack add = stack.copy();
        add.setCount(1);
        player.addItemStackToInventory(add);
        break;
    }
      handled = true;
  }
}

