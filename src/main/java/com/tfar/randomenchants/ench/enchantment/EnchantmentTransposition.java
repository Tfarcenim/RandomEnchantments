package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.weapons;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.TRANSPOSITION;

@Mod.EventBusSubscriber(modid = RandomEnchants.MOD_ID)

public class EnchantmentTransposition extends Enchantment {
    public EnchantmentTransposition() {

        super(Rarity.RARE, EnchantmentType.BOW, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("transposition");
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
        if(!(e.getRayTraceResult() instanceof EntityRayTraceResult) || !(((EntityRayTraceResult) e.getRayTraceResult()).getEntity() instanceof LivingEntity))return;
        if (!(e.getEntity() instanceof AbstractArrowEntity) || e.getEntity().world.isRemote) return;
        AbstractArrowEntity arrow = (AbstractArrowEntity) e.getEntity();
        Entity shooter = arrow.getShooter();
        if (!(shooter instanceof LivingEntity)) return;
        LivingEntity e1 = (LivingEntity) shooter;
        if (EnchantmentHelper.getMaxEnchantmentLevel(TRANSPOSITION, e1) == 0) return;
        float[] pos1 = new float[]{(float)e1.posX,(float)e1.posY,(float)e1.posZ,e1.rotationYaw,e1.rotationPitch
        };
        LivingEntity e2 = (LivingEntity) ((EntityRayTraceResult) e.getRayTraceResult()).getEntity();
        float[] pos2 = new float[]{(float)e2.posX,(float)e2.posY,(float)e2.posZ,e2.rotationYaw,e2.rotationPitch};
        e1.setPositionAndRotationDirect(pos2[0],pos2[1],pos2[2],pos2[3],pos2[4], 1, true);
        e2.setPositionAndRotationDirect(pos1[0],pos1[1],pos1[2],pos1[3],pos1[4],1,true);
    }
}
