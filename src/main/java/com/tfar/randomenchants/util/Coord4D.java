package com.tfar.randomenchants.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.Locale;

public class Coord4D {
  public int xCoord;
  public int yCoord;
  public int zCoord;
  public int dimensionId;
  public DimensionType dimensionType;

  public Coord4D(Entity ent) {
    xCoord = (int)ent.func_226277_ct_();
    yCoord = (int)ent.func_226278_cu_();
    zCoord = (int)ent.func_226281_cx_();

    dimensionId = ent.dimension.getId();
  }

  public Coord4D(double x, double y, double z, int dimensionId) {
    xCoord = MathHelper.floor(x);
    yCoord = MathHelper.floor(y);
    zCoord = MathHelper.floor(z);
    this.dimensionId = dimensionId;
  }

  public Coord4D(BlockPos pos, World world) {
    this(pos.getX(), pos.getY(), pos.getZ(), world.dimension.getType().getId());
  }

  @Nullable
  public static Coord4D fromNBT(CompoundNBT nbt) {
    return nbt.isEmpty() ? null : new Coord4D(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"), nbt.getInt("dim"));
  }

  public CompoundNBT toNBT(CompoundNBT nbt) {
    nbt.putInt("x", xCoord);
    nbt.putInt("y", yCoord);
    nbt.putInt("z", zCoord);
    nbt.putInt("dim", dimensionId);
    return nbt;
  }

  public static Coord4D fromByteBuf(ByteBuf bb) {
    return new Coord4D(bb.readInt(), bb.readInt(), bb.readInt(), bb.readInt());
  }

  public ByteBuf toByteBuf(ByteBuf bb) {
    return bb.writeInt(xCoord).writeInt(yCoord).writeInt(zCoord).writeInt(dimensionId);
  }

  public Coord4D add(int x, int y, int z) {
    return new Coord4D(xCoord+x, yCoord+y, zCoord+z, dimensionId);
  }

  public BlockState blockState() {
    ServerWorld world = world();
    if (world == null) return null;
    return world.getBlockState(pos());
  }

  public TileEntity TE() {
    ServerWorld world = world();
    return world == null ? null : world.getTileEntity(pos());
  }

  public BlockPos pos() {
    return new BlockPos(xCoord, yCoord, zCoord);
  }

  public ServerWorld world() {
    return DimensionManager.getWorld(ServerLifecycleHooks.getCurrentServer(),DimensionType.getById(dimensionId),false,false);
  }

  @Override
  public String toString() {
    return String.format(Locale.US, "[x=%d, y=%d, z=%d] @ dimensionType %d", xCoord, yCoord, zCoord, dimensionId);
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Coord4D &&
            ((Coord4D)obj).xCoord == xCoord &&
            ((Coord4D)obj).yCoord == yCoord &&
            ((Coord4D)obj).zCoord == zCoord &&
            ((Coord4D)obj).dimensionId == dimensionId;
  }
}