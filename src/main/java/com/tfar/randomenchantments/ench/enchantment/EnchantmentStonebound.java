package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.init.ModEnchantment.*;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentStonebound extends Enchantment {
    public EnchantmentStonebound() {

        super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("stonebound");
        this.setName("stonebound");
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

    @SubscribeEvent
    public static void onAttack(LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            if (EnchantmentHelper.getMaxEnchantmentLevel(STONEBOUND, player) > 0) {
                EntityLivingBase entity = event.getEntityLiving();
                ItemStack stack = player.getHeldItemMainhand();
                float reduction = .02f * stack.getItemDamage();
                entity.heal(reduction);
            }
        }
    }
    @SubscribeEvent
    public void onBreakBlock(PlayerEvent.BreakSpeed e)  {
        EntityPlayer p = e.getEntityPlayer();
        if ((EnchantmentHelper.getMaxEnchantmentLevel(STONEBOUND, p) > 0)){
            float oldSpeed = e.getOriginalSpeed();
            ItemStack stack = p.getHeldItemMainhand();
            float increase = .02f * stack.getItemDamage();
            e.setNewSpeed(increase+oldSpeed);
        }
    }
}

