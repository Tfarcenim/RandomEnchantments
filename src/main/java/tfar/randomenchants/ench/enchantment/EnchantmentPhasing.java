package tfar.randomenchants.ench.enchantment;

import tfar.randomenchants.Config;
import tfar.randomenchants.RandomEnchants;
import tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static tfar.randomenchants.RandomEnchants.ObjectHolders.PHASING;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)

public class EnchantmentPhasing extends Enchantment {
  public EnchantmentPhasing() {
    super(Rarity.RARE, EnchantmentType.BOW, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("phasing");
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
  public boolean canApply(ItemStack stack){
    return Config.ServerConfig.phasing.get() != Config.Restriction.DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.phasing.get() == Config.Restriction.ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.phasing.get() != Config.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.phasing.get() == Config.Restriction.NORMAL;
  }

  @SubscribeEvent
  public static void arrowSpawn(ProjectileImpactEvent event)  {
    Entity entity = event.getEntity();
    if (!(entity instanceof AbstractArrowEntity))return;
    Entity shooter = ((AbstractArrowEntity) entity).func_234616_v_();
    if (!(shooter instanceof PlayerEntity))return;
    PlayerEntity player = (PlayerEntity) shooter;
    if (!EnchantUtils.hasEnch(player,PHASING))return;
    if (entity.ticksExisted > 1200)entity.remove();
    if(event.getRayTraceResult() instanceof BlockRayTraceResult)event.setCanceled(true);
    }
  }


