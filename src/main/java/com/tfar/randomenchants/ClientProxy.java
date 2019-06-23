package com.tfar.randomenchants;

import com.tfar.randomenchants.util.ClientEventHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientProxy  {

@SubscribeEvent
  public void init(FMLClientSetupEvent event){
    MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
  }
}
