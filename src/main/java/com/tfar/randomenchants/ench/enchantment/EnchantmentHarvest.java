package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

import static com.tfar.randomenchants.Config.Restriction.*;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)

public class EnchantmentHarvest extends Enchantment {
  public EnchantmentHarvest() {
    super(Rarity.RARE, RandomEnchants.SHOOTABLE, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("harvest");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 25;
  }

  @Override
  public int getMaxLevel() {
    return 1;
  }

  @Override
  public boolean canApply(@Nonnull ItemStack stack){
    return Config.ServerConfig.harvest.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.harvest.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.harvest.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
      return Config.ServerConfig.harvest.get() == NORMAL;
    }


  @SubscribeEvent
  public static void arrowHit(ProjectileImpactEvent event) {
    Entity proj = event.getEntity();
    if (!(proj instanceof AbstractArrowEntity))return;
    RayTraceResult result = event.getRayTraceResult();
    AbstractArrowEntity arrow = (AbstractArrowEntity)proj;
    if (!(result instanceof BlockRayTraceResult))return;
    BlockPos pos = ((BlockRayTraceResult)result).getPos();
    Entity shooter = arrow.getShooter();
    if (!(shooter instanceof PlayerEntity))return;
    PlayerEntity player = (PlayerEntity)shooter;
    if (!EnchantUtils.hasEnch(player, RandomEnchants.ObjectHolders.HARVEST))return;
    Block plant = proj.world.getBlockState(pos).getBlock();
    if (!(isPlant(plant)))return;

    if (player.canHarvestBlock(proj.world.getBlockState(pos)))
      proj.world.destroyBlock(pos,true);
      event.setCanceled(true);
    }

  private static boolean isPlant(Block plant){
    return plant instanceof MelonBlock ||
            plant instanceof ChorusPlantBlock ||
            plant instanceof CocoaBlock ||
            plant instanceof PumpkinBlock ||
            plant instanceof CactusBlock;
    }
  }


