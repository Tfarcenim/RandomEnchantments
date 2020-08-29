package tfar.randomenchants.util;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
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
}
