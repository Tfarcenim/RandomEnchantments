package com.tfar.randomenchantments;

import com.tfar.randomenchantments.network.PacketHandler;
import com.tfar.randomenchantments.util.EventHandler;
import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

import static com.tfar.randomenchantments.util.GlobalVars.*;

@Mod.EventBusSubscriber
@Mod(modid = MOD_ID, name = GlobalVars.NAME, version = GlobalVars.VERSION,dependencies = "required:forge@[14.23.5.2796,);")

public class RandomEnchantments {
    public static final EnumEnchantmentType WEAPONS = RandomEnchantments.addEnchantment("weapons", item -> item instanceof ItemSword || item instanceof ItemBow);
    public static final EnumEnchantmentType PICKAXE = RandomEnchantments.addEnchantment("pickaxe", ItemPickaxe.class::isInstance);
    public static final EnumEnchantmentType SHIELDS = RandomEnchantments.addEnchantment("shields", ItemShield.class::isInstance);
    public static final EnumEnchantmentType AXE = RandomEnchantments.addEnchantment("axe", ItemAxe.class::isInstance);


    public static final SimpleNetworkWrapper NETWORK_WRAPPER = new SimpleNetworkWrapper(MOD_ID);
    @SidedProxy(clientSide = GlobalVars.CLIENT_PROXY_CLASS, serverSide = GlobalVars.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public static Logger logger = LogManager.getLogger(MOD_ID);
        @Mod.EventHandler
        public void init(FMLInitializationEvent event){
        setup();
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        NETWORK_WRAPPER.registerMessage(PacketHandler.Handler.class, PacketHandler.class, 0, Side.SERVER);
        proxy.init(event);
        }
    @SuppressWarnings("ConstantConditions")
    @Nonnull
    public static EnumEnchantmentType addEnchantment(String name, Predicate<Item> condition) {
        return EnumHelper.addEnchantmentType(name, condition::test);
    }

        public static void setup() {
            for ( Item item : ForgeRegistries.ITEMS){
                itemList.add(item);
                size++;
            }
        }
    }

