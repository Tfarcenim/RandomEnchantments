package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ServerWorld;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.WEAPONS;


@Mod.EventBusSubscriber(modid= RandomEnchants.MOD_ID)
public class EnchantmentLightning extends Enchantment {
    public EnchantmentLightning() {

        super(Rarity.RARE, WEAPONS, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("lightning");
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
        return weapons.enableLightning != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return weapons.enableLightning == ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return weapons.enableLightning != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return weapons.enableLightning == NORMAL;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
if (!user.world.isRemote)
    ((ServerWorld)user.world).addLightningBolt(new LightningBoltEntity(user.world, target.posX, target.posY, target.posZ, false));
    }
}

