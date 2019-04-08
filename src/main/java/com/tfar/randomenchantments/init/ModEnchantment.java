package com.tfar.randomenchantments.init;

import com.tfar.randomenchantments.ench.curse.EnchantmentBreakingCurse;
import com.tfar.randomenchantments.ench.curse.EnchantmentButterfingersCurse;
import com.tfar.randomenchantments.ench.curse.EnchantmentFumblingCurse;
import com.tfar.randomenchantments.ench.enchantment.*;
import com.tfar.randomenchantments.util.GlobalVars;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)
public class ModEnchantment {


  //public static final enchantment FLIGHT = new EnchantmentFlight();
  //public static final Enchantment REFLECT = new EnchantmentReflect();
  public static final Enchantment FLOATING = new EnchantmentFloating();
  public static final Enchantment INSTANT_DEATH = new EnchantmentInstantDeath();
  public static final Enchantment CURSED_JUMP = new EnchantmentCursedJumping();
  public static final Enchantment PARALYSIS = new EnchantmentParalysis();
  public static final Enchantment TRUE_LIFESTEAL = new EnchantmentTrueLifesteal();
  public static final Enchantment OBSIDIAN_BUSTER = new EnchantmentObsidianBuster();
  public static final Enchantment EQUAL_MINE = new EnchantmentEqualMine();
  public static final Enchantment LIGHTNING = new EnchantmentLightning();
  // public static final enchantment TRANSPOSITION = new EnchantmentTransposition();
  public static final Enchantment RANDOMNESS = new EnchantmentRandomness();
  public static final Enchantment DISARM = new EnchantmentDisarm();
  public static final Enchantment HOMING = new EnchantmentHoming();
  public static final Enchantment DEFLECT = new EnchantmentDeflect();
  public static final Enchantment STONEBOUND = new EnchantmentStonebound();
  public static final Enchantment STONELOVER = new EnchantmentStoneLover();
  public static final Enchantment BACK_TO_THE_CHAMBER = new EnchantmentBackToTheChamber();
  public static final Enchantment MOMENTUM = new EnchantmentMomentum();
  public static final Enchantment COMBO = new EnchantmentCombo();
  public static final Enchantment QUICKDRAW = new EnchantmentQuickdraw();

  //register curses

  public static final Enchantment BUTTERFINGERS = new EnchantmentButterfingersCurse();
  public static final Enchantment FUMBLING = new EnchantmentFumblingCurse();
  public static final Enchantment BREAKING = new EnchantmentBreakingCurse();


  @SubscribeEvent
  public static void registerEnchantments(Register<Enchantment> event) {
    event.getRegistry().registerAll(INSTANT_DEATH, FLOATING, CURSED_JUMP, PARALYSIS, TRUE_LIFESTEAL,
            OBSIDIAN_BUSTER, EQUAL_MINE, LIGHTNING, RANDOMNESS, DISARM, HOMING, DEFLECT, STONEBOUND,
            BACK_TO_THE_CHAMBER, STONELOVER, BUTTERFINGERS, FUMBLING, BREAKING, MOMENTUM, COMBO, QUICKDRAW);
  }

}
