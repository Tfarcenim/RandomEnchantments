package tfar.randomenchants.ench.enchantment;

import tfar.randomenchants.Config;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.item.ItemStack;

import static tfar.randomenchants.RandomEnchants.SWORDS_BOWS;

public class EnchantmentParalysis extends Enchantment {
    public EnchantmentParalysis() {

        super(Rarity.RARE, SWORDS_BOWS, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND
        });
        this.setRegistryName("paralysis");
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
        return Config.ServerConfig.paralysis.get() != Config.Restriction.DISABLED && super.canApply(stack);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return Config.ServerConfig.paralysis.get() == Config.Restriction.ANVIL;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return Config.ServerConfig.paralysis.get() != Config.Restriction.DISABLED && super.canApplyAtEnchantingTable(stack);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return Config.ServerConfig.paralysis.get() == Config.Restriction.NORMAL;
    }

    @Override
    public void onEntityDamaged(LivingEntity player, Entity target, int level)  {

        if (target instanceof LivingEntity && player instanceof PlayerEntity){
            ((LivingEntity)target).
                    addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 200, 128));
            ((LivingEntity)target).
                    addPotionEffect(new EffectInstance(Effects.SLOWNESS, 200, 5));
            ((LivingEntity)target).
                    addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 200, 4));
            ((LivingEntity)target).
                    addPotionEffect(new EffectInstance(Effects.WEAKNESS, 200, 20));

        }
    }
}

