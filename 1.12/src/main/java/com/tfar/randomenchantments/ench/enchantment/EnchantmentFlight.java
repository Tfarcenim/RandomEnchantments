package com.tfar.randomenchantments.ench.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


//@Mod.EventBusSubscriber(modid= ReferenceVariables.MOD_ID)
public class EnchantmentFlight extends Enchantment {
    public EnchantmentFlight() {

        super(Rarity.RARE, EnumEnchantmentType.ARMOR_CHEST, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.CHEST
        });
        this.setRegistryName("flight");
        this.setName("flight");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return 100;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() == Items.BOOK ||
                stack.getItem() == Items.ELYTRA ||
                (stack.getItem() instanceof ItemArmor)
                        && ((ItemArmor) stack.getItem()).armorType == EntityEquipmentSlot.CHEST;
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {

        EntityLivingBase e = event.getEntityLiving();
        if (e instanceof EntityPlayer) {
  //          if (EnchantmentHelper.getMaxEnchantmentLevel(FLIGHT, e) > 0 || ((EntityPlayer) e).isCreative())
                ((EntityPlayer) e).capabilities.allowFlying = true;
    //        else {
                ((EntityPlayer) e).capabilities.allowFlying = false;
                ((EntityPlayer) e).capabilities.isFlying = false;
            }
      //  }
    }
}