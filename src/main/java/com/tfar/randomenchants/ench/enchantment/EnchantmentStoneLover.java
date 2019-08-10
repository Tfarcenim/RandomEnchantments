package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.STONELOVER;
import static com.tfar.randomenchants.RandomEnchants.PICKAXE;

@Mod.EventBusSubscriber(modid= RandomEnchants.MODID)
public class EnchantmentStoneLover extends Enchantment{
  public EnchantmentStoneLover() {

    super(Rarity.RARE, PICKAXE, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("stonelover");
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
  public boolean canApply(ItemStack stack){
    return Config.ServerConfig.stonelover.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.stonelover.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.stonelover.get() == NORMAL;
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.stonelover.get() == ANVIL;
  }

  @SubscribeEvent
  public static void onBlockBreak(BlockEvent.BreakEvent e) {
    PlayerEntity p = e.getPlayer();
    if (EnchantUtils.hasEnch(p,STONELOVER)){
      if(e.getState().getBlock() != Blocks.STONE)return;
      ItemStack stack = p.getHeldItemMainhand();
      if (Math.random()>.80)return;
      stack.setDamage(stack.getDamage()-1);
    }
  }
}

