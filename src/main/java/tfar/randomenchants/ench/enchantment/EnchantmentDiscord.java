package tfar.randomenchants.ench.enchantment;

import tfar.randomenchants.Config;
import tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class EnchantmentDiscord extends Enchantment {
  public EnchantmentDiscord() {

    super(Rarity.RARE, RandomEnchants.SWORDS_BOWS, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("discord");
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
    return Config.ServerConfig.discord.get() != Config.Restriction.DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.discord.get() == Config.Restriction.ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.discord.get() != Config.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.discord.get() == Config.Restriction.NORMAL;
  }

  private static boolean handled = false;

  @Override
  public void onEntityDamaged(LivingEntity user, Entity target, int level) {
    if (handled){
      handled = false;
      return;
    }
    if (!(target instanceof LivingEntity)) return;
    int r = 64;
    double x = target.getPosX();
    double y = target.getPosY();
    double z = target.getPosZ();
    List<Entity> aggro = target.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(x - r, y - r, z - r, x + r, y + r, z + r));
    for (Entity triggered : aggro) {
      ((LivingEntity) triggered).setRevengeTarget((LivingEntity) target);
    }
    handled = true;
  }
}



