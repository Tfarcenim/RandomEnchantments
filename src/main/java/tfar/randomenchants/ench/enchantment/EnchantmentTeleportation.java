package tfar.randomenchants.ench.enchantment;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import tfar.randomenchants.Config;
import tfar.randomenchants.RandomEnchants;
import tfar.randomenchants.util.EnchantUtils;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
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

import static tfar.randomenchants.Config.Restriction.*;
import static tfar.randomenchants.RandomEnchants.ObjectHolders.TELEPORTATION;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)
public class EnchantmentTeleportation extends Enchantment {
    public EnchantmentTeleportation() {

        super(Rarity.RARE, RandomEnchants.SHOOTABLE, new EquipmentSlotType[]{
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
        if (!(shooter instanceof LivingEntity)) return;
        if (!arrow.getPersistentData().getBoolean(TELEPORTATION.getRegistryName().toString())) return;
        BlockPos pos = ((BlockRayTraceResult) e.getRayTraceResult()).getPos();
        if (arrow.world.getBlockState(pos.up()).getMaterial() == Material.LAVA)return;
        shooter.setPositionAndUpdate(pos.getX(),pos.getY()+1,pos.getZ());
    }

    @SubscribeEvent
    public static void looseArrow(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof AbstractArrowEntity) {
            AbstractArrowEntity arrow = (AbstractArrowEntity)e.getEntity();
            Entity owner = arrow.getShooter();
            if (owner instanceof LivingEntity && EnchantUtils.hasEnch((LivingEntity)owner, TELEPORTATION)) {
                arrow.getPersistentData().putBoolean(TELEPORTATION.getRegistryName().toString(),true);
            }
        }
    }
}

