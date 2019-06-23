package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.EXPLODING;

@Mod.EventBusSubscriber(modid = RandomEnchants.MOD_ID)

public class EnchantmentExploding extends Enchantment {
  public EnchantmentExploding() {
    super(Rarity.RARE, EnchantmentType.BOW, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("exploding");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 5 + 10 * (level - 1);
  }

  @Override
  public int getMaxLevel() {
    return 5;
  }

  @Override
  public boolean canApply(ItemStack stack){
    return weapons.enableExploding != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableExploding == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableExploding != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  protected boolean canApplyTogether(Enchantment ench) {
    return super.canApplyTogether(ench) && !(ench instanceof InfinityEnchantment);
  }

  @Override
  public boolean isAllowedOnBooks() {
      return weapons.enableBackToTheChamber == NORMAL;
    }


  @SubscribeEvent
  public static void arrowHit(ProjectileImpactEvent event) {
    Entity arrow = event.getEntity();
    RayTraceResult result = event.getRayTraceResult();
    if (!(result instanceof EntityRayTraceResult))return;
    Entity victim = ((EntityRayTraceResult) result).getEntity();
    if(EnchantUtils.isArrowAndIsLivingBase(arrow, victim))return;
    PlayerEntity player = (PlayerEntity)((AbstractArrowEntity)arrow).getShooter();
    int level = EnchantmentHelper.getMaxEnchantmentLevel(EXPLODING, player);
    if (level == 0)return;
    if (victim.hurtResistantTime>0)return;
    if (!player.world.isRemote) {victim.world.createExplosion(null,victim.posX,victim.posY,victim.posZ,level, Explosion.Mode.DESTROY);
    arrow.remove();
      }
    }
  }


