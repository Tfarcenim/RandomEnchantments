package com.tfar.randomenchants.network;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketComboReset {

  public PacketComboReset(){}


  public PacketComboReset(PacketBuffer buf) {

  }

  public void encode(PacketBuffer buf) {

  }

  public void handle(Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      if (ctx.get() == null || ctx.get().getSender() == null)return;
      ItemStack stack = ctx.get().getSender().getHeldItemMainhand();
      stack.getOrCreateTag().putInt("combo", 1);});
    ctx.get().setPacketHandled(true);
  }
}
