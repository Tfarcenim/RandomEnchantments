package tfar.randomenchants.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.randomenchants.util.MixinEvents;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(Block.class)
public class BlockMixin {

    private static final ThreadLocal<BlockState> stateThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<TileEntity> tileEntityThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Entity> entityThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<ItemStack> toolStackThreadLocal = new ThreadLocal<>();


    @Inject(method = "spawnDrops(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",at = @At("HEAD"),cancellable = true)
    private static void captureContext(BlockState state, World worldIn, BlockPos pos, CallbackInfo ci) {
        capture(state, null,null,ItemStack.EMPTY);
    }

    @Inject(method = "spawnDrops(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/tileentity/TileEntity;)V",at = @At("HEAD"),cancellable = true)
    private static void captureContext1(BlockState state, IWorld worldIn, BlockPos pos, TileEntity tileEntityIn, CallbackInfo ci) {
        capture(state, tileEntityIn,null,ItemStack.EMPTY);
    }

    @Inject(method = "spawnDrops(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/tileentity/TileEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V",at = @At("HEAD"),cancellable = true)
    private static void captureContext2(BlockState state, World worldIn, BlockPos pos, TileEntity tileEntityIn, Entity entityIn, ItemStack stack, CallbackInfo ci) {
        capture(state, tileEntityIn,entityIn,stack);
    }

    @Inject(method = "getDrops(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/tileentity/TileEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;",at = @At("RETURN"),cancellable = true)
    private static void modifyDrops(BlockState state, ServerWorld worldIn, BlockPos pos, TileEntity blockEntity, Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (MixinEvents.modifyDrops(state,worldIn,pos, blockEntity, entity,stack,cir.getReturnValue())) {
        }
    }

    private static void capture(BlockState state, @Nullable TileEntity tileEntityIn, @Nullable Entity entityIn, ItemStack stack) {
        stateThreadLocal.set(state);
        tileEntityThreadLocal.set(tileEntityIn);
        entityThreadLocal.set(entityIn);
        toolStackThreadLocal.set(stack);
    }

    @Inject(method = "*(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V",at = @At("HEAD"))
    private static void interceptBlockDrops(World world, BlockPos pos, ItemStack dropped, CallbackInfo ci) {
        MixinEvents.handleBlockSpawn(stateThreadLocal.get(),world,pos,tileEntityThreadLocal.get(),entityThreadLocal.get(), toolStackThreadLocal.get(),dropped);
    }
}
