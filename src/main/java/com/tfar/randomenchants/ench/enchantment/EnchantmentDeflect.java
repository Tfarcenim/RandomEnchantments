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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.DEFLECT;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)

public class EnchantmentDeflect extends Enchantment {
  public EnchantmentDeflect() {
    super(Rarity.RARE, EnchantmentType.ARMOR_CHEST, new EquipmentSlotType[]{
            EquipmentSlotType.CHEST
    });
    this.setRegistryName("deflect");
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
    return Config.ServerConfig.deflect.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.deflect.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.deflect.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.deflect.get() == NORMAL;
  }

  @SubscribeEvent
  public static void playerTick(TickEvent.PlayerTickEvent event) {
    PlayerEntity player = event.player;
    if (player == null) return;
    if (!EnchantUtils.hasEnch(player,DEFLECT)) return;
    World world = player.world;
    double r = 2.7;
    List<Entity> arrows = world.getEntitiesWithinAABB(AbstractArrowEntity.class, new AxisAlignedBB(player.posX - r, player.posY - r, player.posZ - r, player.posX + r, player.posY + r, player.posZ + r));
    if (arrows.size() == 0) return;
    ArrayList<Entity> playerArrows = new ArrayList<>();
    for (Entity arrow : arrows) {
      if (((AbstractArrowEntity) arrow).getShooter() instanceof PlayerEntity) playerArrows.add(arrow);
    }
    if (playerArrows.size() > 0) arrows.removeAll(playerArrows);
    if (arrows.size() == 0) return;
    for (Entity arrow : arrows) {
      arrow.remove();
    }
  }
}


