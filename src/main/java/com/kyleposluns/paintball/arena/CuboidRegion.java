package com.kyleposluns.paintball.arena;

import java.util.Objects;
import org.bukkit.Location;

public class CuboidRegion implements Region {

  private final Location min;

  private final Location max;

  public CuboidRegion(Location pos1, Location pos2) {
    if (!Objects.requireNonNull(pos1.getWorld()).getUID().equals(
        Objects.requireNonNull(pos2.getWorld()).getUID())) {
      throw new IllegalArgumentException("Cannot have regions that span across multiple worlds.");
    }

    this.min = new Location(pos1.getWorld(), Math.min(pos1.getX(), pos2.getX()),
        Math.min(pos1.getY(), pos2.getY()), Math.min(pos1
        .getZ(), pos2.getZ()));
    this.max = new Location(pos1.getWorld(), Math.max(pos1.getX(), pos2.getX()),
        Math.max(pos1.getY(), pos2.getY()), Math.max(pos1
        .getZ(), pos2.getZ()));
  }

  @Override
  public boolean isInRegion(Location location) {
    if (!Objects.requireNonNull(location.getWorld()).getUID().equals(
        Objects.requireNonNull(this.min.getWorld()).getUID())) {
      return false;
    }

    return this.min.getX() <= location.getX() && location.getX() <= this.max.getX()
        && this.min.getY() <= location.getY() && location.getY() <= this.max.getY()
        && this.min.getZ() <= location.getZ() && location.getZ() <= this.max.getZ();
  }
}
