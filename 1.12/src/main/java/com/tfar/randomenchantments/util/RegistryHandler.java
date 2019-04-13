package com.tfar.randomenchantments.util;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import static com.tfar.randomenchantments.util.GlobalVars.itemList;
import static com.tfar.randomenchantments.util.GlobalVars.size;

public class RegistryHandler {

    public void setup() {
        for ( Item item : ForgeRegistries.ITEMS){
            itemList.add(item);
            size++;
        }
    }
}

