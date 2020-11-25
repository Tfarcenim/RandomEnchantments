package tfar.randomenchants.ench.enchantment;

import net.minecraft.entity.player.PlayerEntity;
import tfar.randomenchants.Config;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import static tfar.randomenchants.RandomEnchants.SWORDS_BOWS;

public class EnchantmentInstantDeath extends Enchantment {
  public EnchantmentInstantDeath() {

    super(Rarity.RARE, SWORDS_BOWS, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("instant_death");
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
  public boolean canApply(ItemStack stack){
    return Config.ServerConfig.instant_death.get() != Config.Restriction.DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.instant_death.get() == Config.Restriction.ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.instant_death.get() != Config.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.instant_death.get() == Config.Restriction.NORMAL;
  }

  @Override
  public void onEntityDamaged(LivingEntity user, Entity target, int level) {
    if (target instanceof LivingEntity && user instanceof PlayerEntity) {
      LivingEntity victim = (LivingEntity) target;
      victim.setHealth(0);
    }
  }
}
