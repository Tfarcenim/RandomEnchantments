package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.REFLECT;
import static com.tfar.randomenchants.RandomEnchants.SHIELDS;


@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)
public class EnchantmentReflect extends Enchantment {
  public EnchantmentReflect() {

    super(Rarity.RARE, SHIELDS, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND
    });
    this.setRegistryName("reflect");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 15;
  }

  @Override
  public int getMaxLevel() {
    return 1;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.reflect.get() != DISABLED && stack.getItem().isShield(stack, null);
  }

  @Override
  public boolean canApply(@Nonnull ItemStack stack){
    return Config.ServerConfig.reflect.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.reflect.get() == ANVIL;
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.reflect.get() == NORMAL;
  }


  @SubscribeEvent
  public static void reflect(ProjectileImpactEvent e) {

    if (!(e.getRayTraceResult() instanceof EntityRayTraceResult))return;
    Entity projectile = e.getEntity();
    Entity target = ((EntityRayTraceResult) e.getRayTraceResult()).getEntity();
    if (!(target instanceof PlayerEntity)) return;
    PlayerEntity player = (PlayerEntity) target;
    if (EnchantUtils.hasEnch(player, REFLECT)) {
      projectile.setMotion(projectile.getMotion().inverse());
      if(projectile instanceof DamagingProjectileEntity){
        ((DamagingProjectileEntity) projectile).accelerationX *= -1;
        ((DamagingProjectileEntity) projectile).accelerationY *= -1;
        ((DamagingProjectileEntity) projectile).accelerationZ *= -1;
      }

      e.setCanceled(true);
      projectile.velocityChanged = true;
    }
  }
}


