package com.tfar.randomenchants.util;

import com.tfar.randomenchants.EnchantmentConfig;
import com.tfar.randomenchants.init.ModEnchantment;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;

import static com.tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler.KEY;
import static com.tfar.randomenchants.init.ModEnchantment.GLOBAL_TRAVELLER;
import static com.tfar.randomenchants.init.ModEnchantment.STONEBOUND;
import static com.tfar.randomenchants.util.EnchantmentUtils.getTagSafe;
import static com.tfar.randomenchants.util.EnchantmentUtils.getWorldNameFromid;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientEventHandler {
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void onTooltip(ItemTooltipEvent e) {
    EntityPlayer p = e.getEntityPlayer();
    if (p == null) return;
    if ((EnchantmentUtils.stackHasEnch(e.getItemStack(),STONEBOUND))) {
      ItemStack stack = p.getHeldItemMainhand();
      if (e.getItemStack() != stack) return;
      ArrayList<String> tooltip = (ArrayList<String>) e.getToolTip();
      tooltip.add("Mining Bonus: " + TextFormatting.GREEN + "+" + stack.getItemDamage() * .02);
      tooltip.add("Damage Penalty: " + TextFormatting.RED + "-" + stack.getItemDamage() * .02);
    }

    if (e.getItemStack().getItem() instanceof ItemEnchantedBook){
      NBTTagList nbttaglist = ItemEnchantedBook.getEnchantments(e.getItemStack());

      for (int i = 0; i < nbttaglist.tagCount(); ++i)
      {
        NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
        int j = nbttagcompound.getShort("id");
        Enchantment enchantment = Enchantment.getEnchantmentByID(j);
        if (ModEnchantment.enchants.get(enchantment) == EnchantmentConfig.EnumAccessLevel.DISABLED)
        {
          ArrayList<String> tooltip = (ArrayList<String>) e.getToolTip();
          tooltip.add(TextFormatting.DARK_RED+"THIS ENCHANTMENT IS DISABLED!");
        }
      }

    }
  }

  @SubscribeEvent
  public static void tooltip(ItemTooltipEvent event) {
    NBTTagCompound nbt0 = getTagSafe(event.getItemStack());
    if (event.isCanceled()
            || event.getItemStack().isEmpty()
            || !EnchantmentUtils.stackHasEnch(event.getItemStack(), GLOBAL_TRAVELLER)) return;

    event.getToolTip().add("Toggle: " + nbt0.getBoolean("toggle"));

    if (nbt0.hasKey(KEY, 10)) {
      NBTTagCompound nbt = nbt0.getCompoundTag(KEY);
      event.getToolTip().add(I18n.format("tooltip.globalmodifier.info",
              nbt.getInteger("x"),
              nbt.getInteger("y"),
              nbt.getInteger("z"),
              getWorldNameFromid(nbt.getInteger("dim"))));

    }
  }
}
