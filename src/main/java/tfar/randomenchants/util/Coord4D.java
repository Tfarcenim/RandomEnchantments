package tfar.randomenchants.util;

import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Objects;

public class Coord4D {
    public int xCoord;
    public int yCoord;
    public int zCoord;
    public RegistryKey<World> registryKey;

    public Coord4D(double x, double y, double z, RegistryKey<World> worldRegistryKey) {
        xCoord = MathHelper.floor(x);
        yCoord = MathHelper.floor(y);
        zCoord = MathHelper.floor(z);
        registryKey = worldRegistryKey;
    }

    public Coord4D(BlockPos pos, World world) {
        this(pos.getX(), pos.getY(), pos.getZ(), world.getDimensionKey());
    }

    @Nullable
    public static Coord4D fromNBT(CompoundNBT nbt) {
        return nbt.isEmpty() ? null : new Coord4D(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"),

                RegistryKey.getOrCreateKey(Registry.WORLD_KEY,new ResourceLocation(nbt.getString("Dimension"))));
    }

    public CompoundNBT toNBT(CompoundNBT nbt) {
        nbt.putInt("x", xCoord);
        nbt.putInt("y", yCoord);
        nbt.putInt("z", zCoord);
        nbt.putString("Dimension", registryKey.getLocation().toString());
        return nbt;
    }

    public BlockPos pos() {
        return new BlockPos(xCoord, yCoord, zCoord);
    }

    public static RegistryKey<World> getRegistryKey(CompoundNBT nbt) {
        return nbt != null ? DimensionType.decodeWorldKey(new Dynamic<>(NBTDynamicOps.INSTANCE, nbt)).resultOrPartial(LOGGER -> {
                }
        ).orElse(World.OVERWORLD) : World.OVERWORLD;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "[x=%d, y=%d, z=%d] @ dimensionType %d", xCoord, yCoord, zCoord, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord4D coord4D = (Coord4D) o;
        return xCoord == coord4D.xCoord &&
                yCoord == coord4D.yCoord &&
                zCoord == coord4D.zCoord &&
                Objects.equals(registryKey, coord4D.registryKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoord, yCoord, zCoord, registryKey);
    }
}