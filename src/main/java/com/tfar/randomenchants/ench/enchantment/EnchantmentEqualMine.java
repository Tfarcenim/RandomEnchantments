package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.EQUAL_MINE;

@Mod.EventBusSubscriber(modid= RandomEnchants.MODID)
public class EnchantmentEqualMine extends Enchantment {
    public EnchantmentEqualMine() {

        super(Rarity.RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("equal_mine");
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
  public boolean canApply(@Nonnull ItemStack stack){
    return Config.ServerConfig.equalmine.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.equalmine.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.equalmine.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.equalmine.get() == NORMAL;
  }

@SubscribeEvent
public static void onBreakSpeed(PlayerEvent.BreakSpeed e) {
        PlayerEntity p = e.getPlayer();
        BlockState state = e.getState();
        World world = p.getEntityWorld();
    BlockPos pos = e.getPos();
        float hardness = state.getBlockHardness(world,pos);
    if (EnchantUtils.hasEnch(p, EQUAL_MINE)) {

            float oldSpeed = e.getOriginalSpeed();
            if (hardness<1) hardness =1;
        float newSpeed= hardness * oldSpeed;
                e.setNewSpeed(newSpeed);
        }
    }
}

