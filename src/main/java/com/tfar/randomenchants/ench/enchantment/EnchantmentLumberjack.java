package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.tools;
import static com.tfar.randomenchants.RandomEnchants.AXE;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.LUMBERJACK;


@Mod.EventBusSubscriber(modid= RandomEnchants.MOD_ID)
public class EnchantmentLumberjack extends Enchantment {
    public EnchantmentLumberjack() {

        super(Rarity.RARE, AXE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("lumberjack");
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
        return tools.enableLumberjack != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return tools.enableLumberjack == ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return tools.enableLumberjack != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return tools.enableLumberjack == NORMAL;
    }

    @SubscribeEvent
    public static void onWoodBreak(BlockEvent.BreakEvent e){
        PlayerEntity p = e.getPlayer();
        if(!EnchantUtils.hasEnch(p,LUMBERJACK))return;
        ItemStack stack = p.getHeldItemMainhand();
        BlockState state = e.getState();
        BlockPos pos = e.getPos();
        Block block = state.getBlock();
        if (!block.isIn(BlockTags.LOGS))return;
        while(block.isIn(BlockTags.LOGS)){
            pos = pos.up();
            state = p.world.getBlockState(pos);
            block = state.getBlock();
            if (block.isIn(BlockTags.LOGS)){
                stack.damageItem(1,p, playerEntity -> playerEntity.sendBreakAnimation(p.getActiveHand()));
                p.world.destroyBlock(pos,true);
            }
        }
    }
}

