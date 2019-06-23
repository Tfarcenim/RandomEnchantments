package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
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

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.REFLECT;
import static com.tfar.randomenchants.RandomEnchants.SHIELDS;


@Mod.EventBusSubscriber(modid = RandomEnchants.MOD_ID)
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

    if (!(e.getRayTraceResult() instanceof EntityRayTraceResult))return;
    Entity projectile = e.getEntity();
    Entity target = ((EntityRayTraceResult) e.getRayTraceResult()).getEntity();
    if (!(target instanceof PlayerEntity)) return;
    PlayerEntity player = (PlayerEntity) target;
    if (EnchantUtils.hasEnch(player, REFLECT)) {
      projectile.setMotion(projectile.getMotion().func_216371_e());
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


