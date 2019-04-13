package com.tfar.randomenchantments.init;

import com.tfar.randomenchantments.ench.curse.EnchantmentBreakingCurse;
import com.tfar.randomenchantments.ench.curse.EnchantmentButterfingersCurse;
import com.tfar.randomenchantments.ench.curse.EnchantmentFumblingCurse;
import com.tfar.randomenchantments.ench.enchantment.*;
import com.tfar.randomenchantments.util.GlobalVars;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.Map;

import static com.tfar.randomenchantments.EnchantmentConfig.*;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)
public class ModEnchantment {

  //public static final enchantment FLIGHT = new EnchantmentFlight();
  //public static final Enchantment REFLECT = new EnchantmentReflect();
  public static Map<Enchantment, EnumAccessLevel> enchants = new HashMap<>();

  public static final Enchantment FLOATING = new EnchantmentFloating();
  public static final Enchantment INSTANT_DEATH = new EnchantmentInstantDeath();
  public static final Enchantment CURSED_JUMP = new EnchantmentCursedJumping();
  public static final Enchantment PARALYSIS = new EnchantmentParalysis();
  public static final Enchantment TRUE_LIFESTEAL = new EnchantmentTrueLifesteal();

  public static final Enchantment LIGHTNING = new EnchantmentLightning();
  // public static final enchantment TRANSPOSITION = new EnchantmentTransposition();
  public static final Enchantment RANDOMNESS = new EnchantmentRandomness();
  public static final Enchantment DISARM = new EnchantmentDisarm();
  public static final Enchantment HOMING = new EnchantmentHoming();
  public static final Enchantment DEFLECT = new EnchantmentDeflect();
  public static final Enchantment BACK_TO_THE_CHAMBER = new EnchantmentBackToTheChamber();
  public static final Enchantment MOMENTUM = new EnchantmentMomentum();
  public static final Enchantment COMBO = new EnchantmentCombo();
  public static final Enchantment QUICKDRAW = new EnchantmentQuickdraw();
  public static final Enchantment SWIFT = new EnchantmentSwift();


  public static final Enchantment OBSIDIAN_BUSTER = new EnchantmentObsidianBuster();
  public static final Enchantment EQUAL_MINE = new EnchantmentEqualMine();
  public static final Enchantment STONEBOUND = new EnchantmentStonebound();
  public static final Enchantment STONELOVER = new EnchantmentStoneLover();

  //register curses

  public static final Enchantment BUTTERFINGERS = new EnchantmentButterfingersCurse();
  public static final Enchantment FUMBLING = new EnchantmentFumblingCurse();
  public static final Enchantment BREAKING = new EnchantmentBreakingCurse();
  //public static final Enchantment LIGHTNING_ROD = new EnchantmentLightningRodCurse();

  @SubscribeEvent
  public static void registerEnchantments(Register<Enchantment> event) {

    enchants.put(FLOATING,weapons.enableFloating);
    enchants.put(INSTANT_DEATH,weapons.enableInstantDeath);
    enchants.put(CURSED_JUMP,weapons.enableCursedJumping);
    enchants.put(PARALYSIS,weapons.enableParalysis);
    enchants.put(TRUE_LIFESTEAL,weapons.enable2Lifesteal);
    enchants.put(LIGHTNING,weapons.enableLightning);
    enchants.put(DISARM,weapons.enableDisarm);
    enchants.put(HOMING,weapons.enableHoming);
    enchants.put(BACK_TO_THE_CHAMBER,weapons.enableBackToTheChamber);
    enchants.put(DEFLECT,weapons.enableDeflect);
    enchants.put(QUICKDRAW,weapons.enableQuickdraw);
    enchants.put(COMBO,weapons.enableCombo);
    //enchants.put(SWIFT);


    enchants.put(OBSIDIAN_BUSTER,tools.enableObsidianBuster);
    enchants.put(EQUAL_MINE,tools.enableEqualMine);
    enchants.put(STONEBOUND,tools.enableStonebound);
    enchants.put(STONELOVER,tools.enableStonelover);
    enchants.put(RANDOMNESS,tools.enableRandomness);
    enchants.put(MOMENTUM,tools.enableMomentum);

    enchants.put(BUTTERFINGERS,curses.enableButterfingers);
    enchants.put(FUMBLING,curses.enableFumbling);
    enchants.put(BREAKING,curses.enableBreaking);

    //enchants.put(LIGHTNING_ROD,curses.enableLightningRod);

    IForgeRegistry<Enchantment> r = event.getRegistry();

    for (Map.Entry<Enchantment,EnumAccessLevel> enchant : enchants.entrySet())
      //don't register the enchant if disabled and nuclear option is enabled
      if (enchant.getValue() != EnumAccessLevel.DISABLED || !nuclearOption) r.register(enchant.getKey());
  }
}
