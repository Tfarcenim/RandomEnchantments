package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.tools;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.STONELOVER;
import static com.tfar.randomenchants.RandomEnchants.PICKAXE;

@Mod.EventBusSubscriber(modid= RandomEnchants.MOD_ID)
public class EnchantmentStoneLover extends Enchantment{
    public EnchantmentStoneLover() {

        super(Rarity.RARE, PICKAXE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("stonelover");
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
        return tools.enableStonelover != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return tools.enableStonelover != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return tools.enableStonelover == NORMAL;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return tools.enableStonelover == ANVIL;
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent e) {
        PlayerEntity p = e.getPlayer();
        if ((EnchantmentHelper.getMaxEnchantmentLevel(STONELOVER, p) > 0)){
            if(e.getState().getBlock() != Blocks.STONE)return;
            ItemStack stack = p.getHeldItemMainhand();
            if (Math.random()>.80)return;
            stack.setDamage(stack.getDamage()-1);
        }
    }
}

