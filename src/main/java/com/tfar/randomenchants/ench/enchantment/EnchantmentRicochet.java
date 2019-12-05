package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.RICOCHET;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)

public class EnchantmentRicochet extends Enchantment {
  public EnchantmentRicochet() {
    super(Rarity.RARE, RandomEnchants.SHOOTABLE, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("ricochet");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 25;
  }

  @Override
  public int getMaxLevel() {
    return 1;
  }

  @Override
  public boolean canApply(ItemStack stack){
    return Config.ServerConfig.ricochet.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.ricochet.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.ricochet.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
      return Config.ServerConfig.ricochet.get() == NORMAL;
    }


  @SubscribeEvent
  public static void arrowHit(ProjectileImpactEvent event) {
    RayTraceResult result = event.getRayTraceResult();
    if (!(result instanceof BlockRayTraceResult)) return;
    Entity entity = event.getEntity();
    if (!(entity instanceof AbstractArrowEntity)) return;
    AbstractArrowEntity arrow = (AbstractArrowEntity) entity;
    Entity shooter = arrow.getShooter();
    if (!(shooter instanceof PlayerEntity)) return;
    PlayerEntity player = (PlayerEntity) shooter;
    if (!EnchantUtils.hasEnch(player,RICOCHET))return;
    Direction facing = ((BlockRayTraceResult)event.getRayTraceResult()).getFace();
    double x = arrow.getMotion().x;
    double y = arrow.getMotion().y;
    double z = arrow.getMotion().z;

    switch (facing){
      case UP:{
        arrow.setMotion(x,-y, z);break;
      }
      case DOWN:{
        arrow.setMotion(x,-y,z);break;
      }
      case EAST:{
        arrow.setMotion(-x,y,z);break;
      }
      case WEST:{
        arrow.setMotion(-x,y,z);break;
      }
      case NORTH:{
        arrow.setMotion(x,y,-z);break;
      }
      case SOUTH:{
        arrow.setMotion(x,y,-z);break;
      }
      default:{
        throw new IllegalStateException("INVALID ENUM DETECTED: "+facing);
      }
    }
    arrow.velocityChanged = true;
      event.setCanceled(true);
    }
  }


