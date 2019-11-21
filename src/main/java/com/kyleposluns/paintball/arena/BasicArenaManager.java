package com.kyleposluns.paintball.arena;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BasicArenaManager implements ArenaManager {

  private final Map<String, UUID> nameToID;
  private final Map<UUID, Arena> idToArena;

  BasicArenaManager(List<Arena> arenas) {
    HashMap<String, UUID> NameToID = new HashMap<>();
    HashMap<UUID, Arena> IDToArena = new HashMap<>();
    for (Arena a : arenas) {
      String currName = a.getName();
      UUID currUUID = a.getUniqueId();
      NameToID.put(currName, currUUID);
      IDToArena.put(currUUID, a);
    }
    this.nameToID = NameToID;
    this.idToArena = IDToArena;
  }

  @Override
  public UUID getArenaId(String name) {
    return nameToID.get(name);
  }

  @Override
  public Arena getArena(UUID arenaId) {
    return idToArena.get(arenaId);
  }

  @Override
  public Set<UUID> getAvailableArenas() {
    return idToArena.keySet();
  }
}
