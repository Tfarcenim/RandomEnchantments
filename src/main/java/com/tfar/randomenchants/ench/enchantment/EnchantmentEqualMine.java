package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.tools;
import static com.tfar.randomenchants.init.ModEnchantment.EQUAL_MINE;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentEqualMine extends Enchantment {
    public EnchantmentEqualMine() {

        super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("equal_mine");
        this.setName("equal_mine");
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
  public boolean canApply(@Nonnull ItemStack stack){
    return tools.enableEqualMine != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return tools.enableEqualMine == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return tools.enableEqualMine != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return tools.enableEqualMine == NORMAL;
  }

@SubscribeEvent
public static void onBreakSpeed(PlayerEvent.BreakSpeed e) {
        EntityPlayer p = e.getEntityPlayer();
        IBlockState state = e.getState();
        World world = p.getEntityWorld();
    BlockPos pos = e.getPos();
        float hardness = state.getBlockHardness(world,pos);
    if (EnchantmentHelper.getMaxEnchantmentLevel(EQUAL_MINE, p) > 0) {

            float oldSpeed = e.getOriginalSpeed();
            if (hardness<1) hardness =1;
        float newSpeed= hardness * oldSpeed;
                e.setNewSpeed(newSpeed);
        }
    }
}

