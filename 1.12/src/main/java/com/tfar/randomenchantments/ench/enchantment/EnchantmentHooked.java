package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchantments.EnchantmentConfig.tools;
import static com.tfar.randomenchantments.init.ModEnchantment.HOOKED;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentHooked extends Enchantment {
  public EnchantmentHooked() {
    super(Rarity.RARE, EnumEnchantmentType.FISHING_ROD, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("hooked");
    this.setName("hooked");
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
  public boolean canApply(ItemStack stack){
    return tools.enableHooked != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return tools.enableHooked == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return tools.enableHooked != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return tools.enableHooked == NORMAL;
  }

  private static ArrayList<EntityEquipmentSlot> list = new ArrayList<>();

  static {
    list.add(EntityEquipmentSlot.HEAD);
    list.add(EntityEquipmentSlot.CHEST);
    list.add(EntityEquipmentSlot.LEGS);
    list.add(EntityEquipmentSlot.FEET);
  }

  @SubscribeEvent
  public static void playerTick(PlayerInteractEvent e) {
    if (e instanceof PlayerInteractEvent.EntityInteract || e instanceof PlayerInteractEvent.EntityInteractSpecific)return;
    EntityPlayer player = e.getEntityPlayer();
    if (e.getEntityPlayer().fishEntity == null || player.world.isRemote) return;
    EntityFishHook hook = player.fishEntity;
    Entity entity = hook.caughtEntity;
    if (!(entity instanceof EntityLivingBase) || EnchantmentHelper.getMaxEnchantmentLevel(HOOKED, player) == 0) return;
    EntityLivingBase victim = (EntityLivingBase)entity;
    ItemStack piece = removeArmor(victim);
    if (piece == null) return;
    BlockPos pos = victim.getPosition();
    EntityItem entityItem = new EntityItem(victim.world, pos.getX(), pos.getY(), pos.getZ(), piece);
    Vec3d veloctiyVector = new Vec3d(player.posX-victim.posX,player.posY-victim.posY,player.posZ-victim.posZ);
    spawnItemWithVelocity(veloctiyVector,entityItem);
  }
  public static ItemStack removeArmor(EntityLivingBase victim) {
    ItemStack stack = null;
    for (EntityEquipmentSlot slot :list){
      if (victim.getItemStackFromSlot(slot).isEmpty())continue;
      stack = victim.getItemStackFromSlot(slot);
      victim.setItemStackToSlot(slot,ItemStack.EMPTY);
      break;
    }
    return stack;
  }
  public static void spawnItemWithVelocity(Vec3d vec,EntityItem entityItem){
    entityItem.motionX = vec.x;
    entityItem.motionY = vec.y;
    entityItem.motionZ = vec.z;
    entityItem.world.spawnEntity(entityItem);
  }
}



