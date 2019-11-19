package com.kyleposluns.paintball.arena;

import java.util.Set;
import java.util.UUID;

public interface ArenaManager {

  UUID getArenaId(String name);

  Arena loadArena(UUID arenaId);

  Set<UUID> getAvailableArenas();

}
