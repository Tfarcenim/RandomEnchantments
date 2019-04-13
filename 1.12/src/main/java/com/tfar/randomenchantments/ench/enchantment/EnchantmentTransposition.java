package com.tfar.randomenchantments.ench.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;

//import static com.tfar.randomenchantments.init.ModEnchantment.TRANSPOSITION;

public class EnchantmentTransposition extends Enchantment {
    public EnchantmentTransposition() {

        super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("transposition");
        this.setName("transposition");
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
 //       if (EnchantmentHelper.getMaxEnchantmentLevel(TRANSPOSITION, user) > 0){
            BlockPos pos1=target.getPosition();
            BlockPos pos2=user.getPosition();
            target.setPositionAndUpdate(pos2.getX(),pos2.getY(),pos2.getZ());
            user.setPositionAndUpdate(pos1.getX(),pos1.getY(),pos1.getZ());
    //    }
    }
}
