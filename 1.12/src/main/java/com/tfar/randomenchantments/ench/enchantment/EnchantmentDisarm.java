package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.EnchantmentConfig;
import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.RandomEnchantments.WEAPONS;
import static com.tfar.randomenchantments.init.ModEnchantment.DISARM;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentDisarm extends Enchantment {
    public EnchantmentDisarm() {

        super(Rarity.RARE, WEAPONS, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("disarm");
        this.setName("disarm");
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
        return weapons.enableDisarm == EnchantmentConfig.EnumAccessLevel.NORMAL;
    }

    @Override
    public boolean canApply(ItemStack stack){
        return weapons.enableDisarm != EnchantmentConfig.EnumAccessLevel.DISABLED && super.canApply(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return isAllowedOnBooks();
    }

    @Override
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level)  {
        if ((EnchantmentHelper.getMaxEnchantmentLevel(DISARM, user) > 0 && target instanceof EntityLivingBase)){
            World world = user.world;
            BlockPos pos = user.getPosition();
            EntityLivingBase victim = (EntityLivingBase) target;
            ItemStack stack = victim.getHeldItemMainhand();
            victim.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
            EntityItem itemStack = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            world.spawnEntity(itemStack);
        }
    }
}

