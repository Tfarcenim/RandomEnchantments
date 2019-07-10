package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.network.PacketHandler;
import com.tfar.randomenchants.util.EnchantmentUtils;
import com.tfar.randomenchants.util.GlobalVars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
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

import static com.tfar.randomenchants.EnchantmentConfig.EnumAccessLevel.*;
import static com.tfar.randomenchants.EnchantmentConfig.*;
import static com.tfar.randomenchants.init.ModEnchantment.COMBO;
import static net.minecraft.enchantment.EnumEnchantmentType.WEAPON;

@Mod.EventBusSubscriber(modid = GlobalVars.MOD_ID)
public class EnchantmentCombo extends Enchantment {
  public EnchantmentCombo() {

    super(Rarity.RARE, WEAPON, new EntityEquipmentSlot[]{
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
  public boolean canApply(ItemStack stack){
    return weapons.enableCombo != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return weapons.enableCombo == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return weapons.enableCombo != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return weapons.enableCombo == NORMAL;
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
    if (!EnchantmentUtils.stackHasEnch(e.getItemStack(),COMBO)) return;
    RandomEnchants.NETWORK_WRAPPER.sendToServer(new PacketHandler());
  }

  @SubscribeEvent
  public static void onHitBlock(PlayerInteractEvent.LeftClickBlock e) {
    EntityPlayer p = e.getEntityPlayer();
    if (!EnchantmentUtils.stackHasEnch(e.getItemStack(),COMBO)) return;
    RandomEnchants.NETWORK_WRAPPER.sendToServer(new PacketHandler());
  }
}



