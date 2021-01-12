package com.tfar.randomenchants.ench.enchantment;

import static com.tfar.randomenchants.Config.Restriction.ANVIL;
import static com.tfar.randomenchants.Config.Restriction.DISABLED;
import static com.tfar.randomenchants.Config.Restriction.NORMAL;
import static com.tfar.randomenchants.RandomEnchants.SWORDS_BOWS;

import com.tfar.randomenchants.Config;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class EnchantmentInstantDeath extends Enchantment {
    public EnchantmentInstantDeath() {

        super(Rarity.RARE, SWORDS_BOWS, new EquipmentSlotType[] {
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
    public boolean canApply(ItemStack stack) {
        return Config.ServerConfig.instant_death.get() != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return Config.ServerConfig.instant_death.get() == ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return Config.ServerConfig.instant_death.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.ServerConfig.instant_death.get() == NORMAL;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity) {
            LivingEntity victim = (LivingEntity) target;
            float victimHealth = victim.getMaxHealth();
            float victimArmor = victim.getTotalArmorValue();
            // Debug:
//            System.out.println("Victim Absorption Amount: " + victim.getAbsorptionAmount());
//            System.out.println("Victim Health: " + victim.getHealth());
//            System.out.println("Victim Max Health: " + victim.getMaxHealth());
//            System.out.println("Victim Total Armor Value: " + victim.getTotalArmorValue());
            victim.attackEntityFrom(DamageSource.MAGIC, (victimHealth + victimArmor * 2));

        }
    }
}
