package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.SHIELDS;
import static com.tfar.randomenchants.init.ModEnchantment.REFLECT;


@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)
public class EnchantmentReflect extends Enchantment {
  public EnchantmentReflect() {

    super(Rarity.RARE, SHIELDS, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND
    });
    this.setRegistryName("reflect");
    this.setName("reflect");
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
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableReflect != DISABLED && stack.getItem().isShield(stack, null);
  }

  @Override
  public boolean canApply(@Nonnull ItemStack stack){
    return weapons.enableReflect != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableReflect == ANVIL;
  }

  @Override
  public boolean isAllowedOnBooks() {
    return weapons.enableReflect== NORMAL;
  }


  @SubscribeEvent
  public static void reflect(ProjectileImpactEvent e) {

    Entity projectile = e.getEntity();
    Entity target = e.getRayTraceResult().entityHit;
    if (!(target instanceof EntityPlayer)) return;
    EntityPlayer player = (EntityPlayer) target;
    if (EnchantmentHelper.getMaxEnchantmentLevel(REFLECT, player) > 0) {
      projectile.motionX *= -1;
      projectile.motionY *= -1;
      projectile.motionZ *= -1;
      if(projectile instanceof EntityFireball){
        ((EntityFireball) projectile).accelerationX *= -1;
        ((EntityFireball) projectile).accelerationY *= -1;
        ((EntityFireball) projectile).accelerationZ *= -1;
      }

      e.setCanceled(true);
      projectile.velocityChanged = true;
    }
  }
}


