package com.tfar.randomenchants.ench.curse;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

import static com.tfar.randomenchants.Config.Restriction.ANVIL;
import static com.tfar.randomenchants.Config.Restriction.DISABLED;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.SHADOW;
import static com.tfar.randomenchants.util.EnchantUtils.isDark;

@Mod.EventBusSubscriber(modid= RandomEnchants.MODID)
public class ShadowCurse extends Enchantment {
    public ShadowCurse() {

        super(Rarity.RARE, EnchantmentType.BREAKABLE, list);
        this.setRegistryName("shadow");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 25;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
    @Override
    public boolean canApply(@Nonnull ItemStack stack)
    {
        return Config.ServerConfig.shadow.get() != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isCurse()
    {
        return true;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return Config.ServerConfig.shadow.get() == ANVIL;
    }

    private static EquipmentSlotType[] list = new EquipmentSlotType[]{EquipmentSlotType.HEAD,
            EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET,
            EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND};

    @SubscribeEvent
public static void applyShadow(TickEvent.PlayerTickEvent e) {
        if (Math.random() > .05) return;
        PlayerEntity p = e.player;
        if (p.world.isRemote) return;
        for (EquipmentSlotType slot : list) {
            ItemStack stack = p.getItemStackFromSlot(slot);
            if (EnchantmentHelper.getEnchantmentLevel(SHADOW, stack) == 0) continue;
            stack.damageItem((isDark(p) ? -1: 1),p,playerEntity -> playerEntity.sendBreakAnimation(p.getActiveHand()));
        }
    }
}

