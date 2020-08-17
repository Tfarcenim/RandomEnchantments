package tfar.randomenchants.ench.enchantment;

import tfar.randomenchants.Config;
import tfar.randomenchants.RandomEnchants;
import tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)

public class EnchantmentEternal extends Enchantment {
  public EnchantmentEternal() {
    super(Rarity.VERY_RARE, EnchantmentType.BREAKABLE, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("eternal");
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
    return Config.ServerConfig.eternal.get() != Config.Restriction.DISABLED;
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.eternal.get() == Config.Restriction.ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.eternal.get() != Config.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.eternal.get() == Config.Restriction.NORMAL;
  }

  @SubscribeEvent
  public static void itemDespawn(ItemExpireEvent event) {
    ItemEntity entityItem = event.getEntityItem();
    ItemStack stack = entityItem.getItem();
    if (EnchantUtils.hasEnch(stack, RandomEnchants.ObjectHolders.ETERNAL)) {
      event.setExtraLife(Integer.MAX_VALUE);
      event.setCanceled(true);
    }
  }
}

