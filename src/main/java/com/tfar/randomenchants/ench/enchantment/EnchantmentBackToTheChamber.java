package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.BACK_TO_THE_CHAMBER;

@Mod.EventBusSubscriber(modid = RandomEnchants.MOD_ID)

public class EnchantmentBackToTheChamber extends Enchantment {
  public EnchantmentBackToTheChamber() {
    super(Rarity.RARE, EnchantmentType.BOW, new EquipmentSlotType[]{
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
    return weapons.enableBackToTheChamber != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableBackToTheChamber == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableBackToTheChamber != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  protected boolean canApplyTogether(Enchantment ench) {
    return super.canApplyTogether(ench) && !((ench instanceof InfinityEnchantment));
  }

  @Override
  public boolean isAllowedOnBooks() {
      return weapons.enableBackToTheChamber == NORMAL;
    }


  @SubscribeEvent
  public static void arrowHit(ProjectileImpactEvent event) {
    Entity arrow = event.getEntity();
    if (event.getRayTraceResult() instanceof BlockRayTraceResult || EnchantUtils.isArrowAndIsLivingBase(arrow, ((EntityRayTraceResult)event.getRayTraceResult()).getEntity()))
      return;
    PlayerEntity player = (PlayerEntity)((AbstractArrowEntity)arrow).getShooter();
    int level = EnchantmentHelper.getMaxEnchantmentLevel(BACK_TO_THE_CHAMBER, player);
    if (5 * Math.random() > level) return;
    if (!player.world.isRemote) {
      List<ItemStack> inventory = player.inventory.mainInventory;
      for (ItemStack stack : inventory) {
        if (!(stack.getItem() == Items.ARROW || stack.getItem() == Items.TIPPED_ARROW))
          continue;
        stack.grow(1);
        break;
      }
    }
  }
}

