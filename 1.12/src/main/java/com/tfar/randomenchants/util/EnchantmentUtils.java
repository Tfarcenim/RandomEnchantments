package com.tfar.randomenchants.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class EnchantmentUtils {
  public static boolean stackHasEnch(ItemStack stack, Enchantment enchantment){
    return EnchantmentHelper.getEnchantmentLevel(enchantment, stack) > 0;
  }
  public static String getWorldNameFromid(int id){
    WorldServer[] Worlds = DimensionManager.getWorlds();
    for (WorldServer world : Worlds) {
      if (world.provider.getDimension() ==  id){
        return world.provider.getDimensionType().getName();
      }
    }
    return null;
  }

  public static NBTTagCompound getTagSafe(ItemStack stack) {
    if(stack.isEmpty() || !stack.hasTagCompound()) {
      return new NBTTagCompound();
    }
    return stack.getTagCompound();
  }
  public static boolean isDark(EntityLivingBase e) {
    BlockPos blockpos = e.getPosition();
    World world = e.world;
    int i = world.getLightFromNeighbors(blockpos);
    if (world.isThundering())
    {
      int j = world.getSkylightSubtracted();
      world.setSkylightSubtracted(10);
      i = world.getLightFromNeighbors(blockpos);
      world.setSkylightSubtracted(j);
    }
    return i < 8;
  }

  public static boolean isArrowAndIsLivingBase(Entity arrow, Entity victim) {
    return  !(arrow instanceof EntityArrow) || !(((EntityArrow) arrow).shootingEntity instanceof EntityPlayer) || !(victim instanceof EntityLivingBase);
  }
  public static boolean isArrowinBlock(Entity arrow, Entity victim){
    return (!(arrow instanceof EntityArrow)) || !(((EntityArrow) arrow).shootingEntity instanceof EntityPlayer) || victim != null;
  }
}
