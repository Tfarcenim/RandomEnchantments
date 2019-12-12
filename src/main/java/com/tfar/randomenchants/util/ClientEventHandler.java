package com.tfar.randomenchants.util;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.*;
import static com.tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler.KEY;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID,value = Dist.CLIENT)
public class ClientEventHandler {
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void onTooltip(ItemTooltipEvent e) {
    PlayerEntity p = e.getEntityPlayer();
    if (p == null) return;
    List<ITextComponent> tooltip = e.getToolTip();
    if ((EnchantUtils.hasEnch(e.getItemStack(),STONEBOUND))) {
      ItemStack stack = p.getHeldItemMainhand();
      if (e.getItemStack() != stack) return;
      tooltip.add(new StringTextComponent("Mining Bonus: " + TextFormatting.GREEN + "+" + stack.getDamage() * .02));
      tooltip.add(new StringTextComponent("Damage Penalty: " + TextFormatting.RED + "-" + stack.getDamage() * .02));
    } else if (EnchantUtils.hasEnch(p,COMBO)) {
      ItemStack stack = p.getHeldItemMainhand();
      if (e.getItemStack() != stack) return;
      CompoundNBT compound = stack.getOrCreateTag();
      int combo = compound.getInt("combo");
      tooltip.add(new StringTextComponent("Combo: " + TextFormatting.GOLD + combo + "x"));
    }

    if (e.getItemStack().getItem() instanceof EnchantedBookItem){
      ListNBT nbttaglist = EnchantedBookItem.getEnchantments(e.getItemStack());

      for (int i = 0; i < nbttaglist.size(); ++i)
      {
     //   CompoundNBT nbttagcompound = nbttaglist.getShort(i);
    //    int j = nbttagcompound.getShort("id");
     //   Enchantment enchantment = Enchantment.getEnchantmentByID(j);
     //   if (ModEnchantment.enchants.get(enchantment) == EnchantmentConfig.EnumAccessLevel.DISABLED)
        {
    //      tooltip.add(new StringTextComponent(TextFormatting.DARK_RED+"THIS ENCHANTMENT IS DISABLED!"));
        }
      }

    }
  }

  @SubscribeEvent
  public static void tooltip(ItemTooltipEvent event) {
    CompoundNBT nbt0 = event.getItemStack().getTag();
    if (true)return;
    if (event.isCanceled()
            || !EnchantUtils.hasEnch(event.getItemStack(),GLOBAL_TRAVELER)) return;

    event.getToolTip().add(new StringTextComponent("Toggle: "+nbt0.getBoolean("toggle")));

    if (nbt0.contains(KEY, 10)) {
      CompoundNBT nbt = nbt0.getCompound(KEY);
      event.getToolTip().add(new StringTextComponent(I18n.format("tooltip.globalmodifier.info",
              nbt.getInt("x"),
              nbt.getInt("y"),
              nbt.getInt("z"),
         nbt.getInt("dim"))));

    }
  }
}
