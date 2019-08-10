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
import net.minecraft.world.server.ServerWorld;

import java.util.Locale;

public class Coord4D {
  public int xCoord;
  public int yCoord;
  public int zCoord;
  public int dimensionId;
  public Dimension dimension;

  public Coord4D(Entity ent) {
    xCoord = (int)ent.posX;
    yCoord = (int)ent.posY;
    zCoord = (int)ent.posZ;

    dimensionId = ent.dimension.getId();
  }

  public Coord4D(double x, double y, double z,Dimension dimension, int dimensionId) {
    this(x,y,z,dimensionId);
    this.dimension = dimension;
  }

  public Coord4D(double x, double y, double z, int dimensionId) {
    xCoord = MathHelper.floor(x);
    yCoord = MathHelper.floor(y);
    zCoord = MathHelper.floor(z);
    this.dimensionId = dimensionId;
  }

  public Coord4D(BlockPos pos, World world) {
    this(pos.getX(), pos.getY(), pos.getZ(),world.dimension, world.dimension.getType().getId());
  }

  public static Coord4D fromNBT(CompoundNBT nbt) {
    if (nbt.size() == 0) return null;
    return new Coord4D(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"), nbt.getInt("dim"));
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
    return (ServerWorld) dimension.getWorld();
  }

  @Override
  public String toString() {
    return String.format(Locale.US, "[x=%d, y=%d, z=%d] @ dimension %d", xCoord, yCoord, zCoord, dimensionId);
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