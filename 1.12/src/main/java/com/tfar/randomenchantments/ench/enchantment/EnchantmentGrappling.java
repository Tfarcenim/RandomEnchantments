package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchantments.EnchantmentConfig.tools;
import static com.tfar.randomenchantments.init.ModEnchantment.PULLING;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentGrappling extends Enchantment {
  public EnchantmentGrappling() {
    super(Rarity.RARE, EnumEnchantmentType.FISHING_ROD, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("grappling");
    this.setName("grappling");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 30;
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
  public boolean canApply(@Nonnull ItemStack stack){
    return tools.enableGrappling != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return tools.enableGrappling == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return tools.enableGrappling != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return tools.enableGrappling == NORMAL;
  }

  @SubscribeEvent
  public static void playerTick(PlayerInteractEvent e) {
    if (e instanceof PlayerInteractEvent.EntityInteract || e instanceof PlayerInteractEvent.EntityInteractSpecific)
      return;
    EntityPlayer player = e.getEntityPlayer();
    if (player.world.isRemote) return;
    if (e.getEntityPlayer().fishEntity != null) {
      EntityFishHook hook = player.fishEntity;
      Entity entity = hook.caughtEntity;
      if (EnchantmentHelper.getMaxEnchantmentLevel(PULLING, player) == 0) return;
      if (entity != null) {
        Vec3d veloctiyVector = new Vec3d(player.posX - entity.posX, player.posY - entity.posY, player.posZ - entity.posZ);
        entity.addVelocity(veloctiyVector.x,veloctiyVector.y,veloctiyVector.z);
        entity.velocityChanged = true;
      } else {
        double speed = sqrt(pow(hook.motionX,2)+pow(hook.motionY,2)+pow(hook.motionZ,2));
        if (hook.isInWater()||(speed >.01))return;
        Vec3d veloctiyVector = new Vec3d(hook.posX - player.posX, hook.posY - player.posY, hook.posZ - player.posZ);
        player.addVelocity(veloctiyVector.x,veloctiyVector.y,veloctiyVector.z);
        player.velocityChanged = true;
      }
    }
  }
}



