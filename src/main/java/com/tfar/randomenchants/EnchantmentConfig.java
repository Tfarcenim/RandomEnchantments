package com.tfar.randomenchants;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.ANVIL;
import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.NORMAL;
import static com.tfar.randomenchants.RandomEnchants.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class EnchantmentConfig {

  private static final String g = " Enchantment Config";

  private static final String f = "Enable ";

  public static boolean nuclearOption = false;

  public static ConfigWeapons weapons = new ConfigWeapons();

  public static ConfigTools tools = new ConfigTools();

  public static ConfigCurses curses = new ConfigCurses();

  public static class ConfigWeapons {

    public EnumAccessLevel enableBackToTheChamber = NORMAL;

    public EnumAccessLevel enableTorches = NORMAL;

    public EnumAccessLevel enablePhasing = NORMAL;

    public EnumAccessLevel enableExploding = NORMAL;

    public EnumAccessLevel enablePiercing = NORMAL;

    public EnumAccessLevel enableCombo = NORMAL;

    public EnumAccessLevel enableCursedJumping = NORMAL;

    public EnumAccessLevel enableDeflect = NORMAL;

    public EnumAccessLevel enableDisarm = NORMAL;

    public EnumAccessLevel enableFloating = NORMAL;

    public EnumAccessLevel enableHoming = NORMAL;

    public EnumAccessLevel enableInstantDeath = NORMAL;

    public EnumAccessLevel enableLightning = NORMAL;

    public EnumAccessLevel enableParalysis = NORMAL;

    public EnumAccessLevel enableQuickdraw = NORMAL;

    public EnumAccessLevel enableSolar = NORMAL;

    public EnumAccessLevel enableTrueShot = NORMAL;

    public EnumAccessLevel enable2Lifesteal = NORMAL;

    public EnumAccessLevel enableTransposition = NORMAL;

    public EnumAccessLevel enableTeleportation = NORMAL;

    public EnumAccessLevel enableShattering = NORMAL;

    public EnumAccessLevel enableSwift = NORMAL;

    public EnumAccessLevel enableRicochet = NORMAL;

    public EnumAccessLevel enableHarvesting = NORMAL;

    public EnumAccessLevel enableReflect = NORMAL;

    public EnumAccessLevel enableFastPlacing = NORMAL;
  }

  public static class ConfigTools{
    public EnumAccessLevel enableEqualMine = NORMAL;

    public EnumAccessLevel enableHooked = NORMAL;

    public EnumAccessLevel enableLumberjack = NORMAL;

    public EnumAccessLevel enableMomentum = NORMAL;

    public EnumAccessLevel enableObsidianBuster = NORMAL;

    public EnumAccessLevel enableGrappling = NORMAL;

    public EnumAccessLevel enableRandomness = NORMAL;

    public EnumAccessLevel enableStonebound = NORMAL;

    public EnumAccessLevel enableStonelover = NORMAL;

    public EnumAccessLevel enableMagnetic = NORMAL;

    public EnumAccessLevel enableResistance = NORMAL;

    public EnumAccessLevel enableEternal = NORMAL;

    public EnumAccessLevel enableGlobalTraveler = NORMAL;
  }

  public static class ConfigCurses {
    public EnumAccessLevel enableBreaking = ANVIL;

    public EnumAccessLevel enableButterfingers = ANVIL;

    public EnumAccessLevel enableFumbling = ANVIL;

    public EnumAccessLevel enableShadow = ANVIL;
  }
  public enum EnumAccessLevel {DISABLED,ANVIL,NORMAL}

  @SubscribeEvent
  public static void onConfigChanged(ConfigChangedEvent e){
    if (e.getModID().equals(MOD_ID)){
 //     ConfigManager.sync(MOD_ID);
    }
  }
}
