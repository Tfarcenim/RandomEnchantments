package com.tfar.randomenchantments.ench.curse;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchantments.EnchantmentConfig.curses;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentButterfingersCurse extends Enchantment {
    public EnchantmentButterfingersCurse() {

        super(Rarity.RARE, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("butterfingers");
        this.setName("butterfingers");
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
    public boolean isCurse() {
        return true;
    }
    @Override
    public boolean canApply(ItemStack stack)
    {
        return curses.enableButterfingers != DISABLED && stack.isItemStackDamageable() || super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return curses.enableButterfingers == ANVIL;
    }

    @Override
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
        if (!(user instanceof EntityPlayer))return;
        EntityPlayer player = (EntityPlayer)user;
        if (Math.random()>.50)return;
        player.dropItem(player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND),true);
        player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
        //EntityItem itemStack = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        //world.spawnEntity(itemStack);
    }
}
