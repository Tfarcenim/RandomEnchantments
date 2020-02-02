package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantmentUtils;
import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.tools;
import static com.tfar.randomenchants.init.ModEnchantment.MAGNETIC;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)
public class EnchantmentMagnetic extends Enchantment {
  public EnchantmentMagnetic() {

    super(Rarity.RARE, RandomEnchants.TOOLSANDWEAPONS, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("magnetic");
    this.setName("magnetic");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 15;
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
  public boolean canApply(ItemStack stack) {
    return tools.enableMagnetic != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return tools.enableMagnetic == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return tools.enableMagnetic != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return tools.enableMagnetic == NORMAL;
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void onHarvestDrops(BlockEvent.HarvestDropsEvent e) {
    EntityPlayer player = e.getHarvester();
    if (player != null && EnchantmentUtils.stackHasEnch(player.getHeldItemMainhand(),MAGNETIC))
      e.getDrops().removeIf(player::addItemStackToInventory);
  }

  @SubscribeEvent
  public static void onEnemyKilled(LivingDropsEvent e) {
    Entity attacker = e.getSource().getTrueSource();

    if (attacker instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer)attacker;
      if (EnchantmentUtils.stackHasEnch(player.getHeldItemMainhand(),MAGNETIC)) {
        List<ItemStack> stacks = getStacksFromEntityItems(e.getDrops());

        for (EntityItem entityItem : e.getDrops()){
          if (player.addItemStackToInventory(entityItem.getItem())) stacks.remove(entityItem.getItem());
        }
      }
    }
  }

  public static List<ItemStack> getStacksFromEntityItems(List<EntityItem> l){

    List<ItemStack> stacks = new ArrayList<>();
    for (EntityItem item : l){
      stacks.add(item.getItem());
    }
    return stacks;
  }
}

