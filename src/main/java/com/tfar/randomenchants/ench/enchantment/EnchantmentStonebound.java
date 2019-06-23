package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.tools;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.STONEBOUND;

@Mod.EventBusSubscriber(modid= RandomEnchants.MOD_ID)
public class EnchantmentStonebound extends Enchantment {
    public EnchantmentStonebound() {

        super(Rarity.RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("stonebound");
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
        return tools.enableStonebound != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return tools.enableStonebound == ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return tools.enableStonebound != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return tools.enableStonebound == NORMAL;
    }

    @SubscribeEvent
    public static void onAttack(LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
            if (EnchantmentHelper.getMaxEnchantmentLevel(STONEBOUND, player) > 0) {
                LivingEntity entity = event.getEntityLiving();
                ItemStack stack = player.getHeldItemMainhand();
                float reduction = .02f * stack.getDamage();
                entity.heal(reduction);
            }
        }
    }
    @SubscribeEvent
    public void onBreakBlock(PlayerEvent.BreakSpeed e)  {
        PlayerEntity p = e.getEntityPlayer();
        if ((EnchantmentHelper.getMaxEnchantmentLevel(STONEBOUND, p) > 0)){
            float oldSpeed = e.getOriginalSpeed();
            ItemStack stack = p.getHeldItemMainhand();
            float increase = .02f * stack.getDamage();
            e.setNewSpeed(increase+oldSpeed);
        }
    }
}

