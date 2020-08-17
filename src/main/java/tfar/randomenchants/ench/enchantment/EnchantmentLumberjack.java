package tfar.randomenchants.ench.enchantment;

import tfar.randomenchants.Config;
import tfar.randomenchants.RandomEnchants;
import tfar.randomenchants.util.EnchantUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static tfar.randomenchants.RandomEnchants.AXE;
import static tfar.randomenchants.RandomEnchants.ObjectHolders.LUMBERJACK;


@Mod.EventBusSubscriber(modid= RandomEnchants.MODID)
public class EnchantmentLumberjack extends Enchantment {
    public EnchantmentLumberjack() {

        super(Rarity.RARE, AXE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("lumberjack");
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
        return Config.ServerConfig.lumberjack.get() != Config.Restriction.DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return Config.ServerConfig.lumberjack.get() == Config.Restriction.ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return Config.ServerConfig.lumberjack.get() != Config.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.ServerConfig.lumberjack.get() == Config.Restriction.NORMAL;
    }

    @SubscribeEvent
    public static void onWoodBreak(BlockEvent.BreakEvent e){
        PlayerEntity p = e.getPlayer();
        if(!EnchantUtils.hasEnch(p,LUMBERJACK))return;
        ItemStack stack = p.getHeldItemMainhand();
        BlockState state = e.getState();
        BlockPos pos = e.getPos();
        Block block = state.getBlock();
        if (!block.isIn(BlockTags.LOGS))return;
        while(block.isIn(BlockTags.LOGS)){
            pos = pos.up();
            state = p.world.getBlockState(pos);
            block = state.getBlock();
            if (block.isIn(BlockTags.LOGS)){
                stack.damageItem(1,p, playerEntity -> playerEntity.sendBreakAnimation(p.getActiveHand()));
                p.world.destroyBlock(pos,true);
            }
        }
    }
}

