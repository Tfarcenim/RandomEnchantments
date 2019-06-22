package com.tfar.randomenchants.util;

import com.tfar.randomenchants.EnchantmentConfig;
import com.tfar.randomenchants.init.ModEnchantment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

import static com.tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler.KEY;
import static com.tfar.randomenchants.init.ModEnchantment.*;
import static com.tfar.randomenchants.util.EnchantmentUtils.*;

public class ClientEventHandler {
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void onTooltip(ItemTooltipEvent e) {
    EntityPlayer p = e.getEntityPlayer();
    if (p == null) return;
    if ((EnchantmentUtils.stackHasEnch(e.getItemStack(),STONEBOUND))) {
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

  @SubscribeEvent
  public void tooltip(ItemTooltipEvent event) {
    NBTTagCompound nbt0 = getTagSafe(event.getItemStack());
    if (event.isCanceled()
            || event.getItemStack().isEmpty()
            || !EnchantmentUtils.stackHasEnch(event.getItemStack(),GLOBAL_TRAVELLER)) return;

    event.getToolTip().add("Toggle: "+nbt0.getBoolean("toggle"));

    if (nbt0.hasKey(KEY, 10)) {
      NBTTagCompound nbt = nbt0.getCompoundTag(KEY);
      event.getToolTip().add(I18n.format("tooltip.globalmodifier.info",
              nbt.getInteger("x"),
              nbt.getInteger("y"),
              nbt.getInteger("z"),
             getWorldNameFromid( nbt.getInteger("dim"))));

    }
  }// cant be in common code or it will crash dedicated servers!
  @SubscribeEvent
  public void playerTick(TickEvent.PlayerTickEvent event) {
    if (event.phase == TickEvent.Phase.END)return;
    EntityPlayer player = Minecraft.getMinecraft().player;
    if (player == null) return;
    if (!EnchantmentUtils.stackHasEnch(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST), FAST_PLACING)) return;
    int delay = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class,Minecraft.getMinecraft(),"field_71467_ac");
    delay -= EnchantmentHelper.getEnchantmentLevel(FAST_PLACING,player.getItemStackFromSlot(EntityEquipmentSlot.CHEST));
    if (delay<0) delay = 0;
    ObfuscationReflectionHelper.setPrivateValue(Minecraft.class,Minecraft.getMinecraft(),delay,"field_71467_ac");
  }
}
