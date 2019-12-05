package com.tfar.randomenchants.init;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.ench.curse.EnchantmentBreakingCurse;
import com.tfar.randomenchants.ench.curse.EnchantmentButterfingersCurse;
import com.tfar.randomenchants.ench.curse.EnchantmentFumblingCurse;
import com.tfar.randomenchants.ench.curse.EnchantmentShadowCurse;
import com.tfar.randomenchants.ench.enchantment.*;
import com.tfar.randomenchants.util.EventHandler;
import com.tfar.randomenchants.util.GlobalVars;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.Map;

import static com.tfar.randomenchants.EnchantmentConfig.*;
import static com.tfar.randomenchants.RandomEnchants.setup;

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
  public static final Enchantment TRUE_SHOT = new EnchantmentTrueShot();
  public static final Enchantment HOOKED = new EnchantmentHooked();
  public static final Enchantment PHASING = new EnchantmentPhasing();
  public static final Enchantment LIGHTING = new EnchantmentTorches();


  public static Enchantment MULTISHOT = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation("cofhcore","multishot"));

  public static final Enchantment REFLECT = new EnchantmentReflect();
  public static final Enchantment EXPLODING = new EnchantmentExploding();
  public static final Enchantment LIGHTNING = new EnchantmentLightning();
  public static final Enchantment TRANSPOSITION = new EnchantmentTransposition();
  public static final Enchantment RANDOMNESS = new EnchantmentRandomness();
  public static final Enchantment DISARM = new EnchantmentDisarm();
  public static final Enchantment HOMING = new EnchantmentHoming();
  public static final Enchantment DEFLECT = new EnchantmentDeflect();
  public static final Enchantment BACK_TO_THE_CHAMBER = new EnchantmentBackToTheChamber();
  public static final Enchantment MOMENTUM = new EnchantmentMomentum();
  public static final Enchantment QUICKDRAW = new EnchantmentQuickdraw();
  public static final Enchantment SWIFT = new EnchantmentSwift();
  public static final Enchantment PULLING = new EnchantmentGrappling();
  public static final Enchantment TELEPORTATON = new EnchantmentTeleportation();
  public static final Enchantment SOLAR = new EnchantmentSolar();
  public static final Enchantment LUMBERJACK = new EnchantmentLumberjack();
  public static final Enchantment SHATTERING = new EnchantmentShattering();
  public static final Enchantment PIERCING = new EnchantmentPiercing();
  public static final Enchantment MAGNETIC = new EnchantmentMagnetic();
  public static final Enchantment RESISTANCE = new EnchantmentResistant();
  public static final Enchantment ETERNAL = new EnchantmentEternal();
  public static final Enchantment GLOBAL_TRAVELLER = new EnchantmentGlobalTraveler();


  public static final Enchantment OBSIDIAN_BUSTER = new EnchantmentObsidianBuster();
  public static final Enchantment EQUAL_MINE = new EnchantmentEqualMine();
  public static final Enchantment STONEBOUND = new EnchantmentStonebound();
  public static final Enchantment STONELOVER = new EnchantmentStoneLover();
  public static final Enchantment RICOCHET = new EnchantmentRicochet();
  public static final Enchantment HARVEST = new EnchantmentHarvesting();



  //register curses

  public static final Enchantment BUTTERFINGERS = new EnchantmentButterfingersCurse();
  public static final Enchantment FUMBLING = new EnchantmentFumblingCurse();
  public static final Enchantment BREAKING = new EnchantmentBreakingCurse();
  public static final Enchantment SHADOW = new EnchantmentShadowCurse();

  @SubscribeEvent
  public static void registerEnchantments(Register<Enchantment> event) {

    enchants.put(REFLECT,weapons.enableReflect);//
    enchants.put(SWIFT,weapons.enableSwift);
    enchants.put(MAGNETIC,tools.enableMagnetic);
    enchants.put(HARVEST,weapons.enableHarvesting);
    enchants.put(LIGHTING,weapons.enableTorches);
    enchants.put(EXPLODING,weapons.enableExploding);
    enchants.put(PIERCING,weapons.enablePiercing);
    enchants.put(RICOCHET,weapons.enableRicochet);
    enchants.put(SHATTERING,weapons.enableShattering);
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
    enchants.put(SOLAR,weapons.enableSolar);
    enchants.put(TRUE_SHOT,weapons.enableTrueShot);
    enchants.put(TELEPORTATON,weapons.enableTeleportation);
    enchants.put(TRANSPOSITION,weapons.enableTransposition);
    enchants.put(LUMBERJACK,tools.enableLumberjack);
    enchants.put(PHASING,weapons.enablePhasing);
    enchants.put(OBSIDIAN_BUSTER,tools.enableObsidianBuster);
    enchants.put(EQUAL_MINE,tools.enableEqualMine);
    enchants.put(STONEBOUND,tools.enableStonebound);
    enchants.put(STONELOVER,tools.enableStonelover);
    enchants.put(RANDOMNESS,tools.enableRandomness);
    enchants.put(MOMENTUM,tools.enableMomentum);
    enchants.put(HOOKED,tools.enableHooked);
    enchants.put(PULLING,tools.enableGrappling);
    enchants.put(RESISTANCE,tools.enableResistance);
    enchants.put(ETERNAL,tools.enableEternal);
    enchants.put(GLOBAL_TRAVELLER,tools.enableGlobalTraveler);

    enchants.put(BUTTERFINGERS,curses.enableButterfingers);
    enchants.put(FUMBLING,curses.enableFumbling);
    enchants.put(BREAKING,curses.enableBreaking);
    enchants.put(SHADOW,curses.enableShadow);


    IForgeRegistry<Enchantment> r = event.getRegistry();

    for (Map.Entry<Enchantment,EnumAccessLevel> enchant : enchants.entrySet())
      //don't register the enchant if disabled and nuclear option is enabled
      if (enchant.getValue() != EnumAccessLevel.DISABLED || !nuclearOption) {
        r.register(enchant.getKey());
      }
    RandomEnchants.logger.info("Registered "+enchants.size()+" enchantments!");
  }

  public static void registerEvents(){
    MinecraftForge.EVENT_BUS.register(new EventHandler());
    if (tools.enableGlobalTraveler != EnumAccessLevel.DISABLED) {
      MinecraftForge.EVENT_BUS.register(GLOBAL_TRAVELLER);
      EnchantmentGlobalTraveler.KEY = GLOBAL_TRAVELLER.getRegistryName().toString();
    }
    if (tools.enableRandomness != EnumAccessLevel.DISABLED){
      setup();
    }
  }
}
