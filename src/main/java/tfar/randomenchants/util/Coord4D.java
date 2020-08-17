package tfar.randomenchants.util;

import com.mojang.serialization.Dynamic;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.Locale;

public class Coord4D {
    public int xCoord;
    public int yCoord;
    public int zCoord;
    public RegistryKey<World> dimensionType;

    public Coord4D(Entity ent) {
        xCoord = (int) ent.getPosX();
        yCoord = (int) ent.getPosY();
        zCoord = (int) ent.getPosZ();

        dimensionType = ent.world.func_234923_W_();
    }

    public Coord4D(double x, double y, double z, RegistryKey<World> worldRegistryKey) {
        xCoord = MathHelper.floor(x);
        yCoord = MathHelper.floor(y);
        zCoord = MathHelper.floor(z);
        dimensionType = worldRegistryKey;
    }

    public Coord4D(BlockPos pos, World world) {
        this(pos.getX(), pos.getY(), pos.getZ(), world.func_234923_W_());
    }

    @Nullable
    public static Coord4D fromNBT(CompoundNBT nbt) {
        return nbt.isEmpty() ? null : new Coord4D(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"),

                RegistryKey.func_240903_a_(Registry.WORLD_KEY,new ResourceLocation(nbt.getString("Dimension"))));
    }

    public CompoundNBT toNBT(CompoundNBT nbt) {
        nbt.putInt("x", xCoord);
        nbt.putInt("y", yCoord);
        nbt.putInt("z", zCoord);
        nbt.putString("Dimension", dimensionType.func_240901_a_().toString());
        return nbt;
    }

    public BlockPos pos() {
        return new BlockPos(xCoord, yCoord, zCoord);
    }

    public static RegistryKey<World> getRegistryKey(CompoundNBT nbt) {
        return nbt != null ? DimensionType.func_236025_a_(new Dynamic<>(NBTDynamicOps.INSTANCE, nbt)).resultOrPartial(LOGGER -> {
                }
        ).orElse(World.field_234918_g_) : World.field_234918_g_;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "[x=%d, y=%d, z=%d] @ dimensionType %d", xCoord, yCoord, zCoord, null);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Coord4D &&
                ((Coord4D) obj).xCoord == xCoord &&
                ((Coord4D) obj).yCoord == yCoord &&
                ((Coord4D) obj).zCoord == zCoord;// &&
                //((Coord4D) obj).dimensionId == null;
    }
}