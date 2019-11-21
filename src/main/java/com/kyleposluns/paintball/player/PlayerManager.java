package com.kyleposluns.paintball.player;

import com.kyleposluns.paintball.team.PaintballTeam;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PlayerManager {

  void remove(UUID playerId);

  void addPlayer(UUID playerId, PaintballTeam team);

  boolean isAlive(UUID playerId);

  int getAllPlayers();

  boolean hasWinner();

  Optional<PaintballPlayer> getWinner();

  Set<PaintballPlayer> getActivePlayers();

  PaintballTeam getTeam(UUID playerId);

  void purge();

  void save();


}
