package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.GRAPPLING;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)

public class EnchantmentGrappling extends Enchantment {
  public EnchantmentGrappling() {
    super(Rarity.RARE, EnchantmentType.FISHING_ROD, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("grappling");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 30;
  }

  @Override
  public int getMaxLevel() {
    return 1;
  }

  @Override
  public boolean canApply(@Nonnull ItemStack stack){
    return Config.ServerConfig.grappling.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.grappling.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.grappling.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.grappling.get() == NORMAL;
  }

  @SubscribeEvent
  public static void playerGrapple(PlayerInteractEvent e) {
    if (e instanceof PlayerInteractEvent.EntityInteract || e instanceof PlayerInteractEvent.EntityInteractSpecific)
      return;
    PlayerEntity player = e.getPlayer();
    if (player.world.isRemote) return;
    if (e.getPlayer().fishingBobber != null) {
      FishingBobberEntity hook = player.fishingBobber;
      Entity entity = hook.caughtEntity;
      if (EnchantmentHelper.getMaxEnchantmentLevel(GRAPPLING, player) == 0) return;
      if (entity != null) {
        Vec3d veloctiyVector = new Vec3d(player.getPosX() - entity.getPosX(),
                player.getPosY() - entity.getPosY(), player.getPosZ()
                - entity.getPosZ());
        entity.addVelocity(veloctiyVector.x,veloctiyVector.y,veloctiyVector.z);
        entity.velocityChanged = true;
      } else {
        double speed = hook.getMotion().length();
        if (hook.isInWater()||(speed >.01))return;
        Vec3d veloctiyVector = new Vec3d(hook.getPosX() - player.getPosX(),
                hook.getPosY() - player.getPosY(),
                hook.getPosZ() - player.getPosZ());
        player.addVelocity(veloctiyVector.x,veloctiyVector.y,veloctiyVector.z);
        player.velocityChanged = true;
      }
    }
  }
}



