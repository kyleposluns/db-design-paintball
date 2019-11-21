package com.kyleposluns.paintball.arena;

import java.util.Set;
import java.util.UUID;

/**
 * Responsible for loading all of the arena's to memory from disk.
 */
public interface ArenaManager {

  /**
   * Find the id of an arena given a name, case is ignored or null if the arena does not exist.
   * @param name The name of the arena that is being searched for.
   * @return The id of of the arena, if it exists.
   */
  UUID getArenaId(String name);

  /**
   * Get the arena stored in memory using its id.
   * @param arenaId The unique id of the arena which is the key.
   * @return The arena that corresponds to the id.
   */
  Arena getArena(UUID arenaId);

  /**
   * Get all of the arena id's that are stored in this arena manager.
   * @return The available arena id's.
   */
  Set<UUID> getAvailableArenas();

}
