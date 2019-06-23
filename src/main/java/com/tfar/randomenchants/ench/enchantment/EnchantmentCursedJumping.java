package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.CURSED_JUMP;
import static com.tfar.randomenchants.RandomEnchants.WEAPONS;

@Mod.EventBusSubscriber(modid= RandomEnchants.MOD_ID)
public class EnchantmentCursedJumping extends Enchantment {
    public EnchantmentCursedJumping() {

        super(Rarity.RARE, WEAPONS, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("cursed_jump");
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
        return weapons.enableCursedJumping != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return weapons.enableCursedJumping == ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return weapons.enableCursedJumping != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return weapons.enableCursedJumping == NORMAL;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level)  {

        if ((EnchantmentHelper.getMaxEnchantmentLevel(CURSED_JUMP, user) > 0 && target instanceof LivingEntity)){
            ((LivingEntity)target).
                    addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 40, 127));
        }
        }
    }

