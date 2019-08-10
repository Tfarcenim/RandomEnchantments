package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
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

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.TELEPORTATON;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)
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
        return Config.ServerConfig.teleportation.get() != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return Config.ServerConfig.teleportation.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.ServerConfig.teleportation.get() == NORMAL;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return Config.ServerConfig.teleportation.get() == ANVIL;
    }

    @SubscribeEvent
    public static void teleportArrow(ProjectileImpactEvent e) {
        if(!(e.getRayTraceResult() instanceof BlockRayTraceResult))return;
        if (!(e.getEntity() instanceof AbstractArrowEntity) || e.getEntity().world.isRemote) return;
        AbstractArrowEntity arrow = (AbstractArrowEntity) e.getEntity();
        Entity shooter = arrow.getShooter();
        if (!(shooter instanceof PlayerEntity)) return;
        PlayerEntity p = (PlayerEntity) shooter;
        if (!EnchantUtils.hasEnch(p,TELEPORTATON)) return;
        BlockPos pos = ((BlockRayTraceResult) e.getRayTraceResult()).getPos().down();
        if (arrow.world.getBlockState(pos).getMaterial() == Material.LAVA)return;
        p.setPositionAndUpdate(pos.getX(),pos.getY(),pos.getZ());

    }
}

