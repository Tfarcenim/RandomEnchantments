package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.init.ModEnchantment.RICOCHET;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentRicochet extends Enchantment {
  public EnchantmentRicochet() {
    super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("ricochet");
    this.setName("ricochet");
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
  public boolean canApply(ItemStack stack){
    return weapons.enableRicochet != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableRicochet == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableRicochet != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
      return weapons.enableRicochet == NORMAL;
    }


  @SubscribeEvent
  public static void arrowHit(ProjectileImpactEvent event) {
    if (event.getRayTraceResult().entityHit != null) return;
    Entity entity = event.getEntity();
    if (!(entity instanceof EntityArrow)) return;
    EntityArrow arrow = (EntityArrow) entity;
    Entity shooter = arrow.shootingEntity;
    if (!(shooter instanceof EntityPlayer)) return;
    EntityPlayer player = (EntityPlayer) shooter;
    int level = EnchantmentHelper.getMaxEnchantmentLevel(RICOCHET, player);
    if (level <= 0)return;
    EnumFacing facing = event.getRayTraceResult().sideHit;
    switch (facing){
      case UP:{
        arrow.setVelocity(entity.motionX,-entity.motionY, entity.motionZ);break;
      }
      case DOWN:{
        arrow.setVelocity(entity.motionX,-entity.motionY, entity.motionZ);break;
      }
      case EAST:{
        arrow.setVelocity(-entity.motionX,entity.motionY, entity.motionZ);break;
      }
      case WEST:{
        arrow.setVelocity(-entity.motionX,entity.motionY, entity.motionZ);break;
      }
      case NORTH:{
        arrow.setVelocity(entity.motionX,entity.motionY, -entity.motionZ);break;
      }
      case SOUTH:{
        arrow.setVelocity(entity.motionX,entity.motionY, -entity.motionZ);break;
      }
      default:{
        throw new IllegalStateException("INVALID ENUM DETECTED");
      }
    }
    arrow.velocityChanged = true;
      event.setCanceled(true);
    }
  }


