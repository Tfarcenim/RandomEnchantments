package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.init.ModEnchantment.*;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentPiercing extends Enchantment {
  public EnchantmentPiercing() {
    super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("piercing");
    this.setName("piercing");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 25;
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
  public boolean canApply(ItemStack stack) {
    return weapons.enablePiercing != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enablePiercing == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enablePiercing != DISABLED && super.canApplyAtEnchantingTable(stack);
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
    if (!(event.getRayTraceResult().entityHit instanceof EntityLivingBase)) return;
    Entity arrow = event.getEntity();
    if (!(arrow instanceof EntityArrow)) return;
    Entity shooter = ((EntityArrow) arrow).shootingEntity;
    if (!(shooter instanceof EntityPlayer)) return;
    EntityPlayer player = (EntityPlayer) shooter;
    int level = EnchantmentHelper.getMaxEnchantmentLevel(PIERCING, player);
    if (level <= 0) return;
    if (true) {
      Entity victim = event.getRayTraceResult().entityHit;
      if (!victim.world.isRemote) {
        if (victim.hurtResistantTime > 0) {
          event.setCanceled(true);
          return;
        }
        ItemArrow itemArrow = new ItemArrow();
        EntityArrow newArrow = itemArrow.createArrow(player.world, new ItemStack(Items.ARROW), player);
        newArrow.setPosition(victim.posX, arrow.posY, victim.posZ);
        double speed = Math.sqrt(Math.pow(arrow.motionX, 2) + Math.pow(arrow.motionY, 2) + Math.pow(arrow.motionZ, 2));
        newArrow.shoot(arrow.motionX / speed, arrow.motionY / speed, arrow.motionZ / speed, (float) speed, 0);
        victim.world.spawnEntity(newArrow);
      }
    }
  }
}



