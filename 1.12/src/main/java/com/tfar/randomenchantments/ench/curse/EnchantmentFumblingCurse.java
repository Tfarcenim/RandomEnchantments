package com.tfar.randomenchantments.ench.curse;

import com.tfar.randomenchantments.EnchantmentConfig;
import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.EnchantmentConfig.curses;
import static com.tfar.randomenchantments.init.ModEnchantment.FUMBLING;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentFumblingCurse extends Enchantment {
    public EnchantmentFumblingCurse() {

        super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("fumbling");
        this.setName("fumbling");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 25;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return 50;
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
        return curses.enableFumbling != EnchantmentConfig.EnumAccessLevel.DISABLED && stack.isItemStackDamageable() || super.canApply(stack);
    }

@SubscribeEvent
public static void onBreakSpeed(PlayerEvent.BreakSpeed e) {
        EntityPlayer p = e.getEntityPlayer();
        if (EnchantmentHelper.getMaxEnchantmentLevel(FUMBLING, p) > 0) {
            float oldSpeed = e.getOriginalSpeed();
                e.setNewSpeed((float)Math.sqrt(oldSpeed));
        }
    }
}

