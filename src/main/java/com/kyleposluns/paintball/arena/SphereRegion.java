package com.kyleposluns.paintball.arena;

import java.util.Objects;
import org.bukkit.Location;

public class SphereRegion implements Region {

  private final Location center;

  private final double radius;

  public SphereRegion(Location center, double radius) {
    this.center = center;
    this.radius = radius;
  }

  @Override
  public boolean isInRegion(Location location) {
    if (!Objects.requireNonNull(location.getWorld()).getUID().equals(
        Objects.requireNonNull(this.center.getWorld()).getUID())) {
      return false;
    }

    return Math.abs(this.center.getX() - location.getX()) <= this.radius
        && Math.abs(this.center.getY() - location.getY()) <= this.radius
        && Math.abs(this.center.getZ() - location.getZ()) <= this.radius;
  }
}
