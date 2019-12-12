package com.tfar.randomenchants.util;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.ench.enchantment.EnchantmentAssimilation;
import com.tfar.randomenchants.ench.enchantment.EnchantmentAutoSmelt;
import com.tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.*;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.GLOBAL_TRAVELER;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.MAGNETIC;
import static com.tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler.KEY;

public class CoremodHooks {
  public static List<ItemStack> getdrops(LootTable table, LootContext context) {

    Entity entity = context.get(LootParameters.THIS_ENTITY);
    ItemStack tool = context.get(LootParameters.TOOL);
    BlockState state = context.get(LootParameters.BLOCK_STATE);
    BlockPos pos = context.get(LootParameters.POSITION);
    List<ItemStack> contents = table.generate(context);
    Block block = state.getBlock();
    if (entity instanceof LivingEntity) {

      if (EnchantUtils.hasEnch((LivingEntity)entity, RandomEnchants.ObjectHolders.SILVERFISH)) {
        if (block == Blocks.STONE || block == Blocks.COBBLESTONE || block == Blocks.STONE_BRICKS){
          if (!EnchantUtils.hasEnch(tool, Enchantments.SILK_TOUCH))
          contents.removeIf(stack -> stack.getItem() == Items.COBBLESTONE ||stack.getItem() == Items.STONE || stack.getItem() == Items.STONE_BRICKS);
          World world = entity.world;
          Entity pest = EntityType.SILVERFISH.create(world);
          pest.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
          world.addEntity(pest);
        }
      }

      if (EnchantUtils.hasEnch((LivingEntity) entity, RandomEnchants.ObjectHolders.AUTOSMELT)) {
        World world = entity.world;
        List<ItemStack> remove = new ArrayList<>();
        List<ItemStack> smelts = new ArrayList<>();
        for (ItemStack stack : contents) {
          ItemStack result = EnchantmentAutoSmelt.getResult(stack.copy(), world);
          if (!result.isEmpty()) {
            smelts.add(result);
            remove.add(stack);
          }
        }
        contents.removeAll(remove);
        contents.addAll(smelts);
      }


      if (entity instanceof PlayerEntity) {
        PlayerEntity player = (PlayerEntity) entity;

        if ((EnchantUtils.hasEnch(tool, RandomEnchants.ObjectHolders.Assimilation))) {
          EnchantmentAssimilation.repair(player, contents);
        }

        if (EnchantUtils.hasEnch(player.getHeldItemMainhand(), GLOBAL_TRAVELER)) {
          if (EnchantmentGlobalTraveler.getToggleState(tool)) {

            CompoundNBT nbt0 = tool.getOrCreateTag();

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
        if (EnchantUtils.hasEnch(player.getHeldItemMainhand(), MAGNETIC))
          contents.removeIf(player::addItemStackToInventory);
      }
    }

    return contents;

  }
}
