package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.util.EnchantmentUtils;
import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

import com.tfar.randomenchants.RandomEnchants;
import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.init.ModEnchantment.BACK_TO_THE_CHAMBER;
import static com.tfar.randomenchants.init.ModEnchantment.MULTISHOT;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentBackToTheChamber extends Enchantment {
  public EnchantmentBackToTheChamber() {
    super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("back_to_the_chamber");
    this.setName("back_to_the_chamber");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 5 + 10 * (level - 1);
  }

  @Override
  public int getMaxEnchantability(int level) {
    return super.getMinEnchantability(level) + 25;
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
    return super.canApplyTogether(ench) && !((ench == MULTISHOT) || (ench instanceof EnchantmentArrowInfinite));
  }

  @Override
  public boolean isAllowedOnBooks() {
      return weapons.enableBackToTheChamber == NORMAL;
    }


  @SubscribeEvent
  public static void arrowHit(ProjectileImpactEvent event) {
    Entity arrow = event.getEntity();
    if (EnchantmentUtils.isArrowAndIsLivingBase(arrow, event.getRayTraceResult().entityHit))return;
    EntityPlayer player = (EntityPlayer)((EntityArrow)arrow).shootingEntity;
    int level = EnchantmentHelper.getMaxEnchantmentLevel(BACK_TO_THE_CHAMBER, player);
    if (5 * Math.random() > level) return;
    if (!player.world.isRemote) {
      try {
        // EntityArrow#getArrowStack
        ItemStack arrowItem = (ItemStack) ObfuscationReflectionHelper.findMethod(EntityArrow.class, "func_184550_j", ItemStack.class).invoke((EntityArrow) arrow);

        boolean found = false;
        List<ItemStack> inventory = player.inventory.mainInventory;
        for (ItemStack stack : inventory) {
          if (!(stack.getItem() == arrowItem.getItem()))
            continue;
          stack.grow(1);
          found = true;
          break;
        }
        if (!found) {
          player.addItemStackToInventory(arrowItem);
        }
      } catch (Exception e) {
        RandomEnchants.logger.error("Reflection error ", e);
      }
    }
  }
}

