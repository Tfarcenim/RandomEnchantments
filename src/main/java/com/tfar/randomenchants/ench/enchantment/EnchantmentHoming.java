package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.init.ModEnchantment.HOMING;
import static com.tfar.randomenchants.util.EventHandler.absValue;
import static com.tfar.randomenchants.util.EventHandler.homingarrows;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)

public class EnchantmentHoming extends Enchantment {
  public EnchantmentHoming() {
    super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("homing");
    this.setName("homing");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 15;
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
    return weapons.enableHoming != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableHoming == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableHoming != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return weapons.enableHoming == NORMAL;
  }

  @SubscribeEvent
  public static void arrowLoose(EntityJoinWorldEvent event)  {
    Entity entity = event.getEntity();
    if (!(entity instanceof EntityArrow))return;
    Entity shooter = ((EntityArrow) entity).shootingEntity;
    if (!(shooter instanceof EntityPlayer))return;
    EntityPlayer player = (EntityPlayer) shooter;
      if (EnchantmentHelper.getMaxEnchantmentLevel(HOMING, player)==0)return;
    entity.setNoGravity(true);
    homingarrows.put((EntityArrow)entity,absValue(new Vec3d(entity.motionX, entity.motionY, entity.motionZ)));
  }
}

