package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantmentUtils;
import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.init.ModEnchantment.LIGHTING;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)
public class EnchantmentTorches extends Enchantment {
  public EnchantmentTorches() {

    super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    setRegistryName("torches");
    setName("torches");
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
  public boolean canApply(ItemStack stack) {
    return weapons.enableTorches != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableTorches != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return weapons.enableTorches == NORMAL;
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableTorches == ANVIL;
  }

  @SubscribeEvent
  public static void onBlockHit(ProjectileImpactEvent e) {
    Entity arrow = e.getEntity();
    Entity block = e.getRayTraceResult().entityHit;
    if (EnchantmentUtils.isArrowinBlock(arrow, block)) return;
    EntityPlayer user = (EntityPlayer) ((EntityArrow) arrow).shootingEntity;
    if (EnchantmentHelper.getMaxEnchantmentLevel(LIGHTING, user) <= 0) return;
    World world = arrow.world;
    if (!world.isRemote) {
      BlockPos pos = arrow.getPosition();
      if (world.getBlockState(pos).getBlock() != Blocks.AIR)return;
      Block torch = Blocks.TORCH;
      IBlockState iblockstate = torch.getDefaultState();
      switch (e.getRayTraceResult().sideHit) {
        case UP : case DOWN: {
          world.setBlockState(pos, iblockstate);break;
        }
        case EAST:{
          world.setBlockState(pos,iblockstate.withProperty(BlockTorch.FACING, EnumFacing.EAST));break;
        }
        case WEST:{
          world.setBlockState(pos,iblockstate.withProperty(BlockTorch.FACING, EnumFacing.WEST));break;
        }
        case SOUTH:{
          world.setBlockState(pos,iblockstate.withProperty(BlockTorch.FACING, EnumFacing.SOUTH));break;
        }
        case NORTH:{
          world.setBlockState(pos,iblockstate.withProperty(BlockTorch.FACING, EnumFacing.NORTH));break;
        }
      }
      arrow.setDead();
    }
  }
}

