package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import static com.tfar.randomenchantments.RandomEnchantments.SHIELDS;


//@Mod.EventBusSubscriber(modid= GlobalVars.MOD_ID)
public class EnchantmentReflect extends Enchantment {
    public EnchantmentReflect() {

        super(Rarity.RARE, SHIELDS, new EntityEquipmentSlot[]{
                EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND
        });
        this.setRegistryName("reflect");
        this.setName("reflect");
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
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem().isShield(stack, null);
    }


//@SubscribeEvent
  //  public void LivingDamageEvent(EntityLivingBase entity, DamageSource source, float amount){


     //   if (entity instanceof EntityPlayer && EnchantmentHelper.getMaxEnchantmentLevel(REFLECT,entity)>0 &&
           //     (entity.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND)).getItem() == Items.SHIELDS && entity.isHandActive() && source instanceof EntityDamageSource)
  //  {

    //    }

}



    // @SubscribeEvent
  //  public void damageShield(EntityLivingBase user, Entity attacker, int level)
  //  {


    //if (user instanceof EntityPlayer && EnchantmentHelper.getMaxEnchantmentLevel(REFLECT, user) > 0)

     //   {
      //      attacker.attackEntityFrom(DamageSource.GENERIC,20);
       // }

  //  }


