package com.tfar.randomenchants.util;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.*;
import static com.tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler.KEY;

@Mod.EventBusSubscriber(modid = RandomEnchants.MOD_ID,value = Dist.CLIENT)
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
    if (nbt0 == null)return;
    if (event.isCanceled()
            || !EnchantUtils.hasEnch(event.getItemStack(),GLOBAL_TRAVELLER)) return;

    event.getToolTip().add(new StringTextComponent("Toggle: "+nbt0.getBoolean("toggle")));

    if (nbt0.contains(KEY, 10)) {
      CompoundNBT nbt = nbt0.getCompound(KEY);
      event.getToolTip().add(new StringTextComponent(I18n.format("tooltip.globalmodifier.info",
              nbt.getInt("x"),
              nbt.getInt("y"),
              nbt.getInt("z"),
         nbt.getInt("dim"))));

    }
  }// cant be in common code or it will crash dedicated servers!
  @SubscribeEvent
  public void playerTick(TickEvent.PlayerTickEvent event) {
    if (event.phase == TickEvent.Phase.END)return;
    PlayerEntity player = Minecraft.getInstance().player;
    if (player == null) return;
    if (!EnchantUtils.hasEnch(player.getItemStackFromSlot(EquipmentSlotType.CHEST), FAST_PLACING)) return;
    int delay = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class,Minecraft.getInstance(),"field_71467_ac");
    delay -= EnchantmentHelper.getEnchantmentLevel(FAST_PLACING,player.getItemStackFromSlot(EquipmentSlotType.CHEST));
    if (delay<0) delay = 0;
    ObfuscationReflectionHelper.setPrivateValue(Minecraft.class,Minecraft.getInstance(),delay,"field_71467_ac");
  }
}
