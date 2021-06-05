package tfar.randomenchants.ench.enchantment;

import tfar.randomenchants.Config;
import tfar.randomenchants.RandomEnchants;
import tfar.randomenchants.util.EnchantUtils;
import net.minecraft.block.Block;
import net.minecraft.block.GlassBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)

public class EnchantmentShattering extends Enchantment {
    public EnchantmentShattering() {
        super(Rarity.RARE, RandomEnchants.SHOOTABLE, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("shattering");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 25;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return Config.ServerConfig.shattering.get() != Config.Restriction.DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return Config.ServerConfig.shattering.get() == Config.Restriction.ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return Config.ServerConfig.shattering.get() != Config.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.ServerConfig.shattering.get() == Config.Restriction.NORMAL;
    }


    @SubscribeEvent
    public static void arrowHit(ProjectileImpactEvent event) {
        RayTraceResult result = event.getRayTraceResult();
        if (!(result instanceof BlockRayTraceResult)) return;
        Entity arrow = event.getEntity();
        if (!(arrow instanceof AbstractArrowEntity)) return;
        Entity shooter = ((AbstractArrowEntity) arrow).getShooter();
        if (!(shooter instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) ((AbstractArrowEntity) arrow).getShooter();
        if (player == null) return;
        if (!EnchantUtils.hasEnch(player.getHeldItemMainhand(), RandomEnchants.ObjectHolders.SHATTERING)) return;
        BlockPos pos = ((BlockRayTraceResult) result).getPos();
        Block glass = arrow.world.getBlockState(pos).getBlock();
        if (!(glass instanceof GlassBlock)) return;
        arrow.world.destroyBlock(pos, true);
        event.setCanceled(true);
    }
}


