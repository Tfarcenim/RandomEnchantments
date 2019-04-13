package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.RandomEnchantments;
import com.tfar.randomenchantments.network.PacketHandler;
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
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.init.ModEnchantment.*;
import static net.minecraft.enchantment.EnchantmentHelper.getMaxEnchantmentLevel;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)
public class EnchantmentSwift extends Enchantment {
  public EnchantmentSwift() {

    super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("swift");
    this.setName("swift");
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

  @Override
  public boolean canApply(ItemStack stack){
    return true && super.canApply(stack);
  }

  @SubscribeEvent
  public static void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
    if (event.getEntity() instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer) event.getEntity();
      ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
      if (!(heldItem.getItem() instanceof ItemSword) || getMaxEnchantmentLevel(SWIFT, player) <= 0)
        return;
      if (player.isHandActive()) {
        for (int i = 0; i < getMaxEnchantmentLevel(SWIFT, player); i++) {
          tickHeldSword(player);
        }
      }
    }
  }

  private static void tickHeldSword(EntityPlayer player) {
    //borrowed from cyclic
    //player.updateActiveHand();//BUT its protected bahhhh
    ReflectionUtils.callPrivateMethod(EntityLivingBase.class, player, "updateActiveHand", "func_184608_ct");
  }
}



