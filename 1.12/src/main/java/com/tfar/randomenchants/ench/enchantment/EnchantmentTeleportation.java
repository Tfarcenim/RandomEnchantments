package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.init.ModEnchantment.TELEPORTATON;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)
public class EnchantmentTeleportation extends Enchantment {
    public EnchantmentTeleportation() {

        super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("teleportation");
        this.setName("teleportation");
    }

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
        if(e.getRayTraceResult().entityHit != null)return;
        if (!(e.getEntity() instanceof EntityArrow) || e.getEntity().world.isRemote) return;
        BlockPos pos = e.getRayTraceResult().getBlockPos();
        BlockPos pos1 = new BlockPos(pos.getX(),pos.getY()+1,pos.getZ());
        EntityArrow arrow = (EntityArrow) e.getEntity();
        if (arrow.world.getBlockState(pos1).getMaterial() == Material.LAVA)return;
        Entity shooter = arrow.shootingEntity;
        if (!(shooter instanceof EntityPlayer)) return;
        EntityPlayer p = (EntityPlayer) shooter;
        if (EnchantmentHelper.getMaxEnchantmentLevel(TELEPORTATON, p) == 0) return;
        p.setPositionAndUpdate(pos1.getX(),pos1.getY(),pos1.getZ());
    }
}

