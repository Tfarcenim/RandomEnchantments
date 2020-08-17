package tfar.randomenchants.ench.enchantment;

import tfar.randomenchants.Config;
import tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import static tfar.randomenchants.RandomEnchants.SWORDS_BOWS;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)
public class EnchantmentDisarm extends Enchantment {
  public EnchantmentDisarm() {

    super(Rarity.RARE, SWORDS_BOWS, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("disarm");
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
    return Config.ServerConfig.disarm.get() != Config.Restriction.DISABLED && super.canApply(stack);
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.disarm.get() != Config.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.disarm.get() == Config.Restriction.NORMAL;
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.disarm.get() == Config.Restriction.ANVIL;
  }

  @Override
  public void onEntityDamaged(LivingEntity user, Entity target, int level) {
    if (target instanceof LivingEntity) {
      World world = user.world;
      BlockPos pos = user.getPosition();
      LivingEntity victim = (LivingEntity) target;
      ItemStack stack = victim.getHeldItemMainhand();
      victim.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
      ItemEntity itemStack = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
      world.addEntity(itemStack);
    }
  }
}

