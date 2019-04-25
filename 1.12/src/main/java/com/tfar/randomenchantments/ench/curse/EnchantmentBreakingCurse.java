package com.tfar.randomenchantments.ench.curse;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchantments.EnchantmentConfig.curses;
import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.init.ModEnchantment.BREAKING;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentBreakingCurse extends Enchantment {
    public EnchantmentBreakingCurse() {

        super(Rarity.RARE, EnumEnchantmentType.BREAKABLE, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("breaking");
        this.setName("breaking");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 25;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return super.getMinEnchantability(level) + 25;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
    @Override
    public boolean canApply(ItemStack stack)
    {
        return curses.enableBreaking != DISABLED && stack.isItemStackDamageable() || super.canApply(stack);
    }

    @Override
    public boolean isCurse()
    {
        return true;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return curses.enableBreaking == ANVIL;
    }

    @SubscribeEvent
public static void amplifyDamage(BlockEvent.BreakEvent e) {
        EntityPlayer p = e.getPlayer();
        ItemStack stack = p.getHeldItemMainhand();
        stack.setItemDamage(stack.getItemDamage()+ EnchantmentHelper.getMaxEnchantmentLevel(BREAKING,p));
}
}

