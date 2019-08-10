package com.tfar.randomenchants.ench.curse;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.Config.Restriction.*;

@Mod.EventBusSubscriber(modid= RandomEnchants.MODID)
public class ButterfingersCurse extends Enchantment {
    public ButterfingersCurse() {

        super(Rarity.RARE, EnchantmentType.ALL, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("butterfingers");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 25;
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
        return Config.ServerConfig.butterfingers.get() != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return Config.ServerConfig.butterfingers.get() == ANVIL;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        if (!(user instanceof PlayerEntity))return;
        PlayerEntity player = (PlayerEntity)user;
        if (Math.random()>.50)return;
        player.dropItem(player.getItemStackFromSlot(EquipmentSlotType.MAINHAND),true);
        player.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
        //EntityItem itemStack = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        //world.spawnEntity(itemStack);
    }
}
