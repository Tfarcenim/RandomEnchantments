package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.INSTANT_DEATH;
import static com.tfar.randomenchants.RandomEnchants.WEAPONS;

@Mod.EventBusSubscriber(modid= RandomEnchants.MOD_ID)
public class EnchantmentInstantDeath extends Enchantment {
    public EnchantmentInstantDeath() {

        super(Rarity.RARE, WEAPONS, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("instant_death");
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
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        if (EnchantUtils.hasEnch(user,INSTANT_DEATH) && target instanceof LivingEntity) {

            LivingEntity victim = (LivingEntity) target;
            victim.setHealth(0);
        }
    }
}
