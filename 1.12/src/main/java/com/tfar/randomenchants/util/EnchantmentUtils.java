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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

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
    return !(arrow instanceof EntityArrow) || !(((EntityArrow) arrow).shootingEntity instanceof EntityPlayer) || victim != null;
  }

  public static void serverSafeSetVelocity(double x, double y, double z, EntityArrow arrow) {


    arrow.motionX = x;
    arrow.motionY = y;
    arrow.motionZ = z;

    if (arrow.prevRotationPitch == 0.0F && arrow.prevRotationYaw == 0.0F) {
      float f = MathHelper.sqrt(x * x + z * z);
      arrow.rotationPitch = (float) (MathHelper.atan2(y, (double) f) * (180D / Math.PI));
      arrow.rotationYaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
      arrow.prevRotationPitch = arrow.rotationPitch;
      arrow.prevRotationYaw = arrow.rotationYaw;
      arrow.setLocationAndAngles(arrow.posX, arrow.posY, arrow.posZ, arrow.rotationYaw, arrow.rotationPitch);
      ObfuscationReflectionHelper.setPrivateValue(EntityArrow.class, arrow, 0, "field_70252_j");
    }
  }

  public static void serverSafeSetVelocity(Vec3d vec3d, EntityArrow arrow) {
    serverSafeSetVelocity(vec3d.x, vec3d.y, vec3d.z, arrow);
  }
}
