package com.tfar.randomenchants.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.GLOBAL_TRAVELER;
import static com.tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler.KEY;

//import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.GLOBAL_TRAVELER;
//import static com.tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler.KEY;

@Mod.EventBusSubscriber
public class EventHandler {



  @SubscribeEvent
  public static void toggle(PlayerInteractEvent.RightClickItem e) {
    if (e.getWorld().isRemote) return;
     if (e.getWorld().getTileEntity(e.getPos()) != null)return;

    ItemStack stack = e.getItemStack();
    if (EnchantUtils.hasEnch(stack, GLOBAL_TRAVELER) && e.getPlayer().isSneaking()) {
      toggle(stack);
    }
  }

  public static void toggle(ItemStack stack) {
      boolean toggle = stack.getTag().getBoolean("toggle");
      stack.getTag().putBoolean("toggle", !toggle);
  }
}