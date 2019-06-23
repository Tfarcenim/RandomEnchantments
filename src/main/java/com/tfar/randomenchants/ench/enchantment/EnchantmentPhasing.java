package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.PHASING;

@Mod.EventBusSubscriber(modid = RandomEnchants.MOD_ID)

public class EnchantmentPhasing extends Enchantment {
  public EnchantmentPhasing() {
    super(Rarity.RARE, EnchantmentType.BOW, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("phasing");
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
  public boolean canApply(ItemStack stack){
    return weapons.enablePhasing != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enablePhasing == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enablePhasing != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return weapons.enablePhasing == NORMAL;
  }

  @SubscribeEvent
  public static void arrowSpawn(ProjectileImpactEvent event)  {
    Entity entity = event.getEntity();
    if (!(entity instanceof AbstractArrowEntity))return;
    Entity shooter = ((AbstractArrowEntity) entity).getShooter();
    if (!(shooter instanceof PlayerEntity))return;
    PlayerEntity player = (PlayerEntity) shooter;
    if (!EnchantUtils.hasEnch(player,PHASING))return;
    if (entity.ticksExisted > 1200)entity.remove();
    if(event.getRayTraceResult() instanceof BlockRayTraceResult)event.setCanceled(true);
    }
  }


