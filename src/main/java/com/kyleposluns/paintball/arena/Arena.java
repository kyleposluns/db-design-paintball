package com.kyleposluns.paintball.arena;

import java.util.List;
import java.util.UUID;
import org.bukkit.Location;

public interface Arena {

  String getName();

  UUID getUniqueId();

  Region bounds();

  List<Location> getSpawns();

}
