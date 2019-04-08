package com.tfar.randomenchantments.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static com.tfar.randomenchantments.init.ModEnchantment.*;

public class EventHandler {

  public static EventHandler eventHandler = new EventHandler();
  /*public void homingArrows(EntityEvent event){
    if (!(event.getEntity() instanceof EntityArrow))return;
    EntityArrow arrow = (EntityArrow)event.getEntity();
    if ((arrow.getEntityData().getInteger("homing"))!=1)return;
    System.out.println(event);
    Vec3d arrowVelocityVector = new Vec3d(arrow.motionX,arrow.motionY, arrow.motionZ);
    double speed = absValue(arrowVelocityVector);
    speed *= 1.5;
    System.out.println(speed);
    if (speed <.01)return;
    Vec2d arrowXZ = unitVector(new Vec2d(arrowVelocityVector.x,arrowVelocityVector.z));
    World world = arrow.world;
    int r = 24;
    double x = arrow.posX;double y = arrow.posY;double z = arrow.posZ;
    List<Entity> targets = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - r, y - r, z - r, x + r, y + r, z + r));
    if (targets.size()<=1)return;
    targets.remove(arrow.shootingEntity);
    EntityLivingBase victim = null;
    Vec3d targetDirectionVector = null;
    for (Entity target : targets){
      double x1 = target.posX-arrow.posX;
      double y1 = target.posY-arrow.posY;
      double z1 = target.posZ-arrow.posZ;
      Vec3d entityVec = unitVector(new Vec3d(x1,y1,z1));
      Vec2d flatEntityVec = new Vec2d(entityVec.x, entityVec.z);
      double angle = (180/Math.PI)*getAngle(arrowXZ,flatEntityVec);
    //  if (Math.abs(angle) > 90)continue;
      victim = (EntityLivingBase)target;
      AxisAlignedBB box = victim.getEntityBoundingBox();
      double tall = box.maxY - box.minY;
      targetDirectionVector = new Vec3d(entityVec.x,entityVec.y+tall/2,entityVec.z);
      break;
    }
    if (victim==null)return;
    // Vec2d shiftedDirection = shift(arrowXZ,targetDirectionVector);
   //  System.out.println(shiftedDirection);
     if (!world.isRemote) {
       arrow.setVelocity(speed * targetDirectionVector.x, speed * targetDirectionVector.y, speed * targetDirectionVector.z);
       arrow.velocityChanged = true;
     }
  }*/



  public double dotProduct(Vec3d vec1, Vec3d vec2){
    return vec1.x*vec2.x + vec1.y*vec2.y + vec1.z*vec2.z;
  }
  public double absValue(Vec3d vec){
    return Math.sqrt(Math.pow(vec.x,2) + Math.pow(vec.y,2) + Math.pow(vec.z,2));
  }
  public double getAngle(Vec3d vec1, Vec3d vec2){
    double dotP = dotProduct(vec1,vec2);
    return Math.acos(dotP) / (absValue(vec1) * absValue(vec2));
  }
  public Vec3d shift(Vec3d vecCurrent, Vec3d vecTarget){
    Vec3d vecUnit1 = unitVector(vecCurrent);
    Vec3d vecUnit2 = unitVector(vecTarget);
    return unitVector(addVector(vecUnit1,vecUnit2));
  }
  public Vec3d unitVector(Vec3d vec){
    return new Vec3d(vec.x/absValue(vec),vec.y/absValue(vec),vec.z/absValue(vec));
  }



  public Vec3d addVector(Vec3d vec1, Vec3d vec2){
    return new Vec3d(vec1.x+vec2.x,vec1.y+vec2.y,vec1.z+vec2.z);
  }
  public Vec3d scalarMultiply(Vec3d vec , double scalar){
    return new Vec3d(vec.x*scalar,vec.y*scalar,vec.z*scalar);
  }



  public double absValue(Vec2d vec){
    return Math.sqrt(Math.pow(vec.x,2)+Math.pow(vec.z,2));
  }


  public Vec2d unitVector(Vec2d vec){
    return new Vec2d(vec.x/absValue(vec),vec.z/absValue(vec));
  }

  public Vec2d addVector(Vec2d vec1, Vec2d vec2){
    return new Vec2d(vec1.x+vec2.x,vec1.z+vec2.z);
  }
  public Vec2d shift(Vec2d vecCurrent, Vec2d vecTarget){
    Vec2d vecUnit1 = unitVector(vecCurrent);
    Vec2d vecUnit2 = unitVector(vecTarget);
    return unitVector(addVector(vecUnit1,vecUnit2));
  }
  public double getAngle(Vec2d vec1, Vec2d vec2){
    double dotP = dotProduct(vec1,vec2);
    return Math.signum(dotP)*Math.acos(dotP)/(absValue(vec1)*absValue(vec2));
  }
  public double dotProduct(Vec2d vec1, Vec2d vec2){
    return vec1.x*vec2.x+vec1.z*vec2.z;
  }

  @SubscribeEvent
    public void homingArrows(LivingEvent.LivingUpdateEvent event) {
    EntityLivingBase target = event.getEntityLiving();
    if (target == null)return;
    if (target instanceof EntityPlayer || target instanceof EntityEnderman)return;
    World world = target.world;
    int r = 8;
    double x = target.posX;
    double y = target.posY;
    double z = target.posZ;
    List<Entity> arrows = world.getEntitiesWithinAABB(EntityArrow.class, new AxisAlignedBB(x - r, y - r, z - r, x + r, y + r, z + r));
    if (arrows.size() == 0)return;
    ArrayList<Entity> nonHoming = new ArrayList<>();
    for (Entity arrow : arrows) {
      if ((arrow.getEntityData().getInteger("homing")) != 1) nonHoming.add(arrow);
    }
    arrows.removeAll(nonHoming);
    if (arrows.size()==0)return;
    for (Entity arrow : arrows) {
      double speed = arrow.getEntityData().getDouble("speed");
      //  if (speed < .01) return;
      // Vec2d arrowXZ = unitVector(new Vec2d(arrowVelocityVector.x, arrowVelocityVector.z));
      AxisAlignedBB box = target.getEntityBoundingBox();
      double diff = box.maxY - box.minY;
      double x1 = target.posX - arrow.posX;
      double y1 = (target.posY+diff/2) - arrow.posY;
      double z1 = target.posZ - arrow.posZ;
      //  Vec3d entityVec =
      // Vec2d flatEntityVec = new Vec2d(entityVec.x, entityVec.z);
      // double angle = (180 / Math.PI) * getAngle(arrowXZ, flatEntityVec);
      //  if (Math.abs(angle) > 90)continue;
      Vec3d targetDirectionVector = unitVector(new Vec3d(x1, y1, z1));

      // Vec2d shiftedDirection = shift(arrowXZ,targetDirectionVector);
      //  System.out.println(shiftedDirection);
      if (!world.isRemote) {
        arrow.setVelocity(speed * targetDirectionVector.x, speed * targetDirectionVector.y, speed * targetDirectionVector.z);
        arrow.velocityChanged = true;
      }
    }
  }

  @SubscribeEvent
  public void arrowInBlock(ProjectileImpactEvent event) {
    if (!(event.getEntity()instanceof EntityArrow))return;
    if (event.getRayTraceResult().entityHit != null)return;
    EntityArrow arrow = (EntityArrow)event.getEntity();
    if (arrow.pickupStatus == EntityArrow.PickupStatus.ALLOWED)return;
    if (arrow.getEntityData().getInteger("homing") != 1)return;
    arrow.setDead();
  }
}
