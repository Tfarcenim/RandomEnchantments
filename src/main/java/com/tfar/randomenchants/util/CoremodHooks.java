package com.tfar.randomenchants.util;

import com.tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.*;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.GLOBAL_TRAVELER;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.MAGNETIC;
import static com.tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler.KEY;

public class CoremodHooks {
  public static List<ItemStack> drops(BlockState state, LootContext.Builder context) {
    ResourceLocation resourcelocation = state.getBlock().getLootTable();

    if (resourcelocation == LootTables.EMPTY) {
      return Collections.emptyList();
    } else {
      LootContext lootcontext = context.withParameter(LootParameters.BLOCK_STATE, state).build(LootParameterSets.BLOCK);
      ServerWorld serverworld = lootcontext.getWorld();
      Entity entity = lootcontext.get(LootParameters.THIS_ENTITY);
      ItemStack tool = lootcontext.get(LootParameters.TOOL);
      LootTable loottable = serverworld.getServer().getLootTableManager().getLootTableFromLocation(resourcelocation);

      List<ItemStack> contents = loottable.generate(lootcontext);

      if (entity instanceof PlayerEntity){
        PlayerEntity player = (PlayerEntity)entity;

        if (EnchantUtils.hasEnch(player.getHeldItemMainhand(), GLOBAL_TRAVELER)) {
          if (EnchantmentGlobalTraveler.getToggleState(tool)) {
            BlockPos pos = lootcontext.get(LootParameters.POSITION);

            CompoundNBT nbt0 = tool.getOrCreateTag();
            if (tool.canHarvestBlock(state)) {

              CompoundNBT nbt = nbt0.getCompound(KEY);
              Coord4D coord = Coord4D.fromNBT(nbt);

              BlockPos tePos = coord.pos();
              TileEntity te = coord.TE();
              if (!tePos.equals(pos) && te != null) {
                IItemHandler ih = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                        Direction.values()[nbt.getByte("facing")]).orElse(null);
                if (ih != null) {
                  ListIterator<ItemStack> it = contents.listIterator();
                  ItemStack keptSeed = ItemStack.EMPTY;
                  while (it.hasNext()) {
                    it.next();
                    tool.getItem();
                  }

                  tool.getItem();
                  it = contents.listIterator();
                  while (it.hasNext()) {
                    ItemStack stk = it.next();
                    for (int j = 0; j < ih.getSlots(); ++j) {
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
                  if (!keptSeed.isEmpty()) contents.add(keptSeed);

                  te.markDirty();
                }
              }
            }
          }
        }
        if (EnchantUtils.hasEnch(player.getHeldItemMainhand(),MAGNETIC))
          contents.removeIf(player::addItemStackToInventory);
      }

      return contents;

    }
  }

  public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
    ResourceLocation resourcelocation = state.getBlock().getLootTable();
    if (resourcelocation == LootTables.EMPTY) {
      return Collections.emptyList();
    } else {
      LootContext lootcontext = builder.withParameter(LootParameters.BLOCK_STATE, state).build(LootParameterSets.BLOCK);
      ServerWorld serverworld = lootcontext.getWorld();
      LootTable loottable = serverworld.getServer().getLootTableManager().getLootTableFromLocation(resourcelocation);
      return loottable.generate(lootcontext);
    }
  }

  public List<ItemStack> getDrops2(BlockState state, LootContext.Builder builder) {
    return drops(state, builder);
  }
}
