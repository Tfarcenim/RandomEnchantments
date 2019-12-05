package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.MAGNETIC;
//todo update
@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)
public class EnchantmentMagnetic extends Enchantment {
  public EnchantmentMagnetic() {

    super(Rarity.RARE, RandomEnchants.TOOLSANDWEAPONS, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("magnetic");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 15;
  }

  @Override
  public int getMaxLevel() {
    return 1;
  }

  @Override
  public boolean canApply(ItemStack stack) {
    return Config.ServerConfig.magnetic.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.magnetic.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.magnetic.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.magnetic.get() == NORMAL;
  }

  @SubscribeEvent
  public static void onHarvestDrops(BlockEvent.HarvestDropsEvent e) {
    PlayerEntity player = e.getHarvester();
    if (player != null && EnchantUtils.hasEnch(player.getHeldItemMainhand(),MAGNETIC))
      e.getDrops().removeIf(player::addItemStackToInventory);
  }

  @SubscribeEvent
  public static void onEnemyKilled(LivingDropsEvent e) {
    Entity attacker = e.getSource().getTrueSource();

    if (attacker instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity)attacker;
      if (EnchantUtils.hasEnch(player.getHeldItemMainhand(),MAGNETIC)) {
        List<ItemStack> stacks = getStacksFromEntityItems(e.getDrops());

        for (ItemEntity entityItem : e.getDrops()){
          if (player.addItemStackToInventory(entityItem.getItem())) stacks.remove(entityItem.getItem());
        }
      }
    }
  }

  public static List<ItemStack> getStacksFromEntityItems(Collection<ItemEntity> l){

    List<ItemStack> stacks = new ArrayList<>();
    for (ItemEntity item : l){
      stacks.add(item.getItem());
    }
    return stacks;
  }
}

