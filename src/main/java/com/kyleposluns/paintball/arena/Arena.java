package com.kyleposluns.paintball.arena;

import java.util.List;
import java.util.UUID;
import org.bukkit.Location;

/**
 * An Arena represents the place in the world our game is taking place. Arenas are READ-ONLY from
 * a database.
 */
public interface Arena {

  /**
   * Gets the name of this arena.
   * @return the name of the arena.
   */
  String getName();

  /**
   * Gets the id of this arena, used as a primary key in a relational database.
   * @return the id of this arena.
   */
  UUID getUniqueId();

  /**
   * Gets the id of the world that this Arena exists in.
   * @return the id of the world.
   */
  UUID getWorldId();

  /**
   * Represents a region that the arena exists in.
   * The affects of the game should only take place inside the bounds of the arena.
   * @return The arena's boundry.
   */
  Region bounds();

  /**
   * Gets all of the spawn locations with the invariant that each location is within the bounds
   * of this arena.
   * @return The location of each spawn.
   */
  List<Location> getSpawns();

}
