package com.tfar.randomenchants.entity;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderTippedArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderPiercingArrow extends RenderArrow {
  public RenderPiercingArrow(RenderManager renderManagerIn) {
    super(renderManagerIn);
  }

  /**
   * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
   *
   * @param entity
   */
  @Nullable
  @Override
  protected ResourceLocation getEntityTexture(Entity entity) {
    return RenderTippedArrow.RES_ARROW;
  }
}
