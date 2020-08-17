package tfar.randomenchants.ench.enchantment;

import tfar.randomenchants.Config;
import tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static tfar.randomenchants.RandomEnchants.MODID;
import static tfar.randomenchants.RandomEnchants.ObjectHolders.TRUE_LIFESTEAL;
import static tfar.randomenchants.RandomEnchants.SWORDS_BOWS;

@Mod.EventBusSubscriber(modid= MODID)
public class EnchantmentTrueLifesteal extends Enchantment {
  public EnchantmentTrueLifesteal() {

    super(Rarity.RARE, SWORDS_BOWS, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("true_lifesteal");
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
    return Config.ServerConfig.true_lifesteal.get() != Config.Restriction.DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.true_lifesteal.get() == Config.Restriction.ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.true_lifesteal.get() != Config.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.true_lifesteal.get() == Config.Restriction.NORMAL;
  }

  @SubscribeEvent
  public static void onAttack(LivingHurtEvent event) {
    if (event.getSource().getTrueSource() instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
      if (EnchantUtils.hasEnch(player, TRUE_LIFESTEAL)) {
        LivingEntity entity = event.getEntityLiving();
        float damage = event.getAmount();
        entity.attackEntityFrom(DamageSource.GENERIC, damage*1.5f);
        player.heal(damage);
      }
    }
  }
}
