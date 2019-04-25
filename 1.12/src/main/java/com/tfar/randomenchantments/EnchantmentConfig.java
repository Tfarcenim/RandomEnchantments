package com.tfar.randomenchantments;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchantments.util.GlobalVars.MOD_ID;
import static net.minecraftforge.common.config.Config.Type.*;

@Config(modid = MOD_ID)
@Mod.EventBusSubscriber(modid = MOD_ID)
public class EnchantmentConfig {

  private static final String g = " Enchantment Config";

  private static final String f = "Enable ";

  @Config.Name("Nuclear Option")
  @Config.Comment("Prevents disabled enchantments from being registered, use with caution")
  public static boolean nuclearOption = false;

  @Config.Comment("Combat"+g)
  public static ConfigWeapons weapons = new ConfigWeapons();

  @Config.Comment("Tool"+g)
  public static ConfigTools tools = new ConfigTools();

  @Config.Comment("Curse"+g)
  public static ConfigCurses curses = new ConfigCurses();

  public static class ConfigWeapons {

    @Config.Name(f+"Back to the chamber")
    public EnumAccessLevel enableBackToTheChamber = NORMAL;

    @Config.Name(f+"Phasing")
    public EnumAccessLevel enablePhasing = NORMAL;

    @Config.Name(f+"Combo")
    public EnumAccessLevel enableCombo = NORMAL;

    @Config.Name(f+"Cursed Jumping")
    public EnumAccessLevel enableCursedJumping = NORMAL;

    @Config.Name(f+"Deflect")
    public EnumAccessLevel enableDeflect = NORMAL;

    @Config.Name(f+"Disarm")
    public EnumAccessLevel enableDisarm = NORMAL;

    @Config.Name(f+"Floating")
    public EnumAccessLevel enableFloating = NORMAL;

    @Config.Name(f+"Homing")
    public EnumAccessLevel enableHoming = NORMAL;

    @Config.Name(f+"Instant Death")
    public EnumAccessLevel enableInstantDeath = NORMAL;

    @Config.Name(f+"Lightning")
    public EnumAccessLevel enableLightning = NORMAL;

    @Config.Name(f+"Paralysis")
    public EnumAccessLevel enableParalysis = NORMAL;

    @Config.Name(f+"Quickdraw")
    public EnumAccessLevel enableQuickdraw = NORMAL;

    @Config.Name(f+"Solar")
    public EnumAccessLevel enableSolar = NORMAL;

    @Config.Name(f+"True Shot")
    public EnumAccessLevel enableTrueShot = NORMAL;

    @Config.Name(f+"2 Lifesteal")
    public EnumAccessLevel enable2Lifesteal = NORMAL;

    @Config.Name(f+"Teleposistion")
    public EnumAccessLevel enableTransposition = NORMAL;

    @Config.Name(f+"Teleportation")
    public EnumAccessLevel enableTeleportation = NORMAL;
  }

  public static class ConfigTools{
    @Config.Name(f+"Equal Mine")
    public EnumAccessLevel enableEqualMine = NORMAL;

    @Config.Name(f+"Snatching")
    public EnumAccessLevel enableHooked = NORMAL;

    @Config.Name(f+"Lumberjack")
    public EnumAccessLevel enableLumberjack = NORMAL;

    @Config.Name(f+"Momentum")
    public EnumAccessLevel enableMomentum = NORMAL;

    @Config.Name(f+"Obsidian Buster")
    public EnumAccessLevel enableObsidianBuster = NORMAL;

    @Config.Name(f+"Grappling")
    public EnumAccessLevel enableGrappling = NORMAL;

    @Config.Name(f+"Randomness")
    public EnumAccessLevel enableRandomness = NORMAL;

    @Config.Name(f+"Stonebound")
    public EnumAccessLevel enableStonebound = NORMAL;

    @Config.Name(f+"Stonelover")
    public EnumAccessLevel enableStonelover = NORMAL;
  }

  public static class ConfigCurses {
    @Config.Name(f+"Breaking")
    public EnumAccessLevel enableBreaking = ANVIL;

    @Config.Name(f+"Butterfingers")
    public EnumAccessLevel enableButterfingers = ANVIL;

    @Config.Name(f+"Fumbling")
    public EnumAccessLevel enableFumbling = ANVIL;

    @Config.Name(f+"Shadow")
    public EnumAccessLevel enableShadow = ANVIL;
  }
  public enum EnumAccessLevel {DISABLED,ANVIL,NORMAL}

  @SubscribeEvent
  public static void onConfigChanged(ConfigChangedEvent e){
    if (e.getModID().equals(MOD_ID)){
      ConfigManager.sync(MOD_ID, INSTANCE);
    }
  }
}
