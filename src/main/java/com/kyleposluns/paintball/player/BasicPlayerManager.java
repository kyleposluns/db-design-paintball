package com.kyleposluns.paintball.player;

import com.kyleposluns.paintball.team.PaintballTeam;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class BasicPlayerManager implements PlayerManager {

  private final Map<UUID, PaintballTeam> players;
  private int initialSize;


  BasicPlayerManager() {
    this.players = new HashMap<>();
    this.initialSize = 0;
  }

  @Override
  public void remove(UUID playerId) {
    this.players.remove(playerId);
  }

  @Override
  public void addPlayer(UUID playerId, PaintballTeam team) {
    this.players.put(playerId, team);
    this.initialSize++;
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
    if (!this.hasWinner()) {
      throw new IllegalStateException("No winner yet dawg");
    } else {
      return this.players.keySet().stream().findFirst();
    }
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
