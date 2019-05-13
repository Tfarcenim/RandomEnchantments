package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.RandomEnchantments;
import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.init.ModEnchantment.*;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentExploding extends Enchantment {
  public EnchantmentExploding() {
    super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("exploding");
    this.setName("exploding");
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
    return weapons.enableExploding != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableExploding == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableExploding != DISABLED && super.canApplyAtEnchantingTable(stack);
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
    Entity victim = event.getRayTraceResult().entityHit;
    if(RandomEnchantments.checkShooterArrowAndVictim(arrow, victim))return;
    EntityPlayer player = (EntityPlayer)((EntityArrow)arrow).shootingEntity;
    int level = EnchantmentHelper.getMaxEnchantmentLevel(EXPLODING, player);
    if (level == 0)return;
    if (victim.hurtResistantTime>0)return;
    if (!player.world.isRemote) {victim.world.createExplosion(null,victim.posX,victim.posY,victim.posZ,level,false);
    arrow.setDead();
      }
    }
  }


