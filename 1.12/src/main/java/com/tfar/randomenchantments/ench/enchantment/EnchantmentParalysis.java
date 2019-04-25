package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.EnchantmentConfig;
import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.ANVIL;
import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.DISABLED;
import static com.tfar.randomenchantments.EnchantmentConfig.tools;
import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.RandomEnchantments.WEAPONS;
import static com.tfar.randomenchantments.init.ModEnchantment.PARALYSIS;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentParalysis extends Enchantment {
    public EnchantmentParalysis() {

        super(Rarity.RARE, WEAPONS, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("paralysis");
        this.setName("paralysis");
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
        return weapons.enableParalysis != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return weapons.enableParalysis == ANVIL;
    }

    @Override
    public void onEntityDamaged(EntityLivingBase player, Entity target, int level)  {

        if (EnchantmentHelper.getMaxEnchantmentLevel(PARALYSIS, player) > 0 && target instanceof EntityLivingBase && player instanceof EntityPlayer){
            ((EntityLivingBase)target).
                    addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 200, 128));
            ((EntityLivingBase)target).
                    addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 5));
            ((EntityLivingBase)target).
                    addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 200, 4));
            ((EntityLivingBase)target).
                    addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 20));

        }
    }
}

