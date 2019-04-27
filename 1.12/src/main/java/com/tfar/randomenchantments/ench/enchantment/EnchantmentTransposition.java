package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.EnchantmentConfig;
import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.init.ModEnchantment.TRANSPOSITION;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)

public class EnchantmentTransposition extends Enchantment {
    public EnchantmentTransposition() {

        super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND
        });
        this.setRegistryName("transposition");
        this.setName("transposition");
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
        return weapons.enableTransposition != DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return weapons.enableTransposition == ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return weapons.enableTransposition != DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return weapons.enableTransposition == NORMAL;
    }

    @SubscribeEvent
    public static void teleportArrow(ProjectileImpactEvent e) {
        if(!(e.getRayTraceResult().entityHit instanceof EntityLivingBase))return;
        if (!(e.getEntity() instanceof EntityArrow) || e.getEntity().world.isRemote) return;
        EntityArrow arrow = (EntityArrow) e.getEntity();
        Entity shooter = arrow.shootingEntity;
        if (!(shooter instanceof EntityLivingBase)) return;
        EntityLivingBase e1 = (EntityLivingBase) shooter;
        if (EnchantmentHelper.getMaxEnchantmentLevel(TRANSPOSITION, e1) == 0) return;
        Float[] pos1 = new Float[]{(float)e1.posX,(float)e1.posY,(float)e1.posZ,e1.rotationYaw,e1.cameraPitch};
        EntityLivingBase e2 = (EntityLivingBase) e.getRayTraceResult().entityHit;
        Float[] pos2 = new Float[]{(float)e2.posX,(float)e2.posY,(float)e2.posZ,e2.rotationYaw,e2.cameraPitch};
        e1.setPositionAndRotationDirect(pos2[0],pos2[1],pos2[2],pos2[3],pos2[4], 1, true);
        e2.setPositionAndRotationDirect(pos1[0],pos1[1],pos1[2],pos1[3],pos1[4],1,true);
    }
}
