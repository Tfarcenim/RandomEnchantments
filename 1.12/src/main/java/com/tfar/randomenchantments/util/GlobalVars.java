package com.tfar.randomenchantments.util;

import net.minecraft.item.Item;

import java.util.ArrayList;

public class GlobalVars {
    public static final String MOD_ID = "randomenchantments";
    public static final String NAME = "Random Enchants";
    public static final String VERSION = "@VERSION@";
    public static final String MC_VERSION = "1.12.2,";
    public static final ArrayList<Item> itemList = new ArrayList<>();
    public static final String CLIENT_PROXY_CLASS = "com.tfar."+MOD_ID+".ClientProxy";
    public static final String COMMON_PROXY_CLASS = "com.tfar."+MOD_ID+".CommonProxy";
    public static int size = 0;
}
