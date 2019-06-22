package com.tfar.randomenchants;

import com.tfar.randomenchants.util.ClientEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {
  @Override
  public void init(FMLInitializationEvent event){
    super.init(event);
    MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
  }
}
