package com.kyleposluns.paintball.player;

import com.kyleposluns.paintball.team.PaintballTeam;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Handles all of the players in the game.
 */
public interface PlayerManager {

  /**
   * Remove the provided player from the game.
   * @param playerId the id of the player that is to be removed.
   */
  void remove(UUID playerId);

  /**
   * Adds the player to the game with a specified paintball team.
   * @param playerId The id of the player we're tracking.
   * @param team The team that the player has chosen.
   */
  void addPlayer(UUID playerId, PaintballTeam team);

  /**
   * Determines if a given player is still in the game.
   * @param playerId The id of the player.
   * @return True if the player has not died yet.
   */
  boolean isAlive(UUID playerId);

  /**
   * Get all of the players that have ever joined the current game.
   *
   * Differs from getActivePlayers.size()
   * @return The amount of players that have joined this game.
   */
  int getAllPlayers();

  /**
   * Determines if there is a winner of the game.
   * @return True if there is a winner.
   */
  boolean hasWinner();

  /**
   * Find the winner of the game if they exist.
   * @return the winner of the game.
   */
  Optional<UUID> getWinner();

  /**
   * Get all of the active players in the game.
   * @return The active player's id's.
   */
  Set<UUID> getActivePlayers();

  /**
   * Get the team of a player given their id.
   * @param playerId The id of the player we're searching for.
   * @return The team of the player.
   */
  PaintballTeam getTeam(UUID playerId);


}
