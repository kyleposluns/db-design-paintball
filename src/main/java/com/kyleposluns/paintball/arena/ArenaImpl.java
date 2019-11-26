package com.kyleposluns.paintball.arena;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;

public class ArenaImpl implements Arena {

  private final String name;
  private final UUID id;
  private final UUID worldID;
  private final Region reg;
  private final List<Location> spawns;
  public ArenaImpl(String name, UUID id, UUID worldID, Region reg, int spawns) {
    this.name = name;
    this.id = id;
    this.worldID = worldID;
    this.reg = reg;
    this.spawns = new ArrayList<>();
    for (int i = 0; i < spawns; i++) {
      this.spawns.add(reg.findRandomLocation());
    }
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public UUID getUniqueId() {
    return this.id;
  }

  @Override
  public UUID getWorldId() {
    return this.worldID;
  }

  @Override
  public Region bounds() {
    return this.reg;
  }

  @Override
  public List<Location> getSpawns() {
    return this.spawns;
  }
}
