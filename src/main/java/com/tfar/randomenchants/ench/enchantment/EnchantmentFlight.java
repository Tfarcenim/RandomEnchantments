package com.tfar.randomenchants.ench.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;


//@Mod.EventBusSubscriber(modid= ReferenceVariables.MOD_ID)
public class EnchantmentFlight extends Enchantment {
    public EnchantmentFlight() {

        super(Rarity.RARE, EnchantmentType.ARMOR_CHEST, new EquipmentSlotType[]{
                EquipmentSlotType.CHEST
        });
        this.setRegistryName("flight");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 15;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() == Items.BOOK ||
                stack.getItem() == Items.ELYTRA ||
                (stack.getItem() instanceof ArmorItem)
                        && ((ArmorItem) stack.getItem()).getEquipmentSlot() == EquipmentSlotType.CHEST;
    }

  /*  @SubscribeEvent
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
    }*/
}