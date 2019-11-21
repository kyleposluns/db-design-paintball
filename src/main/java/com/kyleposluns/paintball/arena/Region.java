package com.kyleposluns.paintball.arena;

import org.bukkit.Location;

/**
 * An 3 dimensional area in the world of minecraft.
 */
public interface Region {

  /**
   * Determines if a point is located within this 3 dimensional region. The point must also share
   * the same world as the region.
   *
   * @param location The location that is being tested.
   * @return True if the location is inside the 3d space.
   */
  boolean isInRegion(Location location);

}
