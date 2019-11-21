package com.kyleposluns.paintball.arena;

import org.yaml.snakeyaml.events.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BasicArenaManager implements ArenaManager {

  private final Map<String, UUID> NameToID;
  private final Map<UUID, String> IDToName;

  BasicArenaManager(List<Arena> arenas) {
    HashMap<String, UUID> NameToID = new HashMap<>();
    HashMap<UUID, String> IDToName = new HashMap<>();
    for (Arena a : arenas) {
      String currName = a.getName();
      UUID currUUID = a.getUniqueId();
      NameToID.put(currName, currUUID);
      IDToName.put(currUUID, currName);
    }
    this.NameToID = NameToID;
    this.IDToName = IDToName;
  }

  @Override
  public UUID getArenaId(String name) {
    return NameToID.get(name);
  }

  @Override
  public Arena loadArena(UUID arenaId) {
    return null;
  }

  @Override
  public Set<UUID> getAvailableArenas() {
    return null;
  }
}
