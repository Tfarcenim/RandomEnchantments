package com.tfar.randomenchants;

import com.tfar.randomenchants.ench.curse.EnchantmentBreakingCurse;
import com.tfar.randomenchants.ench.curse.EnchantmentButterfingersCurse;
import com.tfar.randomenchants.ench.curse.EnchantmentFumblingCurse;
import com.tfar.randomenchants.ench.curse.EnchantmentShadowCurse;
import com.tfar.randomenchants.ench.enchantment.*;
import com.tfar.randomenchants.util.EventHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static com.tfar.randomenchants.EnchantmentConfig.*;
import static com.tfar.randomenchants.RandomEnchants.MOD_ID;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.*;

@Mod.EventBusSubscriber(modid = MOD_ID)
@Mod(value = MOD_ID)

public class RandomEnchants {

  public static final ArrayList<Item> itemList = new ArrayList<>();
  public static final String MOD_ID = "randomenchants";
  public static final EnchantmentType WEAPONS = addEnchantment("weapons", item -> item instanceof SwordItem || item instanceof BowItem);
  public static final EnchantmentType PICKAXE = addEnchantment("pickaxe", PickaxeItem.class::isInstance);
  public static final EnchantmentType SHIELDS = addEnchantment("shields", ShieldItem.class::isInstance);
  public static final EnchantmentType AXE = addEnchantment("axe", AxeItem.class::isInstance);
  public static final EnchantmentType TOOLSANDWEAPONS = addEnchantment("tools&weapons", item -> item instanceof SwordItem || item instanceof BowItem || item instanceof ToolItem);

  public static Logger logger = LogManager.getLogger(MOD_ID);

  public RandomEnchants(){
    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Enchantment.class,this::registerEnchantments);

  }

  @SubscribeEvent
  public void registerEnchantments(RegistryEvent.Register<Enchantment> event) {

    enchants.put(REFLECT,weapons.enableReflect);//
    enchants.put(FAST_PLACING,weapons.enableFastPlacing);
    enchants.put(SWIFT,weapons.enableSwift);
    enchants.put(MAGNETIC,tools.enableMagnetic);
    enchants.put(HARVEST,weapons.enableHarvesting);
    enchants.put(LIGHTING,weapons.enableTorches);
    enchants.put(EXPLODING,weapons.enableExploding);
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
    enchants.put(COMBO,weapons.enableCombo);
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
    enchants.put(RESISTANT,tools.enableResistance);
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

    MinecraftForge.EVENT_BUS.register(new EventHandler());
    if (tools.enableGlobalTraveler != EnumAccessLevel.DISABLED) {
      MinecraftForge.EVENT_BUS.register(GLOBAL_TRAVELLER);
      EnchantmentGlobalTraveler.KEY = GLOBAL_TRAVELLER.getRegistryName().toString();
    }
    if (tools.enableRandomness != EnumAccessLevel.DISABLED){
      setup();
    }
  }

  @Nonnull
  public static EnchantmentType addEnchantment(String name, Predicate<Item> condition) {
    return EnchantmentType.create(name, condition);
  }

  public static void setup() {
    for (Item item : ForgeRegistries.ITEMS) {
      itemList.add(item);
    }
  }
  public static class ObjectHolders {
    //public static final enchantment FLIGHT = new EnchantmentFlight();
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

    public static final Enchantment REFLECT = new EnchantmentReflect();
    public static final Enchantment FAST_PLACING = new EnchantmentFastPlace();
    public static final Enchantment EXPLODING = new EnchantmentExploding();
    public static final Enchantment LIGHTNING = new EnchantmentLightning();
    public static final Enchantment TRANSPOSITION = new EnchantmentTransposition();
    public static final Enchantment RANDOMNESS = new EnchantmentRandomness();
    public static final Enchantment DISARM = new EnchantmentDisarm();
    public static final Enchantment HOMING = new EnchantmentHoming();
    public static final Enchantment DEFLECT = new EnchantmentDeflect();
    public static final Enchantment BACK_TO_THE_CHAMBER = new EnchantmentBackToTheChamber();
    public static final Enchantment MOMENTUM = new EnchantmentMomentum();
    public static final Enchantment COMBO = new EnchantmentCombo();
    public static final Enchantment QUICKDRAW = new EnchantmentQuickdraw();
    public static final Enchantment SWIFT = new EnchantmentSwift();
    public static final Enchantment PULLING = new EnchantmentGrappling();
    public static final Enchantment TELEPORTATON = new EnchantmentTeleportation();
    public static final Enchantment SOLAR = new EnchantmentSolar();
    public static final Enchantment LUMBERJACK = new EnchantmentLumberjack();
    public static final Enchantment SHATTERING = new EnchantmentShattering();
    public static final Enchantment MAGNETIC = new EnchantmentMagnetic();
    public static final Enchantment RESISTANT = new EnchantmentResistant();
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
  }
}

