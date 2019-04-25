package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.ANVIL;
import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.DISABLED;
import static com.tfar.randomenchantments.EnchantmentConfig.tools;
import static com.tfar.randomenchantments.RandomEnchantments.AXE;
import static com.tfar.randomenchantments.ench.curse.EnchantmentShadowCurse.damage;
import static com.tfar.randomenchantments.init.ModEnchantment.LUMBERJACK;


@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentLumberjack extends Enchantment {
    public EnchantmentLumberjack() {

        super(Rarity.RARE, AXE, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("lumberjack");
        this.setName("lumberjack");
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
        return tools.enableLumberjack != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return tools.enableLumberjack == ANVIL;
    }

    @SubscribeEvent
    public static void onWoodBreak(BlockEvent.BreakEvent e){
        EntityPlayer p = e.getPlayer();
        if(EnchantmentHelper.getMaxEnchantmentLevel(LUMBERJACK, p)<1)return;
        ItemStack stack = p.getHeldItemMainhand();
        IBlockState state = e.getState();
        BlockPos pos = e.getPos();
        Block block = state.getBlock();
        if (!block.isWood(p.world,pos))return;
        while(block.isWood(p.world,pos)){
            pos = pos.up();
            state = p.world.getBlockState(pos);
            block = state.getBlock();
            if (block.isWood(p.world,pos)){
                damage(stack);
                p.world.setBlockToAir(pos);
                EntityItem entityItem = new EntityItem(p.world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(state.getBlock(),1,block.getMetaFromState(state)));
                p.world.spawnEntity(entityItem);
            }
        }
    }
}

