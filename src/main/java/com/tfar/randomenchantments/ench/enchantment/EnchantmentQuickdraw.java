package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.util.GlobalVars;
import com.tfar.randomenchantments.util.ReflectionUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.init.ModEnchantment.*;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentQuickdraw extends Enchantment {
  public EnchantmentQuickdraw() {
    super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    setRegistryName("quickdraw");
    setName("quickdraw");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 5 + 10 * (level - 1);
  }

  @Override
  public int getMaxEnchantability(int level) {
    return super.getMinEnchantability(level) + 25;
  }

  @Override
  public int getMaxLevel() {
    return 5;
  }



  @SubscribeEvent
  public static void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
    if (event.getEntity() instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer) event.getEntity();
      ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
      if (!(heldItem.getItem() instanceof ItemBow)) heldItem = player.getHeldItem(EnumHand.OFF_HAND);
      if (!(heldItem.getItem() instanceof ItemBow)) return;
      if (EnchantmentHelper.getMaxEnchantmentLevel(QUICKDRAW, player) <= 0) return;
      if (player.isHandActive()) {
        for (int i = 0; i < EnchantmentHelper.getMaxEnchantmentLevel(QUICKDRAW, player); i++) {
          tickHeldBow(player);
        }
      }
    }
  }

  private static void tickHeldBow(EntityPlayer player) {
    //     player.updateActiveHand();//BUT its protected bahhhh
    ReflectionUtils.callPrivateMethod(EntityLivingBase.class, player, "updateActiveHand", "func_184608_ct");
  }
}


