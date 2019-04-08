package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.init.ModEnchantment;
import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.CommonProxy.WEAPONS;
import static com.tfar.randomenchantments.init.ModEnchantment.TRUE_LIFESTEAL;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentTrueLifesteal extends Enchantment {
    public EnchantmentTrueLifesteal() {

        super(Rarity.RARE, WEAPONS, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("true_lifesteal");
        this.setName("true_lifesteal");
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
            if (EnchantmentHelper.getMaxEnchantmentLevel(TRUE_LIFESTEAL, player) > 0) {
                EntityLivingBase entity = event.getEntityLiving();
                float damage = event.getAmount();
                entity.attackEntityFrom(DamageSource.GENERIC, damage*1.5f);
                player.heal(damage);
            }
        }
    }
}
