package com.tfar.randomenchants.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.GLOBAL_TRAVELLER;
//import static com.tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler.KEY;
import static net.minecraft.util.math.Vec3d.ZERO;

public class EventHandler {

  public static Map<AbstractArrowEntity, Double> homingarrows = new HashMap<>();
  public static List<AbstractArrowEntity> trueshotarrows = new ArrayList<>();

  public static double absValue(Vec3d vec) {
    return Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y, 2) + Math.pow(vec.z, 2));
  }

  public Vec3d normalize(Vec3d vec) {
    double d0 = absValue(vec);
    return d0 < 1.0E-20D ? ZERO : new Vec3d(vec.x / d0, vec.y / d0, vec.z / d0);
  }

  @SubscribeEvent
  public void arrowInBlock(ProjectileImpactEvent event) {
    if (!(event.getEntity() instanceof AbstractArrowEntity)) return;
    if (event.getRayTraceResult() instanceof EntityRayTraceResult) return;
    AbstractArrowEntity arrow = (AbstractArrowEntity) event.getEntity();
    if (arrow.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) return;
    if (arrow.getEntityData().getInt("homing") != 1) return;
    arrow.remove();
  }

  @SubscribeEvent
  public void serverTick(TickEvent.ServerTickEvent e) {
    if (e.phase != TickEvent.Phase.END) return;
    List<AbstractArrowEntity> removeHoming = new ArrayList<>();
    for (Map.Entry<AbstractArrowEntity, Double> entry : homingarrows.entrySet()) {
      AbstractArrowEntity arrow = entry.getKey();
      if (arrow.ticksExisted > 600) {
        arrow.remove();
        removeHoming.add(arrow);
      }
      if (!arrow.isAlive()) continue;
      World world = arrow.world;
      int r = 8;
      double x = arrow.posX;
      double y = arrow.posY;
      double z = arrow.posZ;
      List<Entity> targets = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(x - r, y - r, z - r, x + r, y + r, z + r));
      if (targets.size() == 0) continue;
      for (Entity target : targets) {
        if (target instanceof PlayerEntity || target instanceof EndermanEntity || target instanceof AnimalEntity)
          continue;
        double speed = entry.getValue();
        AxisAlignedBB box = target.getBoundingBox();
        double diff = box.maxY - box.minY;
        Vec3d arrowDirection = new Vec3d(target.posX - arrow.posX, target.posY + diff / 2 - arrow.posY, target.posZ - arrow.posZ);
        arrowDirection = normalize(arrowDirection);
        arrow.setMotion(speed * arrowDirection.x, speed * arrowDirection.y, speed * arrowDirection.z);
        arrow.velocityChanged = true;
        break;
      }
    }
    for (AbstractArrowEntity arrow : removeHoming) {
      homingarrows.remove(arrow);
    }
    List<AbstractArrowEntity> removeTrueShot = new ArrayList<>();
    for (AbstractArrowEntity arrow : trueshotarrows) {
      if (arrow.ticksExisted > 600) {
        arrow.remove();
        removeTrueShot.add(arrow);
      }
      if (!arrow.isAlive()) continue;
      arrow.setMotion(arrow.getMotion().scale(1/.99));
      arrow.velocityChanged = true;
    }
    trueshotarrows.removeAll(removeTrueShot);
  }

 /* @SubscribeEvent
  public void toggle(PlayerInteractEvent.RightClickItem e) {
    if (e.getWorld().isRemote) return;
    // if (e.getWorld().getTileEntity(e.getPos()) != null)return;

    ItemStack stack = e.getItemStack();
    if (EnchantUtils.hasEnch(stack, GLOBAL_TRAVELLER) && e.getEntityPlayer().isSneaking()) {
      toggle(stack);
    }
  }*/

/*  public static void toggle(ItemStack stack) {
    CompoundNBT nbt = stack.getOrCreateTag();
    if (nbt.contains(KEY)) {
      boolean toggle = stack.getOrCreateTag().getBoolean("toggle");
      stack.getOrCreateTag().putBoolean("toggle", !toggle);
    }
  }*/


}