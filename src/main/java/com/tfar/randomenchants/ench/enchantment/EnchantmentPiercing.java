package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.entity.EntitySpecialArrow;
import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.init.ModEnchantment.*;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentPiercing extends Enchantment {
  public EnchantmentPiercing() {
    super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("piercing");
    this.setName("piercing");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 25;
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
    return weapons.enablePiercing != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enablePiercing == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enablePiercing != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  protected boolean canApplyTogether(Enchantment ench) {
    return super.canApplyTogether(ench) && !((ench == MULTISHOT) || (ench instanceof EnchantmentArrowInfinite));
  }

  @Override
  public boolean isAllowedOnBooks() {
    return weapons.enableBackToTheChamber == NORMAL;
  }


  @SubscribeEvent
  public static void arrowLoose(ArrowLooseEvent event) {

    if (EnchantmentHelper.getEnchantmentLevel(PIERCING, event.getBow()) > 0) {

      event.setCanceled(true);

      EntityPlayer player = event.getEntityPlayer();

      World world = event.getWorld();

      ItemStack stack = findAmmo(player);

      if (!stack.isEmpty()) {

        float f = ItemBow.getArrowVelocity(event.getCharge());

        if (f >= 0.1) {
          boolean flag1 = player.capabilities.isCreativeMode || (stack.getItem() instanceof ItemArrow && ((ItemArrow) stack.getItem()).isInfinite(stack, stack, player));

          if (!world.isRemote) {
            EntityArrow entityarrow = createArrow(world, stack, player);
            entityarrow.shoot(player, player.rotationPitch, player.rotationYaw, 0, f * 3, 0);

            if (f >= 1) {
              entityarrow.setIsCritical(true);
            }

            int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

            if (j > 0) {
              entityarrow.setDamage(entityarrow.getDamage() + (double) j * 0.5 + 0.5);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);

            if (k > 0) {
              entityarrow.setKnockbackStrength(k);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
              entityarrow.setFire(100);
            }

            stack.damageItem(1, player);

            if (flag1 || player.capabilities.isCreativeMode && (stack.getItem() == Items.SPECTRAL_ARROW || stack.getItem() == Items.TIPPED_ARROW)) {
              entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
            }

            world.spawnEntity(entityarrow);
          }

          world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1, 1 / (player.getRNG().nextFloat() * 0.4F + 1.2F) + f * 0.5F);

          if (!flag1 && !player.capabilities.isCreativeMode) {
            stack.shrink(1);

            if (stack.isEmpty()) {
              player.inventory.deleteStack(stack);
            }
          }
        }
      }
    }
  }

  public static EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {

    EntitySpecialArrow entitySpecialArrow = new EntitySpecialArrow(worldIn, shooter);
    entitySpecialArrow.setPotionEffect(stack);
    return entitySpecialArrow;
  }

  private static ItemStack findAmmo(EntityPlayer player) {

    if (isArrow(player.getHeldItem(EnumHand.OFF_HAND))) {
      return player.getHeldItem(EnumHand.OFF_HAND);
    } else if (isArrow(player.getHeldItem(EnumHand.MAIN_HAND))) {
      return player.getHeldItem(EnumHand.MAIN_HAND);
    } else {
      for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
        ItemStack itemstack = player.inventory.getStackInSlot(i);

        if (isArrow(itemstack)) {
          return itemstack;
        }
      }

      return ItemStack.EMPTY;
    }
  }

  protected static boolean isArrow(ItemStack stack) {
    return stack.getItem() instanceof ItemArrow;
  }
}



