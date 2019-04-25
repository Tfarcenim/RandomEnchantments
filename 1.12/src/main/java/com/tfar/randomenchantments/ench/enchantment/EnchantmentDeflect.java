package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.EnchantmentConfig;
import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.init.ModEnchantment.DEFLECT;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentDeflect extends Enchantment {
  public EnchantmentDeflect() {
    super(Rarity.RARE, EnumEnchantmentType.ARMOR_CHEST, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.CHEST
    });
    this.setRegistryName("deflect");
    this.setName("deflect");
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
    return weapons.enableDeflect != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableDeflect == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableDeflect == NORMAL && super.canApplyAtEnchantingTable(stack);
  }

  @SubscribeEvent
  public static void playerTick(TickEvent.PlayerTickEvent event) {
    EntityPlayer player = event.player;
    if (player == null) return;
    if (EnchantmentHelper.getMaxEnchantmentLevel(DEFLECT, player) == 0) return;
    World world = player.world;
    double r = 2.7;
    List<Entity> arrows = world.getEntitiesWithinAABB(EntityArrow.class, new AxisAlignedBB(player.posX - r, player.posY - r, player.posZ - r, player.posX + r, player.posY + r, player.posZ + r));
    if (arrows.size() == 0) return;
    ArrayList<Entity> playerArrows = new ArrayList<>();
    for (Entity arrow : arrows) {
      if (((EntityArrow) arrow).shootingEntity instanceof EntityPlayer) playerArrows.add(arrow);
    }
    if (playerArrows.size() > 0) arrows.removeAll(playerArrows);
    if (arrows.size() == 0) return;
    for (Entity arrow : arrows) {
      arrow.setDead();
    }
  }
}


