package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchantments.EnchantmentConfig.weapons;


import static com.tfar.randomenchantments.init.ModEnchantment.HOMING;
import static com.tfar.randomenchantments.util.EventHandler.*;

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
  public boolean canApply(ItemStack stack){
    return weapons.enableHoming != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableHoming == ANVIL;
  }

  @SubscribeEvent
  public static void arrowLoose(EntityJoinWorldEvent event)  {
    Entity entity = event.getEntity();
    if (!(entity instanceof EntityArrow))return;
    Entity shooter = ((EntityArrow) entity).shootingEntity;
    if (!(shooter instanceof EntityPlayer))return;
    EntityPlayer player = (EntityPlayer) shooter;
      if (EnchantmentHelper.getMaxEnchantmentLevel(HOMING, player)==0)return;
    NBTTagCompound compound = entity.getEntityData();
    compound.setDouble("speed", absValue(new Vec3d(entity.motionX, entity.motionY, entity.motionZ)));
    entity.setNoGravity(true);
    homingarrows.add((EntityArrow)entity);
  }
}

