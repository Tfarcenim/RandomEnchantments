package com.tfar.randomenchants.ench.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.MOD_ID;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.TRUE_LIFESTEAL;
import static com.tfar.randomenchants.RandomEnchants.WEAPONS;

@Mod.EventBusSubscriber(modid= MOD_ID)
public class EnchantmentTrueLifesteal extends Enchantment {
    public EnchantmentTrueLifesteal() {

        super(Rarity.RARE, WEAPONS, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("true_lifesteal");
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
        return weapons.enable2Lifesteal != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return weapons.enable2Lifesteal == ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return weapons.enable2Lifesteal != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return weapons.enable2Lifesteal == NORMAL;
    }

    @SubscribeEvent
    public static void onAttack(LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
            if (EnchantmentHelper.getMaxEnchantmentLevel(TRUE_LIFESTEAL, player) > 0) {
                LivingEntity entity = event.getEntityLiving();
                float damage = event.getAmount();
                entity.attackEntityFrom(DamageSource.GENERIC, damage*1.5f);
                player.heal(damage);
            }
        }
    }
}
