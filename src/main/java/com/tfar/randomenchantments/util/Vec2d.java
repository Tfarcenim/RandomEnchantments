package com.tfar.randomenchantments.util;

public class Vec2d
{
  public static final Vec2d ZERO = new Vec2d(0.0D, 0.0D);
  /** X coordinate of Vec2D */
  public final double x;
  /** Z coordinate of Vec2D */
  public final double z;

  public Vec2d(double xIn, double zIn)
  {
    if (xIn == -0.0D)
    {
      xIn = 0.0D;
    }

    if (zIn == -0.0D)
    {
      zIn = 0.0D;
    }

    this.x = xIn;
    this.z = zIn;
  }
}


