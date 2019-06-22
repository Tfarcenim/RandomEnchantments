package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.Coord4D;
import com.tfar.randomenchants.util.EnchantmentUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.Collections;
import java.util.ListIterator;
import java.util.Set;
import java.util.WeakHashMap;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.tools;
import static com.tfar.randomenchants.init.ModEnchantment.GLOBAL_TRAVELLER;
import static com.tfar.randomenchants.util.EnchantmentUtils.getTagSafe;

public class EnchantmentGlobalTraveler extends Enchantment {
  public EnchantmentGlobalTraveler() {

    super(Rarity.VERY_RARE, RandomEnchants.TOOLSANDWEAPONS, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("global_traveler");
    this.setName("global_traveler");
  }

  public static String KEY;

  @Override
  public int getMinEnchantability(int level) {
    return 15;
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
    return tools.enableGlobalTraveler != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return tools.enableGlobalTraveler == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return tools.enableGlobalTraveler != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return tools.enableGlobalTraveler == NORMAL;
  }

    private static final Set<Block> warnedBlocks = Collections.newSetFromMap(new WeakHashMap<>());

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void blockDrops(BlockEvent.HarvestDropsEvent event) {
      if (event.getWorld().isRemote
              || event.getHarvester() == null) return;
      ItemStack tool = getItemstackToUse(event.getHarvester(), event.getState());
      if (!EnchantmentUtils.stackHasEnch(tool,GLOBAL_TRAVELLER)) return;
      __blockHarvestDrops(tool, event);
    }

    private void __blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event) {
      if (!getToggleState(tool)) return;
      NBTTagCompound nbt0 = getTagSafe(tool);
      if (EnchantmentUtils.stackHasEnch(tool,GLOBAL_TRAVELLER) && tool.canHarvestBlock(event.getState())) {

        NBTTagCompound nbt = nbt0.getCompoundTag(KEY);
        Coord4D coord = Coord4D.fromNBT(nbt);
        if (coord.pos().equals(event.getPos())) {
          return; // prevent self-linking
        }
        TileEntity te = coord.TE();
        if (te == null) return;
        IItemHandler ih = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                EnumFacing.VALUES[nbt.getByte("facing")]);
        if (ih == null) return;

        // *cough* Extra Utilities *cough*
        try {
          ListIterator<ItemStack> dummy = event.getDrops().listIterator();

          if (dummy.hasNext()) {
            ItemStack is = dummy.next();
            dummy.set(is); // This simply sets the 1st element of the list to itself, leaving the list unchanged. If the list is immutable, then this will throw an UnsupportedOperationException.
          }
        } catch (UnsupportedOperationException e) {
          if (!warnedBlocks.contains(event.getState().getBlock())) {
            FMLLog.bigWarning("Block " + event.getState().getBlock() + " implements block drops incorrectly. "
                    + "It appears that it overrides the OFFICIALLY DEPRECATED method "
                    + "getDrops(IBlockAccess, BlockPos, IBlockState, int) instead of the correct method "
                    + "getDrops(NonNullList, IBlockAccess, BlockPos, IBlockState, int). This prevents "
                    + "features such as Random Enchants's Global Traveler from working properly with these blocks.\n"
                    + "USERS: This is a BUG in the mod " + event.getState().getBlock().getRegistryName().getNamespace() + "; report this to them!");
            warnedBlocks.add(event.getState().getBlock());
          }

          return;
        }

        ListIterator<ItemStack> it = event.getDrops().listIterator();
        ItemStack keptSeed = ItemStack.EMPTY;
        while (it.hasNext()) {
          it.next();
          if (event.getWorld().rand.nextFloat() > event.getDropChance()) {
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
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void dropEvent(LivingDropsEvent event) {
      World world0 = event.getEntity().getEntityWorld();
      if (world0.isRemote
              || event.getEntityLiving().getHealth() > 0) return;
      ItemStack weapon = getWeapon(event.getSource());
      NBTTagCompound nbt0 = getTagSafe(weapon);
      if (EnchantmentUtils.stackHasEnch(weapon,GLOBAL_TRAVELLER)) {
        if (!getToggleState(weapon)) return;
        if (nbt0.hasKey(KEY)) {
          NBTTagCompound nbt = nbt0.getCompoundTag(KEY);
          Coord4D coord = Coord4D.fromNBT(nbt);
          TileEntity te = coord.TE();
          if (te == null) return;
          IItemHandler ih = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                  EnumFacing.VALUES[nbt.getByte("facing")]);
          if (ih == null) return;
          ListIterator<EntityItem> it = event.getDrops().listIterator();
          while (it.hasNext()) {
            EntityItem enti = it.next();
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
    public void onPlayerUse(PlayerInteractEvent.RightClickBlock event) {
      NBTTagCompound nbt = getTagSafe(event.getItemStack());
      if (event.getWorld().isRemote
              || event.isCanceled()
              || !event.getEntityPlayer().isSneaking()
              || event.getItemStack().isEmpty()
              || event.getFace() == null
              || !EnchantmentUtils.stackHasEnch(event.getItemStack(), GLOBAL_TRAVELLER))
        return;
      TileEntity te = event.getWorld().getTileEntity(event.getPos());
      if (te == null || te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
              event.getFace()) == null) return;
      NBTTagCompound global = new NBTTagCompound();
      Coord4D coord = new Coord4D(event.getPos(), event.getWorld());
      coord.toNBT(global);
      global.setByte("facing", (byte) event.getFace().ordinal());
      nbt.setTag(KEY, global);
      event.getItemStack().setTagCompound(nbt);
      event.getEntityPlayer().sendMessage(new TextComponentTranslation(
              "tooltip.globalmodifier.info",
              coord.xCoord,
              coord.yCoord,
              coord.zCoord,
              EnchantmentUtils.getWorldNameFromid(coord.dimensionId)));
    }

    private ItemStack getWeapon(DamageSource source) {
      if (source instanceof EntityDamageSource) {
        Entity entity = source.getTrueSource();
        if (entity instanceof EntityLivingBase)
          return ((EntityLivingBase) entity).getHeldItemMainhand();
      }
      return ItemStack.EMPTY;
    }

    public static boolean getToggleState(ItemStack stack){
    return EnchantmentUtils.stackHasEnch(stack,GLOBAL_TRAVELLER) && getTagSafe(stack).getBoolean("toggle");
    }

  public static ItemStack getItemstackToUse(EntityLivingBase player, IBlockState blockState) {
    ItemStack mainhand = player.getHeldItemMainhand();
    if(mainhand.isEmpty() && shouldUseOffhand(player, blockState, mainhand)) {
      return player.getHeldItemOffhand();
    }
    return mainhand;
  }

  public static boolean shouldUseOffhand(EntityLivingBase player, IBlockState blockState, ItemStack tool) {
    ItemStack offhand = player.getHeldItemOffhand();

    return !tool.isEmpty()
            && !offhand.isEmpty()
            && !tool.canHarvestBlock(blockState)
            && offhand.canHarvestBlock(blockState);
  }
}

