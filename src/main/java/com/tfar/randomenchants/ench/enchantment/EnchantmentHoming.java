package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.HOMING;
import static com.tfar.randomenchants.util.EventHandler.homingarrows;

@Mod.EventBusSubscriber(modid= RandomEnchants.MOD_ID)

public class EnchantmentHoming extends Enchantment {
  public EnchantmentHoming() {
    super(Rarity.RARE, EnchantmentType.BOW, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("homing");
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
    return weapons.enableHoming != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableHoming == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableHoming != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return weapons.enableHoming == NORMAL;
  }

  @SubscribeEvent
  public static void arrowLoose(EntityJoinWorldEvent event)  {
    Entity entity = event.getEntity();
    if (!(entity instanceof AbstractArrowEntity))return;
    Entity shooter = ((AbstractArrowEntity) entity).getShooter();
    if (!(shooter instanceof PlayerEntity))return;
    PlayerEntity player = (PlayerEntity) shooter;
      if (!EnchantUtils.hasEnch(player,HOMING))return;
    entity.setNoGravity(true);
    homingarrows.put((AbstractArrowEntity)entity,entity.getMotion().length());
  }
}

