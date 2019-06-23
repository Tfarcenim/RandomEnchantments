package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.PARALYSIS;
import static com.tfar.randomenchants.RandomEnchants.WEAPONS;

@Mod.EventBusSubscriber(modid= RandomEnchants.MOD_ID)
public class EnchantmentParalysis extends Enchantment {
    public EnchantmentParalysis() {

        super(Rarity.RARE, WEAPONS, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("paralysis");
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
        return weapons.enableParalysis != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return weapons.enableParalysis == ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return weapons.enableParalysis != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return weapons.enableParalysis == NORMAL;
    }

    @Override
    public void onEntityDamaged(LivingEntity player, Entity target, int level)  {

        if (EnchantmentHelper.getMaxEnchantmentLevel(PARALYSIS, player) > 0 && target instanceof LivingEntity && player instanceof PlayerEntity){
            ((LivingEntity)target).
                    addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 200, 128));
            ((LivingEntity)target).
                    addPotionEffect(new EffectInstance(Effects.SLOWNESS, 200, 5));
            ((LivingEntity)target).
                    addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 200, 4));
            ((LivingEntity)target).
                    addPotionEffect(new EffectInstance(Effects.WEAKNESS, 200, 20));

        }
    }
}

