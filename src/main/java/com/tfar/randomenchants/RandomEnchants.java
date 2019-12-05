package com.tfar.randomenchants;

import com.tfar.randomenchants.entity.EntitySpecialArrow;
import com.tfar.randomenchants.entity.RenderPiercingArrow;
import com.tfar.randomenchants.init.ModEnchantment;
import com.tfar.randomenchants.network.PacketHandler;
import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

import static com.tfar.randomenchants.util.GlobalVars.*;

@Mod.EventBusSubscriber
@Mod(modid = MOD_ID, name = GlobalVars.NAME, version = GlobalVars.VERSION, dependencies = "required:forge@[14.23.5.2796,);")

public class RandomEnchants {
  public static final EnumEnchantmentType WEAPONS = addEnchantment("weapons", item -> item instanceof ItemSword || item instanceof ItemBow);
  public static final EnumEnchantmentType PICKAXE = addEnchantment("pickaxe", ItemPickaxe.class::isInstance);
  public static final EnumEnchantmentType SHIELDS = addEnchantment("shields", ItemShield.class::isInstance);
  public static final EnumEnchantmentType AXE = addEnchantment("axe", ItemAxe.class::isInstance);
  public static final EnumEnchantmentType TOOLSANDWEAPONS = addEnchantment("tools&weapons",item -> item instanceof ItemSword || item instanceof ItemBow || item instanceof ItemTool);


  public static final SimpleNetworkWrapper NETWORK_WRAPPER = new SimpleNetworkWrapper(MOD_ID);

  public static Logger logger = LogManager.getLogger(MOD_ID);

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {
    ModEnchantment.registerEvents();
    NETWORK_WRAPPER.registerMessage(PacketHandler.Handler.class, PacketHandler.class, 0, Side.SERVER);
  }

  @SuppressWarnings("ConstantConditions")
  @Nonnull
  public static EnumEnchantmentType addEnchantment(String name, Predicate<Item> condition) {
    return EnumHelper.addEnchantmentType(name, condition::test);
  }

  public static void setup() {
    for (Item item : ForgeRegistries.ITEMS) {
      itemList.add(item);
      size++;
    }
  }

  @SubscribeEvent
  public static void registerentity(RegistryEvent.Register<EntityEntry> e){
    final ResourceLocation resourceLocation = new ResourceLocation(MOD_ID, "piercing_arrow");


    e.getRegistry().register(
            EntityEntryBuilder.create()
                    .entity(EntitySpecialArrow.class)
                    .id(resourceLocation, 0)
                    .name(resourceLocation.getPath())
                    .tracker(64, 1, true)
                    .build());
  }

  @SubscribeEvent
  public static void models(ModelRegistryEvent e){
    RenderingRegistry.registerEntityRenderingHandler(EntitySpecialArrow.class, RenderPiercingArrow::new);
  }

}

