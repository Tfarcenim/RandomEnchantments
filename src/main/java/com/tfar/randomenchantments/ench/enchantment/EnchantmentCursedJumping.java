package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchantments.CommonProxy.WEAPONS;
import static com.tfar.randomenchantments.init.ModEnchantment.CURSED_JUMP;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentCursedJumping extends Enchantment {
    public EnchantmentCursedJumping() {

        super(Rarity.RARE, WEAPONS, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("cursed_jump");
        this.setName("cursed_jump");
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
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level)  {

        if ((EnchantmentHelper.getMaxEnchantmentLevel(CURSED_JUMP, user) > 0 && target instanceof EntityLivingBase)){
            ((EntityLivingBase)target).
                    addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 40, 127));
        }
        }
    }

