package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.SWIFT;
import static net.minecraft.enchantment.EnchantmentHelper.getMaxEnchantmentLevel;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)
public class EnchantmentSwift extends Enchantment {

  public EnchantmentSwift() {

    super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("swift");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 5 + 10 * (level - 1);
  }


  @Override
  public int getMaxLevel() {
    return 5;
  }

  @Override
  public boolean canApply(ItemStack stack){
    return Config.ServerConfig.swift.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.swift.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.swift.get() == NORMAL;
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.swift.get() == ANVIL;
  }

  @SubscribeEvent
  public static void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
      PlayerEntity player = event.player;

    if (!EnchantUtils.hasEnch(player,SWIFT)) return;
        int swing = ObfuscationReflectionHelper.getPrivateValue(LivingEntity.class,player,"field_184617_aD");
        swing+=getMaxEnchantmentLevel(SWIFT, player);
        ObfuscationReflectionHelper.setPrivateValue(LivingEntity.class,player,swing,"field_184617_aD");
      }
    }