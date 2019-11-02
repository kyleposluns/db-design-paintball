package com.kyleposluns.paintball.game.projectiles;

import java.util.UUID;
import java.util.Vector;
import org.bukkit.Location;

public class Paintball {

  public final UUID id;

  private final Vector direction;

  private final Location start;

  public Paintball(UUID id, Location start, Vector direction) {
    this.id = id;
    this.start = start;
    this.direction = direction;
  }



}
