package com.tfar.randomenchants.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.GLOBAL_TRAVELER;
import static com.tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler.GLOBAL_TRAVELER_KEY;

@Mod.EventBusSubscriber
public class EventHandler {



  @SubscribeEvent
  public static void toggle(PlayerInteractEvent.RightClickBlock e) {
    World world = e.getWorld();
    if (world.isRemote) return;
    BlockPos pos = e.getPos();
    TileEntity tile = world.getTileEntity(pos);
     if (tile != null)return;

    ItemStack stack = e.getItemStack();
    if (EnchantUtils.hasEnch(stack, GLOBAL_TRAVELER) && e.getPlayer().isCrouching()) {
      toggle(stack);
    }
  }

  public static void toggle(ItemStack stack) {
    CompoundNBT global = stack.getTag().getCompound(GLOBAL_TRAVELER_KEY);
    if (!global.isEmpty()) {
      boolean toggle = global.getBoolean("toggle");
      global.putBoolean("toggle", !toggle);
    }
  }
}