package tfar.randomenchants.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.randomenchants.RandomEnchants;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow public abstract ItemStack getItem();

    @Inject(method = "attackEntityFrom",at = @At("HEAD"),cancellable = true)
    private void applyNetherProof(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        if (source.isFireDamage() && EnchantmentHelper.getEnchantmentLevel(RandomEnchants.ObjectHolders.NETHERPROOF,this.getItem()) > 0) {
            cir.setReturnValue(false);
        }
    }
}
