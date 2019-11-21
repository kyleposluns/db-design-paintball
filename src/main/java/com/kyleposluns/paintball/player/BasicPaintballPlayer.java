package com.kyleposluns.paintball.player;

import java.util.UUID;

public class BasicPaintballPlayer implements PaintballPlayer {

  private final UUID id;
  private final String name;

  BasicPaintballPlayer(UUID id, String name) {
    this.id = id;
    this.name = name;

  }

  @Override
  public UUID getUniqueId() {
    return null;
  }

  @Override
  public String getName() {
    return null;
  }
}
