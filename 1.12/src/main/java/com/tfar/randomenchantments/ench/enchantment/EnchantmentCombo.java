package com.tfar.randomenchantments.ench.enchantment;

import com.tfar.randomenchantments.EnchantmentConfig;
import com.tfar.randomenchantments.RandomEnchantments;
import com.tfar.randomenchantments.network.PacketHandler;
import com.tfar.randomenchantments.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.tfar.randomenchantments.EnchantmentConfig.weapons;
import static com.tfar.randomenchantments.init.ModEnchantment.COMBO;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)
public class EnchantmentCombo extends Enchantment {
  public EnchantmentCombo() {

    super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
    });
    this.setRegistryName("combo");
    this.setName("combo");
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
  public boolean isAllowedOnBooks() {
    return weapons.enableCombo == EnchantmentConfig.EnumAccessLevel.NORMAL;
  }

  @Override
  public boolean canApply(ItemStack stack){
    return weapons.enableBackToTheChamber != EnchantmentConfig.EnumAccessLevel.DISABLED && super.canApply(stack);
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return isAllowedOnBooks();
  }

  @SubscribeEvent
  public static void onEntityDamaged(LivingDamageEvent e) {
    EntityLivingBase target = e.getEntityLiving();
    if (!(e.getSource().getTrueSource() instanceof EntityPlayer)) return;
    EntityPlayer p = (EntityPlayer) e.getSource().getTrueSource();
    if (!p.world.isRemote && EnchantmentHelper.getMaxEnchantmentLevel(COMBO, p) > 0) {
      ItemStack stack = p.getHeldItemMainhand();
      NBTTagCompound compound = stack.getTagCompound();
      target.attackEntityFrom(DamageSource.GENERIC, compound.getInteger("combo") * .5f);
      compound.setInteger("combo", compound.getInteger("combo") + 1);
    }
  }

  @SubscribeEvent
  public static void onMiss(PlayerInteractEvent.LeftClickEmpty e) {
    EntityPlayer p = e.getEntityPlayer();
    if (EnchantmentHelper.getMaxEnchantmentLevel(COMBO, p) == 0) return;
    RandomEnchantments.NETWORK_WRAPPER.sendToServer(new PacketHandler());
  }

  @SubscribeEvent
  public static void onHitBlock(PlayerInteractEvent.LeftClickBlock e) {
    EntityPlayer p = e.getEntityPlayer();
    if (EnchantmentHelper.getMaxEnchantmentLevel(COMBO, p) == 0) return;
    RandomEnchantments.NETWORK_WRAPPER.sendToServer(new PacketHandler());
  }
}



