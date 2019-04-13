package com.tfar.randomenchantments.util;

import com.tfar.randomenchantments.RandomEnchantments;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Method;

  public class ReflectionUtils {

    public static void callPrivateMethod(Class theClass, Object obj, String name, String obsName) {
      try {
        Method m = ReflectionHelper.findMethod(theClass, name, obsName);
        m.invoke(obj);
      }
      catch (Exception e) {
       RandomEnchantments.logger.error("Reflection error ", e);
      }
    }
  }

