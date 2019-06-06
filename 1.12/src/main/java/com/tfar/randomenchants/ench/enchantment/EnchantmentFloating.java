package com.tfar.randomenchants.ench.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import javax.annotation.Nonnull;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.WEAPONS;
import static com.tfar.randomenchants.init.ModEnchantment.FLOATING;

public class EnchantmentFloating extends Enchantment {
    public EnchantmentFloating() {

        super(Rarity.RARE, WEAPONS, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("floating");
        this.setName("floating");
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
    public boolean canApply(@Nonnull ItemStack stack){
        return weapons.enableFloating != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return weapons.enableFloating == ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return weapons.enableFloating != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return weapons.enableFloating == NORMAL;
    }

    @Override
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level)  {

        if ((EnchantmentHelper.getMaxEnchantmentLevel(FLOATING, user) > 0 && target instanceof EntityLivingBase)){
            ((EntityLivingBase)target).
                    addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 200, 1));
        }
        }
    }

