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
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.ANVIL;
import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.DISABLED;
import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.init.ModEnchantment.TRUE_SHOT;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)

public class EnchantmentTrueShot extends Enchantment {
  public EnchantmentTrueShot() {
    super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("true_shot");
    this.setName("true_shot");
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
  public boolean canApply(ItemStack stack){
    return weapons.enableTrueShot != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableTrueShot == ANVIL;
  }

  @SubscribeEvent
  public static void arrowSpawn(EntityJoinWorldEvent event)  {
    Entity entity = event.getEntity();
    if (!(entity instanceof EntityArrow))return;
    Entity shooter = ((EntityArrow) entity).shootingEntity;
    if (!(shooter instanceof EntityPlayer))return;
    EntityPlayer player = (EntityPlayer) shooter;
      if (EnchantmentHelper.getMaxEnchantmentLevel(TRUE_SHOT, player)==0)return;
    entity.setNoGravity(true);
    }
  }

