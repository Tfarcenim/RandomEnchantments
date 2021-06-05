package tfar.randomenchants.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import tfar.randomenchants.RandomEnchants;
import tfar.randomenchants.ench.enchantment.EnchantmentAutoSmelt;
import tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static tfar.randomenchants.RandomEnchants.ObjectHolders.GLOBAL_TRAVELER;
import static tfar.randomenchants.RandomEnchants.ObjectHolders.MAGNETIC;
import static tfar.randomenchants.ench.enchantment.EnchantmentGlobalTraveler.GLOBAL_TRAVELER_KEY;

public class MixinEvents {

    //@SubscribeEvent
    //public static void drops(DropLootEvent event) {
    /*PlayerEntity player = event.getPlayer();
    LootContext context = event.getContext();
    ItemStack tool = context.get(LootParameters.TOOL);
    BlockState state = context.get(LootParameters.BLOCK_STATE);
    BlockPos pos = context.get(LootParameters.POSITION);
    List<ItemStack> contents = event.getDrops();
    Block block = state.getBlock();
    if (player != null) {

      if (EnchantUtils.hasEnch(player, RandomEnchants.ObjectHolders.SILVERFISH)) {
        if (block == Blocks.STONE || block == Blocks.COBBLESTONE || block == Blocks.STONE_BRICKS){
          if (!EnchantUtils.hasEnch(tool, Enchantments.SILK_TOUCH))
            contents.removeIf(stack -> stack.getItem() == Items.COBBLESTONE ||stack.getItem() == Items.STONE || stack.getItem() == Items.STONE_BRICKS);
          World world = player.world;
          Entity pest = EntityType.SILVERFISH.create(world);
          pest.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
          world.addEntity(pest);
        }
      }

      if (EnchantUtils.hasEnch(player, RandomEnchants.ObjectHolders.AUTOSMELT)) {
        World world = player.world;
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


      if ((EnchantUtils.hasEnch(tool, RandomEnchants.ObjectHolders.Assimilation))) {
        EnchantmentAssimilation.repair(player, contents);
      }

      if (EnchantUtils.hasEnch(player.getHeldItemMainhand(), GLOBAL_TRAVELER)) {
        CompoundNBT global = tool.getOrCreateTag().getCompound(GLOBAL_TRAVELER_KEY);
        if (EnchantmentGlobalTraveler.getToggleState(global)) {
          Coord4D coord = Coord4D.fromNBT(global);
          BlockPos tePos = coord.pos();
          TileEntity te = coord.getBlockEntity(world0);
          if (!tePos.equals(pos) && te != null) {
            IItemHandler ih = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                    Direction.values()[global.getByte("facing")]).orElse(null);
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
    }*/
    // }
    //note, handles items one at a time
    public static void handleBlockSpawn(BlockState state, World world, BlockPos pos, @Nullable TileEntity tileEntityIn, @Nullable Entity entityIn, ItemStack tool, ItemStack droppedStack) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entityIn;
            if (EnchantUtils.hasEnch(tool,GLOBAL_TRAVELER)) {
                if (EnchantUtils.hasEnch(player.getHeldItemMainhand(), GLOBAL_TRAVELER)) {
                    CompoundNBT global = tool.getOrCreateTag().getCompound(GLOBAL_TRAVELER_KEY);
                    if (EnchantmentGlobalTraveler.getToggleState(global)) {
                        Coord4D coord = Coord4D.fromNBT(global);
                        BlockPos tePos = coord.pos();
                        TileEntity te = ((ServerWorld) world).getServer().getWorld(coord.registryKey)
                                .getTileEntity(new BlockPos(coord.xCoord,coord.yCoord,coord.zCoord));
                        if (!tePos.equals(pos) && te != null) {
                            IItemHandler ih = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                                    Direction.values()[global.getByte("facing")]).orElse(null);
                            ItemStack returned = droppedStack.copy();
                            int count = 0;
                            if (ih != null) {
                                for (int i = 0; i < ih.getSlots();i++) {
                                    ItemStack ret = ih.insertItem(i, returned, false);
                                    count += (returned.getCount() - ret.getCount());
                                    if (ret.isEmpty()) {
                                        break;
                                    }
                                    returned = ret;
                                }
                                droppedStack.shrink(count);
                            }
                        }
                    }
                }
            }

            if (EnchantUtils.hasEnch(tool, MAGNETIC)) {
                ((PlayerEntity) entityIn).addItemStackToInventory(droppedStack);
            }
        }
    }

    public static boolean modifyDrops(BlockState state, ServerWorld worldIn, BlockPos pos, TileEntity blockEntity, Entity entity, ItemStack tool, List<ItemStack> returnValue) {
        if (EnchantUtils.hasEnch(tool, RandomEnchants.ObjectHolders.AUTOSMELT)) {
            List<ItemStack> remove = new ArrayList<>();
            List<ItemStack> smelts = new ArrayList<>();
            for (ItemStack stack : returnValue) {
                ItemStack result = EnchantmentAutoSmelt.getResult(stack.copy(), worldIn);
                if (!result.isEmpty()) {
                    smelts.add(result);
                    remove.add(stack);
                }
            }
            returnValue.removeAll(remove);
            returnValue.addAll(smelts);
        }
        return false;
    }
}
