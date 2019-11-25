package com.kyleposluns.paintball.arena;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ArenaManagerImpl implements ArenaManager {

  private final Map<String, UUID> nameToID;
  private final Map<UUID, Arena> idToArena;

  public ArenaManagerImpl(List<Arena> arenas) {
    arenas.add(new BadBoyArena());
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

  private class BadBoyArena implements Arena {


    @Override
    public String getName() {
      return "OferTheyreForest";
    }

    @Override
    public UUID getUniqueId() {
      return UUID.fromString("20eb6653-6d66-44f2-bc3e-3421e3a4c04b");
    }

    @Override
    public UUID getWorldId() {
      return UUID.fromString("20eb6653-6d66-44f2-bc3e-3421e3a4c04b");
    }

    @Override
    public Region bounds() {

      Location min = new Location(Bukkit.getWorld(this.getWorldId()), -891, 103, -268);
      Location max = new Location(Bukkit.getWorld(this.getWorldId()), -831, 118, -226);

      return new CuboidRegion(min, max);
    }

    @Override
    public List<Location> getSpawns() {
      return Arrays.asList(new Location(Bukkit.getWorld(this.getWorldId()), -891, 103, -268),
          new Location(Bukkit.getWorld(this.getWorldId()), -831, 103, -226));
    }
  }
}
