package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import static com.tfar.randomenchantments.util.EventHandler.eventHandler;


import static com.tfar.randomenchantments.init.ModEnchantment.HOMING;
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

  @SubscribeEvent
  public static void arrowSpawn(EntityJoinWorldEvent event)  {
    Entity entity = event.getEntity();
    if (!(entity instanceof EntityArrow))return;
    Entity shooter = ((EntityArrow) entity).shootingEntity;
    if (!(shooter instanceof EntityPlayer))return;
    EntityPlayer player = (EntityPlayer) shooter;
      if (EnchantmentHelper.getMaxEnchantmentLevel(HOMING, player)==0)return;
    NBTTagCompound compound = entity.getEntityData();
    compound.setInteger("homing",1);
    compound.setDouble("speed", eventHandler.absValue(new Vec3d(entity.motionX, entity.motionY, entity.motionZ)));

    entity.setNoGravity(true);
  //  if ((EnchantmentHelper.getMaxEnchantmentLevel(FLOAT, user) > 0 && target instanceof EntityLivingBase)){
   //   ((EntityLivingBase)target).
      //        addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 200, 1));
    }
  }

