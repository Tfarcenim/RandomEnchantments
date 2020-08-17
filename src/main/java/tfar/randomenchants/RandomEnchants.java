package tfar.randomenchants;

import net.minecraft.inventory.EquipmentSlotType;
import tfar.randomenchants.ench.curse.ButterfingersCurse;
import tfar.randomenchants.ench.curse.BreakingCurse;
import tfar.randomenchants.ench.curse.ShadowCurse;
import tfar.randomenchants.ench.curse.FumblingCurse;
import tfar.randomenchants.util.EventHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tfar.randomenchants.ench.enchantment.*;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Predicate;

import static tfar.randomenchants.RandomEnchants.MODID;

@Mod.EventBusSubscriber(modid = MODID)
@Mod(value = MODID)

public class RandomEnchants {

  public static final ArrayList<Item> itemList = new ArrayList<>();
  public static final String MODID = "randomenchants";
  public static final EnchantmentType SWORDS_BOWS = addEnchantment("weapons", item -> item instanceof SwordItem || item instanceof ShootableItem);

  public static final EnchantmentType PICKAXE = addEnchantment("pickaxe", PickaxeItem.class::isInstance);
  public static final EnchantmentType SHOOTABLE = addEnchantment("shootable", ShootableItem.class::isInstance);
  public static final EnchantmentType SHIELDS = addEnchantment("shields", ShieldItem.class::isInstance);
  public static final EnchantmentType AXE = addEnchantment("axe", AxeItem.class::isInstance);
  public static final EnchantmentType TOOLSANDWEAPONS = addEnchantment("tools&weapons", item -> item instanceof SwordItem || item instanceof ShootableItem || item instanceof ToolItem);

  public static Set<Enchantment> enchants = new HashSet<>();

  public static Logger logger = LogManager.getLogger(MODID);

  public RandomEnchants(){
    ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Enchantment.class,this::registerEnchantments);
  }

  @SubscribeEvent
  public void registerEnchantments(RegistryEvent.Register<Enchantment> event) {

    enchants.add(ObjectHolders.NETHERPROOF.setRegistryName("netherproof"));
    enchants.add(new EnchantmentReflect());
    enchants.add(new EnchantmentDiscord());
    enchants.add(new EnchantmentSwift());
    enchants.add(new EnchantmentMagnetic());
    enchants.add(new EnchantmentHarvest());
    enchants.add(new EnchantmentTorches());
    enchants.add(new EnchantmentBackToTheChamber());
    enchants.add(new EnchantmentExploding());
    enchants.add(new EnchantmentRicochet());
    enchants.add(new EnchantmentShattering());
    enchants.add(new EnchantmentFloating());
    enchants.add(new EnchantmentInstantDeath());
    enchants.add(new EnchantmentCursedJumping());
    enchants.add(new EnchantmentParalysis());
    enchants.add(new EnchantmentTrueLifesteal());
    enchants.add(new EnchantmentLightning());
    enchants.add(new EnchantmentDisarm());
    enchants.add(new EnchantmentHoming());
    enchants.add(new EnchantmentQuickdraw());
    enchants.add(new EnchantmentSolar());
    enchants.add(new EnchantmentTrueShot());
    enchants.add(new EnchantmentTeleportation());
    enchants.add(new EnchantmentTransposition());
    enchants.add(new EnchantmentLumberjack());
    enchants.add(new EnchantmentPhasing());
    enchants.add(new EnchantmentObsidianBuster());
    enchants.add(new EnchantmentEqualMine());
    enchants.add(new EnchantmentStonebound());
    enchants.add(new EnchantmentStoneLover());
    enchants.add(new EnchantmentDungeoneering());
    enchants.add(new EnchantmentSnatching());
    enchants.add(new EnchantmentGrappling());
    enchants.add(new EnchantmentResistant());
    enchants.add(new EnchantmentEternal());
    enchants.add(new EnchantmentGlobalTraveler());
    enchants.add(new EnchantmentAutoSmelt());
    enchants.add(new EnchantmentAssimilation());

    enchants.add(new ButterfingersCurse());
    enchants.add(new FumblingCurse());
    enchants.add(new BreakingCurse());
    enchants.add(new ShadowCurse());
    enchants.add(new EnchantmentSilverfish());



    IForgeRegistry<Enchantment> r = event.getRegistry();

    for (Enchantment enchant : enchants)
        r.register(enchant);
    RandomEnchants.logger.info("Registered "+enchants.size()+" enchantments!");

    if (false) {
  //    MinecraftForge.EVENT_BUS.register(GLOBAL_TRAVELER);
    //  EnchantmentGlobalTraveler.GLOBAL_TRAVELER_KEY = GLOBAL_TRAVELER.getRegistryName().toString();
    }
    setup();
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
  @ObjectHolder(MODID)
  public static class ObjectHolders {

    public static final Enchantment FLOATING = null;
    public static final Enchantment INSTANT_DEATH = null;
    public static final Enchantment CURSED_JUMP = null;
    public static final Enchantment PARALYSIS = null;
    public static final Enchantment TRUE_LIFESTEAL = null;
    public static final Enchantment TRUE_SHOT = null;
    public static final Enchantment SNATCHING = null;
    public static final Enchantment PHASING = null;
    public static final Enchantment TORCHES = null;
    public static final Enchantment HARVEST = null;

    public static final Enchantment REFLECT = null;
    public static final Enchantment DISCORD = null;
    public static final Enchantment LIGHTNING = null;
    public static final Enchantment TRANSPOSITION = null;
    public static final Enchantment DUNGEONEERING = null;
    public static final Enchantment DISARM = null;
    public static final Enchantment HOMING = null;
    public static final Enchantment QUICKDRAW = null;
    public static final Enchantment SWIFT = null;
    public static final Enchantment GRAPPLING = null;
    public static final Enchantment TELEPORTATON = null;
    public static final Enchantment SOLAR = null;
    public static final Enchantment LUMBERJACK = null;
    public static final Enchantment SHATTERING = null;
    public static final Enchantment MAGNETIC = null;
    public static final Enchantment RESISTANT = null;
    public static final Enchantment ETERNAL = null;
    public static final Enchantment GLOBAL_TRAVELER = null;

    public static final Enchantment OBSIDIAN_BUSTER = null;
    public static final Enchantment EQUAL_MINE = null;
    public static final Enchantment STONEBOUND = null;
    public static final Enchantment STONELOVER = null;
    public static final Enchantment RICOCHET = null;
    public static final Enchantment AUTOSMELT = null;
    public static final Enchantment Assimilation = null;
    public static final Enchantment NETHERPROOF = new NetherProofingEnchant(Enchantment.Rarity.RARE, EnchantmentType.BREAKABLE, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND});

    //register curses

    public static final Enchantment BUTTERFINGERS = null;
    public static final Enchantment FUMBLING = null;
    public static final Enchantment BREAKING = null;
    public static final Enchantment SHADOW = null;
    public static final Enchantment SILVERFISH = null;
  }
}

