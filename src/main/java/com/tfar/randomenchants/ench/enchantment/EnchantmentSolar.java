package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.SOLAR;
import static com.tfar.randomenchants.util.EnchantUtils.isDark;

@Mod.EventBusSubscriber(modid= RandomEnchants.MOD_ID)
public class EnchantmentSolar extends Enchantment {
    public EnchantmentSolar() {

        super(Rarity.RARE, EnchantmentType.BREAKABLE, list);
        this.setRegistryName("solar");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 5 + 10 * (level - 1);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }


    private static EquipmentSlotType[] list = new EquipmentSlotType[]{EquipmentSlotType.HEAD,
            EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET,
            EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND};

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
        PlayerEntity p = e.player;
        if (p.world.isRemote) return;
        for (EquipmentSlotType slot : list) {
            ItemStack stack = p.getItemStackFromSlot(slot);
            int level = EnchantmentHelper.getEnchantmentLevel(SOLAR, stack);
            if (level == 0 || Math.random()/level > 0.004) continue;
            if (!isDark(p))stack.damageItem(-1,p,playerEntity -> playerEntity.sendBreakAnimation(p.getActiveHand()));
        }
    }
}

