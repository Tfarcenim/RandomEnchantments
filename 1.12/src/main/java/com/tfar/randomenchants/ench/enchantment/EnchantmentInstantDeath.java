package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.WEAPONS;
import static com.tfar.randomenchants.init.ModEnchantment.INSTANT_DEATH;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
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
    public boolean canApply(ItemStack stack){
        return weapons.enableInstantDeath != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return weapons.enableInstantDeath == ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return weapons.enableInstantDeath != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return weapons.enableInstantDeath == NORMAL;
    }

    @Override
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
        if (EnchantmentHelper.getMaxEnchantmentLevel(INSTANT_DEATH, user) > 0 && target instanceof EntityLivingBase) {

            EntityLivingBase victim = (EntityLivingBase) target;
            victim.setHealth(0);
        }
    }
}
