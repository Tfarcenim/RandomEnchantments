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
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.init.ModEnchantment.PHASING;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentPhasing extends Enchantment {
  public EnchantmentPhasing() {
    super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("phasing");
    this.setName("phasing");
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
    return weapons.enablePhasing != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enablePhasing == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enablePhasing == NORMAL && super.canApplyAtEnchantingTable(stack);
  }

  @SubscribeEvent
  public static void arrowSpawn(ProjectileImpactEvent event)  {
    Entity entity = event.getEntity();
    if (!(entity instanceof EntityArrow))return;
    Entity shooter = ((EntityArrow) entity).shootingEntity;
    if (!(shooter instanceof EntityPlayer))return;
    EntityPlayer player = (EntityPlayer) shooter;
    if (EnchantmentHelper.getMaxEnchantmentLevel(PHASING, player)==0)return;
    if (entity.ticksExisted > 1200)entity.setDead();
    if(event.getRayTraceResult().entityHit == null)event.setCanceled(true);
    }
  }


