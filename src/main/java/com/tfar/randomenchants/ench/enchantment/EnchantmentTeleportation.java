package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.TELEPORTATON;

@Mod.EventBusSubscriber(modid = RandomEnchants.MOD_ID)
public class EnchantmentTeleportation extends Enchantment {
    public EnchantmentTeleportation() {

        super(Rarity.RARE, EnchantmentType.BOW, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("teleportation");
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
        return weapons.enableTeleportation != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return weapons.enableTeleportation != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return weapons.enableTeleportation == NORMAL;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return weapons.enableTeleportation == ANVIL;
    }

    @SubscribeEvent
    public static void teleportArrow(ProjectileImpactEvent e) {
        if(!(e.getRayTraceResult() instanceof BlockRayTraceResult))return;
        if (!(e.getEntity() instanceof AbstractArrowEntity) || e.getEntity().world.isRemote) return;
        BlockPos pos = ((BlockRayTraceResult) e.getRayTraceResult()).getPos();
        BlockPos pos1 = new BlockPos(pos.getX(),pos.getY()+1,pos.getZ());
        AbstractArrowEntity arrow = (AbstractArrowEntity) e.getEntity();
        if (arrow.world.getBlockState(pos1).getMaterial() == Material.LAVA)return;
        Entity shooter = arrow.getShooter();
        if (!(shooter instanceof PlayerEntity)) return;
        PlayerEntity p = (PlayerEntity) shooter;
        if (EnchantmentHelper.getMaxEnchantmentLevel(TELEPORTATON, p) == 0) return;
        p.setPositionAndUpdate(pos1.getX(),pos1.getY(),pos1.getZ());
    }
}

