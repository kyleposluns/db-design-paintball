package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.sql.AddKills;
import com.kyleposluns.paintball.sql.UpdateBestWave;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.EntityType;

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
    if (this.kills.containsKey(shooter)) {
      this.kills.get(shooter).add(entityType);
    } else {
      ArrayList<EntityType> ents = new ArrayList<>();
      ents.add(entityType);
      this.kills.put(shooter, ents);
    }
  }

  @Override
  public boolean isTracked(UUID projectile) {
    return this.shots.containsKey(projectile);
  }


  @Override
  public void save(int waveNum, Set<UUID> players) {
    Map<UUID, AddKills> playerToMethod = new HashMap<>();
    for (UUID p : kills.keySet()) {
      if (playerToMethod.containsKey(p)) {
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

    for (UUID h : players) {
      try {
        new UpdateBestWave(this.connection, waveNum, h).run();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public int getKills(UUID player) {
    try {
      PreparedStatement statement = connection.prepareStatement("SELECT killsOverall FROM player WHERE playerID = " + player.toString());
      ResultSet set = statement.executeQuery();
      return set.getInt("killsOverall");
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }

  }
}
