package com.kyleposluns.paintball.player;

import com.kyleposluns.paintball.sql.AddPlayer;
import com.kyleposluns.paintball.team.PaintballTeam;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class PlayerManagerImpl implements PlayerManager {

  private final Map<UUID, PaintballTeam> players;
  private final Connection conn;
  private int initialSize;

  public PlayerManagerImpl(Connection conn) {
    this.players = new HashMap<>();
    this.initialSize = 0;
    this.conn = conn;
  }

  @Override
  public void remove(UUID playerId) {
    this.players.remove(playerId);
  }

  @Override
  public void addPlayer(UUID playerId, PaintballTeam team) {
    this.players.put(playerId, team);
    this.initialSize++;
    try {
      new AddPlayer(conn, playerId).run();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean isAlive(UUID playerId) {
    return this.players.containsKey(playerId);
  }

  @Override
  public int getAllPlayers() {
    return this.initialSize;
  }

  @Override
  public boolean hasWinner() {
    return this.players.size() == 1;
  }

  @Override
  public Optional<UUID> getWinner() {
    return this.players.keySet().stream().findFirst();
  }

  @Override
  public Set<UUID> getActivePlayers() {
    return this.players.keySet();
  }

  @Override
  public PaintballTeam getTeam(UUID playerId) {
    return this.players.get(playerId);
  }

}
