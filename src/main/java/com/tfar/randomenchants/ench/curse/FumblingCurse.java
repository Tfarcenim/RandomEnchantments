package com.tfar.randomenchants.ench.curse;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.Config.Restriction.ANVIL;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.FUMBLING;

@Mod.EventBusSubscriber(modid= RandomEnchants.MODID)
public class FumblingCurse extends Enchantment {
  public FumblingCurse() {

    super(Rarity.RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("fumbling");
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
  public boolean isCurse()
  {
    return true;
  }
  @Override
  public boolean canApply(ItemStack stack)
  {
    return Config.ServerConfig.fumbling.get() != Config.Restriction.DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.fumbling.get() == ANVIL;
  }

  @SubscribeEvent
  public static void onBreakSpeed(PlayerEvent.BreakSpeed e) {
    PlayerEntity p = e.getEntityPlayer();
    if (EnchantUtils.hasEnch(p, FUMBLING)) {
      float oldSpeed = e.getOriginalSpeed();
      e.setNewSpeed((float)Math.sqrt(oldSpeed));
    }
  }
}

