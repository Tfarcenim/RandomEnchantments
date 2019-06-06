package com.tfar.randomenchants.util;

import com.tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler;
import com.tfar.randomenchants.init.ModEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler.KEY;
import static com.tfar.randomenchants.util.EnchantmentUtils.getTagSafe;
import static net.minecraft.util.math.Vec3d.ZERO;

public class EventHandler {

  public static Map<EntityArrow, Double> homingarrows = new HashMap<>();
  public static List<EntityArrow> trueshotarrows = new ArrayList<>();

  public static double absValue(Vec3d vec) {
    return Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y, 2) + Math.pow(vec.z, 2));
  }

  public Vec3d normalize(Vec3d vec) {
    double d0 = absValue(vec);
    return d0 < 1.0E-20D ? ZERO : new Vec3d(vec.x / d0, vec.y / d0, vec.z / d0);
  }

  @SubscribeEvent
  public void arrowInBlock(ProjectileImpactEvent event) {
    if (!(event.getEntity() instanceof EntityArrow)) return;
    if (event.getRayTraceResult().entityHit != null) return;
    EntityArrow arrow = (EntityArrow) event.getEntity();
    if (arrow.pickupStatus == EntityArrow.PickupStatus.ALLOWED) return;
    if (arrow.getEntityData().getInteger("homing") != 1) return;
    arrow.setDead();
  }

  @SubscribeEvent
  public void serverTick(TickEvent.ServerTickEvent e) {
    if (e.phase != TickEvent.Phase.END) return;
    List<EntityArrow> removeHoming = new ArrayList<>();
    for (Map.Entry<EntityArrow, Double> entry : homingarrows.entrySet()) {
      EntityArrow arrow = entry.getKey();
      if (arrow.ticksExisted > 600) {
        arrow.setDead();
        removeHoming.add(arrow);
      }
      if (arrow.isDead) continue;
      World world = arrow.world;
      int r = 8;
      double x = arrow.posX;
      double y = arrow.posY;
      double z = arrow.posZ;
      List<Entity> targets = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - r, y - r, z - r, x + r, y + r, z + r));
      if (targets.size() == 0) continue;
      for (Entity target : targets) {
        if (target instanceof EntityPlayer || target instanceof EntityEnderman || target instanceof EntityAnimal)
          continue;
        double speed = entry.getValue();
        AxisAlignedBB box = target.getEntityBoundingBox();
        double diff = box.maxY - box.minY;
        Vec3d arrowDirection = new Vec3d(target.posX - arrow.posX, target.posY + diff / 2 - arrow.posY, target.posZ - arrow.posZ);
        arrowDirection = normalize(arrowDirection);
        arrow.setVelocity(speed * arrowDirection.x, speed * arrowDirection.y, speed * arrowDirection.z);
        arrow.velocityChanged = true;
        break;
      }
    }
    for (EntityArrow arrow : removeHoming) {
      homingarrows.remove(arrow);
    }
    List<EntityArrow> removeTrueShot = new ArrayList<>();
    for (EntityArrow arrow : trueshotarrows) {
      if (arrow.ticksExisted > 600) {
        arrow.setDead();
        removeTrueShot.add(arrow);
      }
      if (arrow.isDead) continue;
      arrow.setVelocity(arrow.motionX / .99, arrow.motionY / .99, arrow.motionZ / .99);
      arrow.velocityChanged = true;
    }
    trueshotarrows.removeAll(removeTrueShot);
  }

  @SubscribeEvent
  public void toggle(PlayerInteractEvent.RightClickItem e){
    if (e.getWorld().isRemote)return;
   // if (e.getWorld().getTileEntity(e.getPos()) != null)return;

    ItemStack stack = e.getItemStack();
    if (EnchantmentUtils.stackHasEnch(stack, ModEnchantment.GLOBAL_TRAVELLER) && e.getEntityPlayer().isSneaking()){
      toggle(stack);
    }
  }

  public static void toggle(ItemStack stack){
    NBTTagCompound nbt = getTagSafe(stack);
    if (nbt.hasKey(KEY)) {
      boolean toggle = stack.getTagCompound().getBoolean("toggle");
      stack.getTagCompound().setBoolean("toggle", !toggle);
    }
  }
}
