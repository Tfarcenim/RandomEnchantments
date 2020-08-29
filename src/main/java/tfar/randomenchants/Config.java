package tfar.randomenchants;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

import static tfar.randomenchants.Config.Restriction.*;
import static tfar.randomenchants.RandomEnchants.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class Config {

  private static final String g = " Enchantment Config";

  private static final String f = "Enable ";

  //this will no longer work with forge configs, need to find a better way
  public static boolean nuclearOption = false;

  public static final ServerConfig SERVER;
  public static final ForgeConfigSpec SERVER_SPEC;

  static {
    final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
    SERVER_SPEC = specPair.getRight();
    SERVER = specPair.getLeft();
  }

  public static final String s1 = "NORMAL,ANVIL,DISABLED";

  public static class ServerConfig {

    public static ForgeConfigSpec.EnumValue<Restriction> backtothechamber;
    public static ForgeConfigSpec.EnumValue<Restriction> torches;
    public static ForgeConfigSpec.EnumValue<Restriction> combo;
    public static ForgeConfigSpec.EnumValue<Restriction> cursedjumping;
    public static ForgeConfigSpec.EnumValue<Restriction> deflect;
    public static ForgeConfigSpec.EnumValue<Restriction> disarm;
    public static ForgeConfigSpec.EnumValue<Restriction> discord;
    public static ForgeConfigSpec.EnumValue<Restriction> equalmine;
    public static ForgeConfigSpec.EnumValue<Restriction> eternal;
    public static ForgeConfigSpec.EnumValue<Restriction> exploding;
    public static ForgeConfigSpec.EnumValue<Restriction> floating;
    public static ForgeConfigSpec.EnumValue<Restriction> grappling;
    public static ForgeConfigSpec.EnumValue<Restriction> harvest;
    public static ForgeConfigSpec.EnumValue<Restriction> homing;
    public static ForgeConfigSpec.EnumValue<Restriction> instant_death;
    public static ForgeConfigSpec.EnumValue<Restriction> lightning;
    public static ForgeConfigSpec.EnumValue<Restriction> lumberjack;
    public static ForgeConfigSpec.EnumValue<Restriction> momentum;
    public static ForgeConfigSpec.EnumValue<Restriction> obsidian_buster;
    public static ForgeConfigSpec.EnumValue<Restriction> paralysis;
    public static ForgeConfigSpec.EnumValue<Restriction> phasing;
    public static ForgeConfigSpec.EnumValue<Restriction> quickdraw;
    public static ForgeConfigSpec.EnumValue<Restriction> dungeoneering;
    public static ForgeConfigSpec.EnumValue<Restriction> reflect;
    public static ForgeConfigSpec.EnumValue<Restriction> resistant;
    public static ForgeConfigSpec.EnumValue<Restriction> ricochet;
    public static ForgeConfigSpec.EnumValue<Restriction> shattering;
    public static ForgeConfigSpec.EnumValue<Restriction> silverfish;
    public static ForgeConfigSpec.EnumValue<Restriction> snatching;
    public static ForgeConfigSpec.EnumValue<Restriction> solar;
    public static ForgeConfigSpec.EnumValue<Restriction> stonebound;
    public static ForgeConfigSpec.EnumValue<Restriction> stonelover;
    public static ForgeConfigSpec.EnumValue<Restriction> swift;
    public static ForgeConfigSpec.EnumValue<Restriction> teleportation;
    public static ForgeConfigSpec.EnumValue<Restriction> transposition;
    public static ForgeConfigSpec.EnumValue<Restriction> true_lifesteal;
    public static ForgeConfigSpec.EnumValue<Restriction> true_shot;
    public static ForgeConfigSpec.EnumValue<Restriction> breaking;
    public static ForgeConfigSpec.EnumValue<Restriction> butterfingers;
    public static ForgeConfigSpec.EnumValue<Restriction> fumbling;
    public static ForgeConfigSpec.EnumValue<Restriction> shadow;
    public static ForgeConfigSpec.EnumValue<Restriction> magnetic;
    public static ForgeConfigSpec.EnumValue<Restriction> global_traveller;


    ServerConfig(ForgeConfigSpec.Builder builder) {
      builder.push("general");
      backtothechamber = builder
              .comment(s1)
              .translation("text.randomenchants.config.backtothechamber")
              .defineEnum("backtothechamber", NORMAL);
      combo = builder
              .comment(s1)
              .translation("text.randomenchants.config.combo")
              .defineEnum("combo", NORMAL);
      cursedjumping = builder
              .comment(s1)
              .translation("text.randomenchants.config.cursedjumping")
              .defineEnum("cursedjumping", NORMAL);
      deflect = builder
              .comment(s1)
              .translation("text.randomenchants.config.deflect")
              .defineEnum("deflect", NORMAL);
      disarm = builder
              .comment(s1)
              .translation("text.randomenchants.config.disarm")
              .defineEnum("disarm", NORMAL);
      discord = builder
              .comment(s1)
              .translation("text.randomenchants.config.discord")
              .defineEnum("discord", NORMAL);
      equalmine = builder
              .comment(s1)
              .translation("text.randomenchants.config.equalmine")
              .defineEnum("equalmine", NORMAL);
      eternal = builder
              .comment(s1)
              .translation("text.randomenchants.config.eternal")
              .defineEnum("eternal", NORMAL);
      exploding = builder
              .comment(s1)
              .translation("text.randomenchants.config.exploding")
              .defineEnum("exploding", NORMAL);
      floating = builder
              .comment(s1)
              .translation("text.randomenchants.config.floating")
              .defineEnum("floating", NORMAL);
      grappling = builder
              .comment(s1)
              .translation("text.randomenchants.config.grappling")
              .defineEnum("grappling", NORMAL);
      harvest = builder
              .comment(s1)
              .translation("text.randomenchants.config.harvest")
              .defineEnum("harvest", NORMAL);
      homing = builder
              .comment(s1)
              .translation("text.randomenchants.config.homing")
              .defineEnum("homing", NORMAL);
      instant_death = builder
              .comment(s1)
              .translation("text.randomenchants.config.instant_death")
              .defineEnum("instant_death", NORMAL);
      lightning = builder
              .comment(s1)
              .translation("text.randomenchants.config.lightning")
              .defineEnum("instant_death", NORMAL);
      lumberjack = builder
              .comment(s1)
              .translation("text.randomenchants.config.lumberjack")
              .defineEnum("lumberjack", NORMAL);
      momentum = builder
              .comment(s1)
              .translation("text.randomenchants.config.momentum")
              .defineEnum("momentum", NORMAL);
      obsidian_buster = builder
              .comment(s1)
              .translation("text.randomenchants.config.obsidian_buster")
              .defineEnum("obsidian_buster", NORMAL);
      paralysis = builder
              .comment(s1)
              .translation("text.randomenchants.config.paralysis")
              .defineEnum("paralysis", NORMAL);
      phasing = builder
              .comment(s1)
              .translation("text.randomenchants.config.phasing")
              .defineEnum("phasing", NORMAL);
      quickdraw = builder
              .comment(s1)
              .translation("text.randomenchants.config.quickdraw")
              .defineEnum("quickdraw", NORMAL);
      dungeoneering = builder
              .comment(s1)
              .translation("text.randomenchants.config.dungeoneering")
              .defineEnum("dungeoneering", NORMAL);
      reflect = builder
              .comment(s1)
              .translation("text.randomenchants.config.reflect")
              .defineEnum("reflect", NORMAL);
      resistant = builder
              .comment(s1)
              .translation("text.randomenchants.config.resistant")
              .defineEnum("resistant", NORMAL);
      ricochet = builder
              .comment(s1)
              .translation("text.randomenchants.config.ricochet")
              .defineEnum("ricochet", NORMAL);
      shattering = builder
              .comment(s1)
              .translation("text.randomenchants.config.shattering")
              .defineEnum("shattering", NORMAL);
      silverfish = builder
              .comment(s1)
              .translation("text.randomenchants.config.shattering")
              .defineEnum("shattering", ANVIL);
      snatching = builder
              .comment(s1)
              .translation("text.randomenchants.config.snatching")
              .defineEnum("snatching", NORMAL);
      solar = builder
              .comment(s1)
              .translation("text.randomenchants.config.solar")
              .defineEnum("solar", NORMAL);
      stonebound = builder
              .comment(s1)
              .translation("text.randomenchants.config.stonebound")
              .defineEnum("stonebound", NORMAL);
      stonelover = builder
              .comment(s1)
              .translation("text.randomenchants.config.stonelover")
              .defineEnum("stonelover", NORMAL);
      swift = builder
              .comment(s1)
              .translation("text.randomenchants.config.swift")
              .defineEnum("swift", NORMAL);
      teleportation = builder
              .comment(s1)
              .translation("text.randomenchants.config.teleportation")
              .defineEnum("teleportation", NORMAL);
      transposition = builder
              .comment(s1)
              .translation("text.randomenchants.config.transposition")
              .defineEnum("transposition", NORMAL);
      true_lifesteal = builder
              .comment(s1)
              .translation("text.randomenchants.config.true_lifesteal")
              .defineEnum("true_lifesteal", NORMAL);
      true_shot = builder
              .comment(s1)
              .translation("text.randomenchants.config.true_shot")
              .defineEnum("true_shot", NORMAL);
      torches = builder
              .comment(s1)
              .translation("text.randomenchants.config.torches")
              .defineEnum("torches", NORMAL);

      breaking = builder
              .comment(s1)
              .translation("text.randomenchants.config.breaking")
              .defineEnum("breaking", NORMAL);
      butterfingers = builder
              .comment(s1)
              .translation("text.randomenchants.config.butterfingers")
              .defineEnum("butterfingers", NORMAL);
      fumbling = builder
              .comment(s1)
              .translation("text.randomenchants.config.fumbling")
              .defineEnum("fumbling", NORMAL);
      shadow = builder
              .comment(s1)
              .translation("text.randomenchants.config.shadow")
              .defineEnum("shadow", NORMAL);

      magnetic = builder
              .comment(s1)
              .translation("text.randomenchants.config.magnetic")
              .defineEnum("magnetic", NORMAL);

      global_traveller = builder
              .comment(s1)
              .translation("text.randomenchants.config.global_traveller")
              .defineEnum("golbal_traveller", NORMAL);

      builder.pop();
    }
  }

  public enum Restriction {DISABLED,ANVIL,NORMAL}
}
