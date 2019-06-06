package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.util.EnchantmentUtils;
import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.init.ModEnchantment.SHATTERING;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentShattering extends Enchantment {
  public EnchantmentShattering() {
    super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("shattering");
    this.setName("shattering");
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
    return weapons.enableShattering != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableShattering == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableShattering != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
      return weapons.enableShattering == NORMAL;
    }


  @SubscribeEvent
  public static void arrowHit(ProjectileImpactEvent event) {
    Entity arrow = event.getEntity();
    Entity block = event.getRayTraceResult().entityHit;
    if(EnchantmentUtils.isArrowinBlock(arrow, block))return;
    EntityPlayer player = (EntityPlayer)((EntityArrow)arrow).shootingEntity;
    if (!EnchantmentUtils.stackHasEnch(player.getHeldItemMainhand(),SHATTERING))return;
    BlockPos pos = event.getRayTraceResult().getBlockPos();

    Block glass = arrow.world.getBlockState(pos).getBlock();

    if (!(glass instanceof BlockGlass))return;

    if (player.canHarvestBlock(arrow.world.getBlockState(pos)))
      arrow.world.destroyBlock(pos,true);
      event.setCanceled(true);
    }
  }


