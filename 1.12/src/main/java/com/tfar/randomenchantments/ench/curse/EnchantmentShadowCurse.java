package com.tfar.randomenchantments.ench.curse;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.ANVIL;
import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.DISABLED;
import static com.tfar.randomenchantments.EnchantmentConfig.curses;
import static com.tfar.randomenchantments.init.ModEnchantment.*;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentShadowCurse extends Enchantment {
    public EnchantmentShadowCurse() {

        super(Rarity.RARE, EnumEnchantmentType.BREAKABLE, list);
        this.setRegistryName("shadow");
        this.setName("shadow");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 25;
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
    public boolean canApply(ItemStack stack)
    {
        return curses.enableShadow != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isCurse()
    {
        return true;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return curses.enableShadow == ANVIL;
    }

    private static EntityEquipmentSlot[] list = new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD,
            EntityEquipmentSlot.CHEST,EntityEquipmentSlot.LEGS,EntityEquipmentSlot.FEET,
            EntityEquipmentSlot.MAINHAND,EntityEquipmentSlot.OFFHAND};

    @SubscribeEvent
public static void applyShadow(TickEvent.PlayerTickEvent e) {
        if (Math.random() > .05) return;
        EntityPlayer p = e.player;
        if (p.world.isRemote) return;
        for (EntityEquipmentSlot slot : list) {
            ItemStack stack = p.getItemStackFromSlot(slot);
            if (EnchantmentHelper.getEnchantmentLevel(SHADOW, stack) == 0) continue;
            if (isDark(p)) repair(stack);
            else damage(stack);
        }
    }
    private static boolean isDark(EntityLivingBase e) {
        BlockPos blockpos = e.getPosition();
        World world = e.world;
        int i = world.getLightFromNeighbors(blockpos);
        if (world.isThundering())
        {
            int j = world.getSkylightSubtracted();
            world.setSkylightSubtracted(10);
            i = world.getLightFromNeighbors(blockpos);
            world.setSkylightSubtracted(j);
        }
        return i < 8;
    }
    public static void repair(ItemStack stack){
        stack.setItemDamage(stack.getItemDamage()-1);
    }
    public static void damage(ItemStack stack){
        if (stack.getItemDamage() < stack.getMaxDamage())
        stack.setItemDamage(stack.getItemDamage()+1);
    }
}

