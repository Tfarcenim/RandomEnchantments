package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.tfar.randomenchants.Config.Restriction.*;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)
public class EnchantmentAssimilation extends Enchantment {
  public EnchantmentAssimilation() {

    super(Rarity.VERY_RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("assimilation");
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
  public boolean canApply(ItemStack stack) {
    return Config.ServerConfig.disarm.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.disarm.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.disarm.get() == NORMAL;
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.disarm.get() == ANVIL;
  }

  public static void repair(PlayerEntity player, List<ItemStack> drops) {
    List<ItemStack> remove = new ArrayList<>();
    ItemStack tool = player.getHeldItemMainhand();
    for (ItemStack stack : drops) {
      int fuelvalue = (int)Math.ceil(ForgeHooks.getBurnTime(stack)/50d);
      if (fuelvalue == 0) continue;
      int toRepair = tool.getDamage();
      if (toRepair >= fuelvalue) {
        tool.setDamage(toRepair - fuelvalue);
        remove.add(stack);
      }
    }
    drops.removeAll(remove);
  }
}

