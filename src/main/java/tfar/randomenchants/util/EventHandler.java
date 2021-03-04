package tfar.randomenchants.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tfar.randomenchants.RandomEnchants;
import tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler;

import static tfar.randomenchants.RandomEnchants.ObjectHolders.GLOBAL_TRAVELER;

@Mod.EventBusSubscriber
public class EventHandler {

  @SubscribeEvent
  public static void toggle(PlayerInteractEvent.RightClickBlock e) {
    World world = e.getWorld();
    if (world.isRemote) return;
    BlockPos pos = e.getPos();
    TileEntity tile = world.getTileEntity(pos);
     if (tile != null)return;

    ItemStack stack = e.getItemStack();
    if (EnchantUtils.hasEnch(stack, GLOBAL_TRAVELER) && e.getPlayer().isCrouching()) {
      toggle(stack);
    }
  }

  @SubscribeEvent
  public static void hit(LivingDamageEvent event) {
    LivingEntity victim = event.getEntityLiving();
    Entity user = event.getSource().getTrueSource();
    if (user instanceof PlayerEntity && EnchantUtils.hasEnch((LivingEntity) user, RandomEnchants.ObjectHolders.INSTANT_DEATH)) {
      victim.setHealth(0);
    }
  }

  public static void toggle(ItemStack stack) {
    CompoundNBT global = stack.getTag().getCompound(EnchantmentGlobalTraveler.GLOBAL_TRAVELER_KEY);
    if (!global.isEmpty()) {
      boolean toggle = global.getBoolean("toggle");
      global.putBoolean("toggle", !toggle);
    }
  }
}