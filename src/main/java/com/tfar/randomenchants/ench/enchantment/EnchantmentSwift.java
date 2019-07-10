package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.init.ModEnchantment.*;
import static net.minecraft.enchantment.EnchantmentHelper.getMaxEnchantmentLevel;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)
public class EnchantmentSwift extends Enchantment {

  public EnchantmentSwift() {

    super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("swift");
    this.setName("swift");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 5 + 10 * (level - 1);
  }

  @Override
  public int getMaxEnchantability(int level) {
    return super.getMinEnchantability(level) + 25;
  }

  @Override
  public int getMaxLevel() {
    return 5;
  }

  @Override
  public boolean canApply(ItemStack stack){
    return weapons.enableSwift != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableSwift != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return weapons.enableSwift == NORMAL;
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableSwift == ANVIL;
  }

  @SubscribeEvent
  public static void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
      EntityPlayer player = event.player;

    if (getMaxEnchantmentLevel(SWIFT, player) <= 0) return;
        int swing = ObfuscationReflectionHelper.getPrivateValue(EntityLivingBase.class,player,"field_184617_aD");
        swing+=getMaxEnchantmentLevel(SWIFT, player);
        ObfuscationReflectionHelper.setPrivateValue(EntityLivingBase.class,player,swing,"field_184617_aD");
      }
    }