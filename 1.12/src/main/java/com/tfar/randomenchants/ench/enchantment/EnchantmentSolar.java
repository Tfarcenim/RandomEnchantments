package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.init.ModEnchantment.SOLAR;
import static com.tfar.randomenchants.util.EnchantmentUtils.isDark;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentSolar extends Enchantment {
    public EnchantmentSolar() {

        super(Rarity.RARE, EnumEnchantmentType.BREAKABLE, list);
        this.setRegistryName("solar");
        this.setName("solar");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 5 + 10 * (level - 1);
    }

    @Override
    public int getMaxEnchantability(int level) {
        return super.getMinEnchantability(level) + 25;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }


    private static EntityEquipmentSlot[] list = new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD,
            EntityEquipmentSlot.CHEST,EntityEquipmentSlot.LEGS,EntityEquipmentSlot.FEET,
            EntityEquipmentSlot.MAINHAND,EntityEquipmentSlot.OFFHAND};

    @Override
    public boolean canApply(ItemStack stack){
        return weapons.enableSolar != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return weapons.enableSolar == ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return weapons.enableSolar != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return weapons.enableSolar == NORMAL;
    }

    @SubscribeEvent
public static void applySolar(TickEvent.PlayerTickEvent e) {
        EntityPlayer p = e.player;
        if (p.world.isRemote) return;
        for (EntityEquipmentSlot slot : list) {
            ItemStack stack = p.getItemStackFromSlot(slot);
            int level = EnchantmentHelper.getEnchantmentLevel(SOLAR, stack);
            if (level == 0 || Math.random()/level > 0.004) continue;
            if (!isDark(p))stack.damageItem(-1,p);
        }
    }
}

