package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.init.ModEnchantment.RANDOMNESS;
import static com.tfar.randomenchantments.util.GlobalVars.itemList;
import static com.tfar.randomenchantments.util.GlobalVars.size;

@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentRandomness extends Enchantment {
    public EnchantmentRandomness() {

        super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("randomness");
        this.setName("randomness");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 5 + 10 * (level - 1);
    }

    @Override
    public int getMaxEnchantability(int level) {
        return super.getMinEnchantability(level) + 25;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }


    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent e) {
        EntityPlayer p = e.getPlayer();
        World world = p.getEntityWorld();
        BlockPos pos = e.getPos();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(RANDOMNESS, p);
        if (level > 0 && !world.isRemote) {
            for (int i=0; i < level;i++) {
                int rand = (int)Math.floor(Math.random()*size);
                Item item = itemList.get(rand);
                EntityItem itemStack = new EntityItem(p.world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(item));
                world.spawnEntity(itemStack);
            }
        }
    }
}
