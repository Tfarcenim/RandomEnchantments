package com.tfar.randomenchants.ench.enchantment;

import com.tfar.randomenchants.Config;
import com.tfar.randomenchants.RandomEnchants;
import com.tfar.randomenchants.util.EnchantUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.tfar.randomenchants.Config.Restriction.*;
import static com.tfar.randomenchants.RandomEnchants.ObjectHolders.DUNGEONEERING;


@Mod.EventBusSubscriber(modid = RandomEnchants.MODID)
public class EnchantmentDungeoneering extends Enchantment {
  public EnchantmentDungeoneering() {

    super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{
            EquipmentSlotType.MAINHAND
    });
    this.setRegistryName("dungeoneering");
  }

  @Override
  public int getMinEnchantability(int level) {
    return 5 + 10 * (level - 1);
  }

  @Override
  public int getMaxLevel() {
    return 5;
  }

  @Override
  public boolean canApply(ItemStack stack) {
    return Config.ServerConfig.dungeoneering.get() != DISABLED && super.canApply(stack);
  }

  @Override
  public boolean isTreasureEnchantment() {
    return Config.ServerConfig.dungeoneering.get() == ANVIL;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return Config.ServerConfig.dungeoneering.get() != DISABLED && super.canApplyAtEnchantingTable(stack);
  }

  @Override
  public boolean isAllowedOnBooks() {
    return Config.ServerConfig.dungeoneering.get() == NORMAL;
  }

  @SubscribeEvent
  public static void addDungeonDrops(LivingDropsEvent e) {
    Entity attacker = e.getSource().getTrueSource();
    if (attacker instanceof PlayerEntity) {
      PlayerEntity p = (PlayerEntity) attacker;
      LivingEntity victim = e.getEntityLiving();
      World world = p.getEntityWorld();
      if (EnchantUtils.hasEnch(p.getHeldItemMainhand(), DUNGEONEERING) && !world.isRemote) {
        ServerWorld serverWorld = (ServerWorld)world;
        double chance = .10 * EnchantmentHelper.getEnchantmentLevel(DUNGEONEERING,p.getHeldItemMainhand());
        if (serverWorld.rand.nextDouble() > chance)return;
        LootContext.Builder builder = (new LootContext.Builder(serverWorld))
                .withRandom(serverWorld.rand)
                .withParameter(LootParameters.THIS_ENTITY,p)
                .withParameter(LootParameters.POSITION,victim.getPosition())
                .withParameter(LootParameters.DAMAGE_SOURCE,e.getSource());
        LootContext lootcontext = builder.build(LootParameterSets.ENTITY);
        ResourceLocation rl = new ResourceLocation("chests/simple_dungeon");
        LootTable loottable = serverWorld.getServer().getLootTableManager().getLootTableFromLocation(rl);
        List<ItemStack> drops = loottable.generate(lootcontext);
        ItemStack stack = drops.listIterator().next();
        e.getDrops().add(new ItemEntity(serverWorld,victim.getPosX(),victim.getPosY(),victim.getPosZ(),stack));
      }
    }
  }
}
