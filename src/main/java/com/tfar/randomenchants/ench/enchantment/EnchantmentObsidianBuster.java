package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.tools;
import static com.tfar.randomenchants.init.ModEnchantment.OBSIDIAN_BUSTER;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentObsidianBuster extends Enchantment {
    public EnchantmentObsidianBuster() {

        super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("obsidian_buster");
        this.setName("obsidian_buster");
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
    public boolean canApply(ItemStack stack){
        return tools.enableObsidianBuster != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return tools.enableObsidianBuster == ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return tools.enableObsidianBuster != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return tools.enableObsidianBuster == NORMAL;
    }

    @SubscribeEvent
public static void onBreakSpeed(PlayerEvent.BreakSpeed e) {
        EntityPlayer p = e.getEntityPlayer();
        IBlockState state = e.getState();
        if (EnchantmentHelper.getMaxEnchantmentLevel(OBSIDIAN_BUSTER, p) > 0 && state.getBlock() == Blocks.OBSIDIAN) {
            float oldSpeed = e.getOriginalSpeed();
                e.setNewSpeed(oldSpeed + 100F);
        }
    }
}

