package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.EnchantmentConfig;
import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.EnchantmentConfig.tools;
import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.RandomEnchantments.PICKAXE;
import static com.tfar.randomenchantments.init.ModEnchantment.STONELOVER;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentStoneLover extends Enchantment{
    public EnchantmentStoneLover() {

        super(Rarity.RARE, PICKAXE, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("stonelover");
        this.setName("stonelover");
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
    public boolean isAllowedOnBooks() {
        return tools.enableStonelover == EnchantmentConfig.EnumAccessLevel.NORMAL;
    }

    @Override
    public boolean canApply(ItemStack stack){
        return tools.enableStonelover != EnchantmentConfig.EnumAccessLevel.DISABLED && super.canApply(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return isAllowedOnBooks();
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent e) {
        EntityPlayer p = e.getPlayer();
        if ((EnchantmentHelper.getMaxEnchantmentLevel(STONELOVER, p) > 0)){
            if(e.getState().getBlock() != Blocks.STONE)return;
            ItemStack stack = p.getHeldItemMainhand();
            if (Math.random()>.80)return;
            stack.setItemDamage(stack.getItemDamage()-1);
        }
    }
}

