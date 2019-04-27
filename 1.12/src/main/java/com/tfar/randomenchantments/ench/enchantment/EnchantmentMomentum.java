package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchantments.EnchantmentConfig.tools;
import static com.tfar.randomenchantments.init.ModEnchantment.MOMENTUM;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)
public class EnchantmentMomentum extends Enchantment {
  public EnchantmentMomentum() {

    super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("momentum");
    this.setName("momentum");
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
    return tools.enableMomentum != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return tools.enableMomentum == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return tools.enableMomentum != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return tools.enableMomentum == NORMAL;
  }

  @SubscribeEvent
  public static void onBreakSpeed(PlayerEvent.BreakSpeed e) {
    EntityPlayer p = e.getEntityPlayer();

    if (EnchantmentHelper.getMaxEnchantmentLevel(MOMENTUM, p) > 0) {
      ItemStack stack = p.getHeldItemMainhand();
      NBTTagCompound compound = stack.getTagCompound();
      int momentum = compound.getInteger("momentum");
      float oldSpeed = e.getOriginalSpeed();
      float newSpeed = oldSpeed + .05f * momentum;
      e.setNewSpeed(newSpeed);
    }
  }

  @SubscribeEvent
  public static void onBreak(BlockEvent.BreakEvent e) {
    EntityPlayer p = e.getPlayer();
    if (EnchantmentHelper.getMaxEnchantmentLevel(MOMENTUM, p) == 0) return;
    ItemStack stack = p.getHeldItemMainhand();
    NBTTagCompound compound = stack.getTagCompound();
    int momentum = compound.getInteger("momentum");
    String cachedBlock = compound.getString("block");
    String currentBlock = e.getState().getBlock().toString();
    if (!cachedBlock.equals(currentBlock)) {
      compound.setInteger("momentum", 0);
      compound.setString("block", currentBlock);
    } else {
      compound.setInteger("momentum", momentum + 1);
    }
  }
}

