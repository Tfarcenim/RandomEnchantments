package com.tfar.randomenchants.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandler implements IMessage {

  public boolean increment;

  public PacketHandler(){}

  @Override
  public void fromBytes(ByteBuf buf) {
    this.increment = buf.readBoolean();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeBoolean(increment);
  }

  public static class Handler implements IMessageHandler<PacketHandler, IMessage> {
    @Override
    public IMessage onMessage(PacketHandler message, MessageContext ctx) {
      FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
        ItemStack stack = ctx.getServerHandler().player.getHeldItemMainhand();
        stack.getTagCompound().setInteger("combo", 1);
      });
      return null;
    }
  }
}
