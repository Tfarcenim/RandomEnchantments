package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.init.ModEnchantment;
import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchantments.CommonProxy.WEAPONS;
import static com.tfar.randomenchantments.init.ModEnchantment.INSTANT_DEATH;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
@SuppressWarnings("Null")
public class EnchantmentInstantDeath extends Enchantment {
    public EnchantmentInstantDeath() {

        super(Rarity.RARE, WEAPONS, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("instant_death");
        this.setName("instant_death");
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
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
        EntityLivingBase u = user;
        if (EnchantmentHelper.getMaxEnchantmentLevel(INSTANT_DEATH, u) > 0 && target instanceof EntityLivingBase) {

            EntityLivingBase victim = (EntityLivingBase) target;
            victim.setHealth(0);
        }
    }
}
