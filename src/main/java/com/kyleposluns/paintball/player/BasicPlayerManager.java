package com.kyleposluns.paintball.player;

import com.kyleposluns.paintball.team.PaintballTeam;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class BasicPlayerManager implements PlayerManager {

  private final Map<UUID, PaintballTeam> players;
  private int initalSize;


  BasicPlayerManager() {
    this.players = new HashMap<>();
    this.initalSize = 0;
  }

  @Override
  public void remove(UUID playerId) {
    this.players.remove(playerId);
  }

  @Override
  public void addPlayer(UUID playerId, PaintballTeam team) {
    this.players.put(playerId, team);
    this.initalSize++;
  }

  @Override
  public boolean isAlive(UUID playerId) {
    return this.players.containsKey(playerId);
  }

  @Override
  public int getAllPlayers() {
    return this.initalSize;
  }

  @Override
  public boolean hasWinner() {
    return this.players.size() == 1;
  }

  @Override
  public Optional<PaintballPlayer> getWinner() {
    return Optional.empty();
  }

  @Override
  public Set<PaintballPlayer> getActivePlayers() {
    return null;
  }

  @Override
  public PaintballTeam getTeam(UUID playerId) {
    return this.players.get(playerId);
  }

  @Override
  public void purge() {

  }

  @Override
  public void save() {

  }
}
