package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.MOMENTUM;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)
public class EnchantmentMomentum extends Enchantment {
  public EnchantmentMomentum() {

    super(Rarity.RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("momentum");
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
    return Config.ServerConfig.momentum.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.momentum.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.momentum.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.momentum.get() == NORMAL;
  }

  @SubscribeEvent
  public static void onBreakSpeed(PlayerEvent.BreakSpeed e) {
    PlayerEntity p = e.getEntityPlayer();

    if (EnchantUtils.hasEnch(p, MOMENTUM)) {
      ItemStack stack = p.getHeldItemMainhand();
      CompoundNBT compound = stack.getOrCreateTag();
      int momentum = compound.getInt("momentum");
      float oldSpeed = e.getOriginalSpeed();
      float newSpeed = oldSpeed + .05f * momentum;
      e.setNewSpeed(newSpeed);
    }
  }

  @SubscribeEvent
  public static void onBreak(BlockEvent.BreakEvent e) {
    PlayerEntity p = e.getPlayer();
    if (!EnchantUtils.hasEnch(p,MOMENTUM)) return;
    ItemStack stack = p.getHeldItemMainhand();
    CompoundNBT compound = stack.getOrCreateTag();
    int momentum = compound.getInt("momentum");
    String cachedBlock = compound.getString("block");
    String currentBlock = e.getState().getBlock().toString();
    if (!cachedBlock.equals(currentBlock)) {
      compound.putInt("momentum", 0);
      compound.putString("block", currentBlock);
    } else {
      compound.putInt("momentum", momentum + 1);
    }
  }
}

