package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.EnchantmentConfig;
import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.RandomEnchantments.WEAPONS;
import static com.tfar.randomenchantments.init.ModEnchantment.FLOATING;

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
    public boolean isAllowedOnBooks() {
        return weapons.enableFloating == EnchantmentConfig.EnumAccessLevel.NORMAL;
    }

    @Override
    public boolean canApply(ItemStack stack){
        return weapons.enableFloating != EnchantmentConfig.EnumAccessLevel.DISABLED && super.canApply(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return isAllowedOnBooks();
    }

    @Override
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level)  {

        if ((EnchantmentHelper.getMaxEnchantmentLevel(FLOATING, user) > 0 && target instanceof EntityLivingBase)){
            ((EntityLivingBase)target).
                    addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 200, 1));
        }
        }
    }

