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

  Arena loadArena(UUID arenaId);

  Set<UUID> getAvailableArenas();

}
