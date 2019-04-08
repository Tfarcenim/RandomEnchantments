package com.tfar.randomenchantments;

import com.tfar.randomenchantments.util.ClientEventHandler;
import net.minecraftforge.common.MinecraftForge;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {
  @Override
  public void init(){
    MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
  }
}
