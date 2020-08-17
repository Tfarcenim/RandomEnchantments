package tfar.randomenchants.ench.enchantment;

import tfar.randomenchants.Config;
import tfar.randomenchants.RandomEnchants;
import tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
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

@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)

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
        return Config.ServerConfig.transposition.get() != Config.Restriction.DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return Config.ServerConfig.transposition.get() == Config.Restriction.ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return Config.ServerConfig.transposition.get() != Config.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.ServerConfig.transposition.get() == Config.Restriction.NORMAL;
    }

    @SubscribeEvent
    public static void teleportArrow(ProjectileImpactEvent e) {
        if(!(e.getRayTraceResult() instanceof EntityRayTraceResult) || !(((EntityRayTraceResult) e.getRayTraceResult()).getEntity() instanceof LivingEntity))return;
        if (!(e.getEntity() instanceof AbstractArrowEntity) || e.getEntity().world.isRemote) return;
        AbstractArrowEntity arrow = (AbstractArrowEntity) e.getEntity();
        Entity shooter = arrow.func_234616_v_();
        if (!(shooter instanceof LivingEntity)) return;
        LivingEntity e1 = (LivingEntity) shooter;
        if (!EnchantUtils.hasEnch(e1, RandomEnchants.ObjectHolders.TRANSPOSITION)) return;
        float[] pos1 = new float[]{(float)e1.getPosX(),(float)e1.getPosY(),(float)e1.getPosZ(),e1.rotationYaw,e1.rotationPitch
        };
        LivingEntity e2 = (LivingEntity) ((EntityRayTraceResult) e.getRayTraceResult()).getEntity();
        float[] pos2 = new float[]{(float)e2.getPosX(),(float)e2.getPosY(),(float)e2.getPosZ(),e2.rotationYaw,e2.rotationPitch};
        e1.setPosition(pos2[0],pos2[1],pos2[2]);
        e2.setPosition(pos1[0],pos1[1],pos1[2]);
        arrow.remove();
    }
}
