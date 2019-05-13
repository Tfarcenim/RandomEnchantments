package com.tfar.randomenchantments.util;

import com.tfar.randomenchantments.EnchantmentConfig;
import com.tfar.randomenchantments.init.ModEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

import static com.tfar.randomenchantments.init.ModEnchantment.COMBO;
import static com.tfar.randomenchantments.init.ModEnchantment.STONEBOUND;

public class ClientEventHandler {
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void onTooltip(ItemTooltipEvent e) {
    EntityPlayer p = e.getEntityPlayer();
    if (p == null) return;
    if ((EnchantmentHelper.getMaxEnchantmentLevel(STONEBOUND, p) > 0)) {
      ItemStack stack = p.getHeldItemMainhand();
      if (e.getItemStack() != stack) return;
      ArrayList<String> tooltip = (ArrayList<String>) e.getToolTip();
      tooltip.add("Mining Bonus: " + TextFormatting.GREEN + "+" + stack.getItemDamage() * .02);
      tooltip.add("Damage Penalty: " + TextFormatting.RED + "-" + stack.getItemDamage() * .02);
    } else if ((EnchantmentHelper.getMaxEnchantmentLevel(COMBO, p) > 0)) {
      ItemStack stack = p.getHeldItemMainhand();
      if (e.getItemStack() != stack) return;
      NBTTagCompound compound = stack.getTagCompound();
      int combo = compound.getInteger("combo");
      ArrayList<String> tooltip = (ArrayList<String>) e.getToolTip();
      tooltip.add("Combo: " + TextFormatting.GOLD + combo + "x");
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
}
