package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.sql.AddKills;
import com.kyleposluns.paintball.sql.SQLCommand;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ProjectileTrackImpl implements KillHandler {

  private final Map<UUID, UUID> shots;
  private final Map<UUID, List<EntityType>> kills;
  private final Connection connection;

  ProjectileTrackImpl(Connection connection) {
    this.kills = new HashMap<>();
    this.shots = new HashMap<>();
    this.connection = connection;
  }

  @Override
  public void logShot(UUID shooter, UUID projectile) {
    this.shots.put(projectile, shooter);
  }


  @Override
  public Optional<UUID> getShooter(UUID projectile) {
    return Optional.of(shots.get(projectile));
  }

  @Override
  public void logKill(UUID shooter, EntityType entityType) {
    if (this.kills.keySet().contains(shooter)) {
      this.kills.get(shooter).add(entityType);
    } else {
      ArrayList<EntityType> ents = new ArrayList<EntityType>();
      ents.add(entityType);
      this.kills.put(shooter, ents);
    }
  }

  @Override
  public boolean isTracked(UUID projectile) {
    return this.shots.keySet().contains(projectile);
  }


  @Override
  public void save() {
    Map<UUID, AddKills> playerToMethod = new HashMap<>();
    for (UUID p : kills.keySet()) {
      if (playerToMethod.keySet().contains(p)) {
        playerToMethod.get(p).increment();
      } else {
        AddKills kills = new AddKills(connection, p);
        kills.increment();
        playerToMethod.put(p, kills);
      }
    }
    for (UUID q : playerToMethod.keySet()) {
      playerToMethod.get(q).run();
    }
  }
}