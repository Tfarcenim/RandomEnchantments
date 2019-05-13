package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.RandomEnchantments;
import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.block.*;
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

import javax.annotation.Nonnull;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.init.ModEnchantment.HARVEST;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentHarvesting extends Enchantment {
  public EnchantmentHarvesting() {
    super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("harvesting");
    this.setName("harvesting");
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
  public boolean canApply(@Nonnull ItemStack stack){
    return weapons.enableHarvesting != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableHarvesting == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableHarvesting != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
      return weapons.enableHarvesting == NORMAL;
    }


  @SubscribeEvent
  public static void arrowHit(ProjectileImpactEvent event) {
    Entity arrow = event.getEntity();
    Entity block = event.getRayTraceResult().entityHit;
    if(RandomEnchantments.isArrowinBlock(arrow, block))return;
    EntityPlayer player = (EntityPlayer)((EntityArrow)arrow).shootingEntity;
    int level = EnchantmentHelper.getMaxEnchantmentLevel(HARVEST, player);
    if (level <= 0)return;
    BlockPos pos = event.getRayTraceResult().getBlockPos();

    Block plant = arrow.world.getBlockState(pos).getBlock();

    if (!(checkBlock(plant)))return;

    if (player.canHarvestBlock(arrow.world.getBlockState(pos)))
      arrow.world.destroyBlock(pos,true);
      event.setCanceled(true);
    }

  private static boolean checkBlock(Block plant){
    return plant instanceof BlockMelon ||
            plant instanceof BlockChorusFlower ||
            plant instanceof BlockChorusPlant ||
            plant instanceof BlockCocoa ||
            plant instanceof BlockPumpkin ||
            plant instanceof BlockCactus;
    }
  }


