package com.tfar.randomenchants.ench.curse;

import com.tfar.randomenchants.EnchantmentConfig;
import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.ANVIL;
import static com.tfar.randomenchants.EnchantmentConfig.curses;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.FUMBLING;

@Mod.EventBusSubscriber(modid= RandomEnchants.MOD_ID)
public class EnchantmentFumblingCurse extends Enchantment {
    public EnchantmentFumblingCurse() {

        super(Rarity.RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("fumbling");
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
    public boolean isCurse()
    {
        return true;
    }
    @Override
    public boolean canApply(ItemStack stack)
    {
        return curses.enableFumbling != EnchantmentConfig.EnumAccessLevel.DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return curses.enableFumbling == ANVIL;
    }

@SubscribeEvent
public static void onBreakSpeed(PlayerEvent.BreakSpeed e) {
        PlayerEntity p = e.getEntityPlayer();
        if (EnchantmentHelper.getMaxEnchantmentLevel(FUMBLING, p) > 0) {
            float oldSpeed = e.getOriginalSpeed();
                e.setNewSpeed((float)Math.sqrt(oldSpeed));
        }
    }
}

