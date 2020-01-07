package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.SNATCHING;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)

public class EnchantmentSnatching extends Enchantment {
  public EnchantmentSnatching() {
    super(Rarity.RARE, EnchantmentType.FISHING_ROD, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("hooked");
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
  public boolean canApply(ItemStack stack){
    return Config.ServerConfig.snatching.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.snatching.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.snatching.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.snatching.get() == NORMAL;
  }

  private static ArrayList<EquipmentSlotType> list = new ArrayList<>();

  static {
    list.add(EquipmentSlotType.HEAD);
    list.add(EquipmentSlotType.CHEST);
    list.add(EquipmentSlotType.LEGS);
    list.add(EquipmentSlotType.FEET);
  }

  @SubscribeEvent
  public static void playerHook(PlayerInteractEvent e) {
    if (e instanceof PlayerInteractEvent.EntityInteract || e instanceof PlayerInteractEvent.EntityInteractSpecific)return;
    PlayerEntity player = e.getPlayer();
    if (e.getPlayer().fishingBobber == null || player.world.isRemote) return;
    FishingBobberEntity hook = player.fishingBobber;
    Entity entity = hook.caughtEntity;
    if (!(entity instanceof LivingEntity) || !EnchantUtils.hasEnch(player, SNATCHING)) return;
    LivingEntity victim = (LivingEntity)entity;
    ItemStack piece = removeArmor(victim);
    if (piece == null) return;
    BlockPos pos = victim.getPosition();
    ItemEntity entityItem = new ItemEntity(victim.world, pos.getX(), pos.getY(), pos.getZ(), piece);
    Vec3d veloctiyVector = new Vec3d(player.func_226277_ct_()-victim.func_226277_ct_(),player.func_226278_cu_()-victim.func_226278_cu_(),player.func_226281_cx_()-victim.func_226281_cx_());
    spawnItemWithVelocity(veloctiyVector,entityItem);
  }
  public static ItemStack removeArmor(LivingEntity victim) {
    ItemStack stack = null;
    for (EquipmentSlotType slot :list){
      if (victim.getItemStackFromSlot(slot).isEmpty())continue;
      stack = victim.getItemStackFromSlot(slot);
      victim.setItemStackToSlot(slot,ItemStack.EMPTY);
      break;
    }
    return stack;
  }
  public static void spawnItemWithVelocity(Vec3d vec, ItemEntity entityItem){
    entityItem.setMotion(vec);
    entityItem.world.addEntity(entityItem);
  }
}



