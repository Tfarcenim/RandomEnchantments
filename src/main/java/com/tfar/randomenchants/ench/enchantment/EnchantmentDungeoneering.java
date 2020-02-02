package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.RANDOMNESS;
import static com.tfar.randomenchants.RandomEnchants.itemList;


@Mod.EventBusSubscriber(modid= RandomEnchants.MODID)
public class EnchantmentDungeoneering extends Enchantment {
    public EnchantmentDungeoneering() {

        super(Rarity.RARE, EnchantmentType.DIGGER, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("randomness");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 5 + 10 * (level - 1);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canApply(ItemStack stack){
        return Config.ServerConfig.randomness.get() != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return Config.ServerConfig.randomness.get() == ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return Config.ServerConfig.randomness.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.ServerConfig.randomness.get() == NORMAL;
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent e) {
        PlayerEntity p = e.getPlayer();
        World world = p.getEntityWorld();
        BlockPos pos = e.getPos();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(RANDOMNESS, p);
        if (level > 0 && !world.isRemote) {
            for (int i=0; i < level;i++) {
                int rand = (int)Math.floor(Math.random()*itemList.size());
                Item item = itemList.get(rand);
                ItemEntity itemStack = new ItemEntity(p.world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(item));
                world.addEntity(itemStack);
            }
        }
    }
    //doesn't work
    @SubscribeEvent
    public static void removeDefaultDrops(BlockEvent.HarvestDropsEvent e){
        PlayerEntity p = e.getHarvester();
        if (p == null)return;
        World world = p.getEntityWorld();
        if (EnchantUtils.hasEnch(p.getHeldItemMainhand(),RANDOMNESS) && !world.isRemote) {
            e.getDrops().clear();
        }
    }
}
