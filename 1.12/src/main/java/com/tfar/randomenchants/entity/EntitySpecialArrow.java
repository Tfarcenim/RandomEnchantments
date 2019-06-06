package com.tfar.randomenchants.entity;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static net.minecraft.entity.projectile.EntityTippedArrow.getCustomColor;

public class EntitySpecialArrow extends EntityArrow {
  private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntitySpecialArrow.class, DataSerializers.VARINT);
  private PotionType potion = PotionTypes.EMPTY;
  private final Set<PotionEffect> customPotionEffects = Sets.newHashSet();
  private boolean fixedColor;

  /**
   * The amount of knockback an arrow applies when it hits a mob.
   */
  private int knockbackStrength;


  public EntitySpecialArrow(World worldIn) {
    super(worldIn);
  }

  public EntitySpecialArrow(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
  }

  public EntitySpecialArrow(World worldIn, EntityLivingBase shooter) {
    super(worldIn, shooter);
  }

  private void refreshColor() {
    this.fixedColor = false;
    this.dataManager.set(COLOR, PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.customPotionEffects)));
  }

  public void addEffect(PotionEffect effect) {
    this.customPotionEffects.add(effect);
    this.getDataManager().set(COLOR, PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.customPotionEffects)));
  }

  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(COLOR, -1);
  }


  public int getColor() {
    return this.dataManager.get(COLOR);
  }

  @Override
  @Nonnull
  protected ItemStack getArrowStack() {
    if (this.customPotionEffects.isEmpty() && this.potion == PotionTypes.EMPTY) {
      return new ItemStack(Items.ARROW);
    } else {
      ItemStack itemstack = new ItemStack(Items.TIPPED_ARROW);
      PotionUtils.addPotionToItemStack(itemstack, this.potion);
      PotionUtils.appendEffects(itemstack, this.customPotionEffects);

      if (this.fixedColor) {
        NBTTagCompound nbttagcompound = itemstack.getTagCompound();

        if (nbttagcompound == null) {
          nbttagcompound = new NBTTagCompound();
          itemstack.setTagCompound(nbttagcompound);
        }

        nbttagcompound.setInteger("CustomPotionColor", this.getColor());
      }

      return itemstack;
    }
  }

  @Override
  protected void onHit(RayTraceResult raytraceResultIn) {
    Entity entity = raytraceResultIn.entityHit;
   // if (!this.world.isRemote) return;
    if (entity != null) {
      float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
      int i = MathHelper.ceil((double) f * (double) ObfuscationReflectionHelper.getPrivateValue(EntityArrow.class, this, "field_70255_ao"));

      if (this.getIsCritical()) {
        i += this.rand.nextInt(i / 2 + 2);
      }

      DamageSource damagesource;

      if (this.shootingEntity == null) {
        damagesource = DamageSource.causeArrowDamage(this, this);
      } else {
        damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
      }

      if (this.isBurning() && !(entity instanceof EntityEnderman)) {
        entity.setFire(5);
      }

      if (entity.attackEntityFrom(damagesource, (float) i)) {
        if (entity instanceof EntityLivingBase) {
          EntityLivingBase entitylivingbase = (EntityLivingBase) entity;

          if (!this.world.isRemote) {
            entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
          }

          if (this.knockbackStrength > 0) {
            float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

            if (f1 > 0) {
              entitylivingbase.addVelocity(this.motionX * (double) this.knockbackStrength * 0.6 / (double) f1, 0.1D, this.motionZ * (double) this.knockbackStrength * 0.6000000238418579D / (double) f1);
            }
          }

          if (this.shootingEntity instanceof EntityLivingBase) {
            EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
            EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) this.shootingEntity, entitylivingbase);
          }

          this.arrowHit(entitylivingbase);

          if (this.shootingEntity != null && entitylivingbase != this.shootingEntity && entitylivingbase instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
            ((EntityPlayerMP) this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0));
          }
        }

        this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

        if (!(entity instanceof EntityEnderman)) {
          //  this.setDead();
        }
      } else {
        //   this.motionX *= -0.1;
        // this.motionY *= -0.1;
        //  this.motionZ *= -0.1;
        //  this.rotationYaw += 180;
        //  this.prevRotationYaw += 180;

        if (!this.world.isRemote && this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ < 0.001) {
          if (this.pickupStatus == EntityArrow.PickupStatus.ALLOWED) {
            this.entityDropItem(this.getArrowStack(), 0.1F);
          }
          this.setDead();
        }
      }
    } else {
      BlockPos blockpos = raytraceResultIn.getBlockPos();
      IBlockState iblockstate = this.world.getBlockState(blockpos);
      Block inTile = iblockstate.getBlock();
      this.motionX = (double) (float) (raytraceResultIn.hitVec.x - this.posX);
      this.motionY = (double) (float) (raytraceResultIn.hitVec.y - this.posY);
      this.motionZ = (double) (float) (raytraceResultIn.hitVec.z - this.posZ);
      float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
      this.posX -= this.motionX / (double) f2 * 0.05;
      this.posY -= this.motionY / (double) f2 * 0.05;
      this.posZ -= this.motionZ / (double) f2 * 0.05;
      this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
      this.inGround = true;
      this.arrowShake = 7;
      this.setIsCritical(false);

      if (iblockstate.getMaterial() != Material.AIR) {
        inTile.onEntityCollision(this.world, blockpos, iblockstate, this);
      }
    }
  }

  /**
   * (abstract) Protected helper method to read subclass entity data from NBT.
   */
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);

    if (compound.hasKey("Potion", 8)) {
      this.potion = PotionUtils.getPotionTypeFromNBT(compound);
    }

    for (PotionEffect potioneffect : PotionUtils.getFullEffectsFromTag(compound)) {
      this.addEffect(potioneffect);
    }

    if (compound.hasKey("Color", 99)) {
      this.setFixedColor(compound.getInteger("Color"));
    } else {
      this.refreshColor();
    }
  }

  protected void arrowHit(EntityLivingBase living) {
    super.arrowHit(living);

    for (PotionEffect potioneffect : this.potion.getEffects()) {
      living.addPotionEffect(new PotionEffect(potioneffect.getPotion(), Math.max(potioneffect.getDuration() / 8, 1), potioneffect.getAmplifier(), potioneffect.getIsAmbient(), potioneffect.doesShowParticles()));
    }

    if (!this.customPotionEffects.isEmpty()) {
      for (PotionEffect potioneffect1 : this.customPotionEffects) {
        living.addPotionEffect(potioneffect1);
      }
    }
  }

  public void setPotionEffect(ItemStack stack) {
    if (stack.getItem() == Items.TIPPED_ARROW) {
      this.potion = PotionUtils.getPotionFromItem(stack.copy());
      Collection<PotionEffect> collection = PotionUtils.getFullEffectsFromItem(stack);

      if (!collection.isEmpty()) {
        for (PotionEffect potioneffect : collection) {
          this.customPotionEffects.add(new PotionEffect(potioneffect));
        }
      }

      int i = getCustomColor(stack);

      if (i == -1) {
        this.refreshColor();
      } else {
        this.setFixedColor(i);
      }
    } else if (stack.getItem() == Items.ARROW) {
      this.potion = PotionTypes.EMPTY;
      this.customPotionEffects.clear();
      this.dataManager.set(COLOR, -1);
    }
  }

  private void setFixedColor(int p_191507_1_) {
    this.fixedColor = true;
    this.dataManager.set(COLOR, p_191507_1_);
  }

  /**
   * Handler for {@link World#setEntityState}
   */
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 0) {
      int i = this.getColor();

      if (i != -1) {
        double d0 = (double) (i >> 16 & 255) / 255.0D;
        double d1 = (double) (i >> 8 & 255) / 255.0D;
        double d2 = (double) (i & 255) / 255.0D;

        for (int j = 0; j < 20; ++j) {
          this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, d0, d1, d2);
        }
      }
    } else {
      super.handleStatusUpdate(id);
    }
  }
}