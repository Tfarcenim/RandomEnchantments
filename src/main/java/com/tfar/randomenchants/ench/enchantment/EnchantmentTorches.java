package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.LIGHTING;

@Mod.EventBusSubscriber(modid = RandomEnchants.MOD_ID)
public class EnchantmentTorches extends Enchantment {
  public EnchantmentTorches() {

    super(Rarity.RARE, EnchantmentType.BOW, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    setRegistryName("torches");
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
    if (!(arrow instanceof  AbstractArrowEntity))return;
    RayTraceResult result = e.getRayTraceResult();
    if (!(result instanceof BlockRayTraceResult))return;
    Entity shooter = ((AbstractArrowEntity) arrow).getShooter();
    if (!(shooter instanceof LivingEntity))return;
    LivingEntity user = (LivingEntity)((AbstractArrowEntity) arrow).getShooter();
    if (user == null)return;
    if (!EnchantUtils.hasEnch(user,LIGHTING)) return;
    World world = arrow.world;
    if (!world.isRemote) {
      BlockPos pos = arrow.getPosition();
      if (world.getBlockState(pos).getBlock().isAir(world.getBlockState(pos),null,null))return;
      Block torch = Blocks.TORCH;
      BlockState blockState = torch.getDefaultState();
      switch (((BlockRayTraceResult) result).getFace()) {
        case UP : case DOWN: {
          world.setBlockState(pos, blockState.updatePostPlacement(Direction.DOWN,blockState,world,pos,pos.down()));break;
        }
        case EAST:{
          world.setBlockState(pos,blockState.updatePostPlacement(Direction.DOWN,blockState,world,pos,pos.down()));
        }
        case WEST:{
          world.setBlockState(pos,blockState.updatePostPlacement(Direction.DOWN,blockState,world,pos,pos.down()));
        }
        case SOUTH:{
          world.setBlockState(pos,blockState.updatePostPlacement(Direction.DOWN,blockState,world,pos,pos.down()));
        }
        case NORTH:{
          world.setBlockState(pos,blockState.updatePostPlacement(Direction.DOWN,blockState,world,pos,pos.down()));
        }
      }
      arrow.remove();
    }
  }
}

