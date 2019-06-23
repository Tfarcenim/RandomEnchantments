package com.tfar.randomenchants.ench.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;

import javax.annotation.Nonnull;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.FLOATING;
import static com.tfar.randomenchants.RandomEnchants.WEAPONS;

public class EnchantmentFloating extends Enchantment {
    public EnchantmentFloating() {

        super(Rarity.RARE, WEAPONS, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("floating");
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
    public void onEntityDamaged(LivingEntity user, Entity target, int level)  {

        if ((EnchantmentHelper.getMaxEnchantmentLevel(FLOATING, user) > 0 && target instanceof LivingEntity)){
            ((LivingEntity)target).
                    addPotionEffect(new EffectInstance(Effects.LEVITATION, 200, 1));
        }
        }
    }

