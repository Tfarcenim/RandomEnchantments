package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.Coord4D;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.*;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.GLOBAL_TRAVELER;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)
public class EnchantmentGlobalTraveler extends Enchantment {
  //works like plustic version
  public EnchantmentGlobalTraveler() {

    super(Rarity.VERY_RARE, RandomEnchants.TOOLSANDWEAPONS, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("global_traveler");
  }

  public static String GLOBAL_TRAVELER_KEY = "randomenchants:coords";

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
    return Config.ServerConfig.global_traveller.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.global_traveller.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.global_traveller.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.global_traveller.get() == NORMAL;
  }

    /*@SubscribeEvent(priority = EventPriority.LOWEST)
    public void blockDrops(BlockEvent.HarvestDropsEvent event) {
      if (event.getWorld().isRemote()
              || event.getHarvester() == null) return;
      ItemStack tool = getItemstackToUse(event.getHarvester(), event.getState());
      if (!EnchantUtils.hasEnch(tool, GLOBAL_TRAVELER)) return;
      __blockHarvestDrops(tool, event);
    }

    private void __blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event) {
      if (!getToggleState(tool)) return;
      CompoundNBT nbt0 = tool.getOrCreateTag();
      if (tool.canHarvestBlock(event.getState())) {

        CompoundNBT nbt = nbt0.getCompound(GLOBAL_TRAVELER_KEY);
        Coord4D coord = Coord4D.fromNBT(nbt);
        if (coord.pos().equals(event.getPos())) {
          return; // prevent self-linking
        }
        TileEntity te = coord.TE();
        if (te == null) return;
        IItemHandler ih = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                Direction.values()[nbt.getByte("facing")]).orElse(null);
        if (ih == null) return;

        ListIterator<ItemStack> it = event.getDrops().listIterator();
        ItemStack keptSeed = ItemStack.EMPTY;
        while (it.hasNext()) {
          it.next();
          if (event.getWorld().getRandom().nextFloat() > event.getDropChance()) {
            it.remove();
          } else {
            tool.getItem();
          }
        }

        tool.getItem();
        it = event.getDrops().listIterator();
        while (it.hasNext()) {
          ItemStack stk = it.next();
          for (int j=0; j<ih.getSlots(); ++j) {
            ItemStack res = ih.insertItem(j, stk, false);
            if (!res.isEmpty()) {
              it.set(res);
              stk = res;
            } else {
              it.remove();
              break;
            }
          }
        }
        if (!keptSeed.isEmpty()) event.getDrops().add(keptSeed);

        event.setDropChance(1); // already accounted for in this function

        te.markDirty();
      }
    }*/

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void dropEvent(LivingDropsEvent event) {
    World world0 = event.getEntity().getEntityWorld();
    if (world0.isRemote
            || event.getEntityLiving().getHealth() > 0) return;
    ItemStack weapon = getWeapon(event.getSource());
    if (EnchantUtils.hasEnch(weapon, GLOBAL_TRAVELER)) {
      CompoundNBT globalTravellerNBT = weapon.getTag().getCompound(GLOBAL_TRAVELER_KEY);
      if (!getToggleState(globalTravellerNBT)) return;
      if (globalTravellerNBT.contains(GLOBAL_TRAVELER_KEY)) {
        CompoundNBT nbt = globalTravellerNBT.getCompound(GLOBAL_TRAVELER_KEY);
        Coord4D coord = Coord4D.fromNBT(nbt);
        TileEntity te = coord.TE();
        if (te == null) return;
        IItemHandler ih = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                Direction.values()[nbt.getByte("facing")]).orElse(null);
        if (ih == null) return;
        Iterator<ItemEntity> it = event.getDrops().iterator();
        while (it.hasNext()) {
          ItemEntity enti = it.next();
          ItemStack stk = enti.getItem();
          for (int j = 0; j < ih.getSlots(); ++j) {
            ItemStack res = ih.insertItem(j, stk, false);
            if (!res.isEmpty()) {
              enti.setItem(res);
              stk = res;
            } else {
              it.remove();
              break;
            }
          }
        }
        te.markDirty();
      }
    }
  }

  @SubscribeEvent
  public static void onPlayerUse(PlayerInteractEvent.RightClickBlock event) {
    if (event.getWorld().isRemote
            || !event.getPlayer().isCrouching()
            || event.getFace() == null
            || !EnchantUtils.hasEnch(event.getItemStack(), GLOBAL_TRAVELER)
            || event.getHand() == Hand.OFF_HAND
    )
      return;
    TileEntity te = event.getWorld().getTileEntity(event.getPos());
    if (te == null || !te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
            event.getFace()).isPresent()) return;
    CompoundNBT nbt = event.getItemStack().getTag();
    CompoundNBT global = nbt.getCompound(GLOBAL_TRAVELER_KEY);
    Coord4D coord = new Coord4D(event.getPos(), event.getWorld());
    coord.toNBT(global);
    global.putByte("facing", (byte) event.getFace().ordinal());
    nbt.put(GLOBAL_TRAVELER_KEY, global);
    event.getItemStack().setTag(nbt);
    event.getPlayer().sendMessage(new TranslationTextComponent(
            "tooltip.globalmodifier.info",
            coord.xCoord,
            coord.yCoord,
            coord.zCoord,
            coord.dimensionId));
  }

  private ItemStack getWeapon(DamageSource source) {
    if (source instanceof EntityDamageSource) {
      Entity entity = source.getTrueSource();
      if (entity instanceof LivingEntity)
        return ((LivingEntity) entity).getHeldItemMainhand();
    }
    return ItemStack.EMPTY;
  }

  public static boolean getToggleState(CompoundNBT nbt) {
    return !nbt.isEmpty() && nbt.getBoolean("toggle");
  }

  public static ItemStack getItemstackToUse(LivingEntity player, BlockState blockState) {
    ItemStack mainhand = player.getHeldItemMainhand();
    if (mainhand.isEmpty() && shouldUseOffhand(player, blockState, mainhand)) {
      return player.getHeldItemOffhand();
    }
    return mainhand;
  }

  public static boolean shouldUseOffhand(LivingEntity player, BlockState blockState, ItemStack tool) {
    ItemStack offhand = player.getHeldItemOffhand();

    return !tool.isEmpty()
            && !offhand.isEmpty()
            && !tool.canHarvestBlock(blockState)
            && offhand.canHarvestBlock(blockState);
  }
}

