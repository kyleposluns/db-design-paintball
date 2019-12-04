package com.kyleposluns.paintball.game;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.EntityType;

/**
 * Records all interactions that players and monsters have with paint-balls.
 */
public interface KillHandler {

  /**
   * Logs that a player's weapon has been fired. Called every time a player shoots their gun.
   * Records the id of the shooter and the id of the paintball.
   *
   * @param shooter    The id shooter shooting the paintball.
   * @param projectile The id of the projectile that is being shot.
   */
  void logShot(UUID shooter, UUID projectile);


  /**
   * Safely finds the id of the shooter of a projectile. If it does not exist the optional will be
   * Optional.empty() if the projectile is not being tracked by this tracker.
   *
   * @param projectile The id of the projectile.
   * @return An object that either contains the id of the shooter or null.
   */
  Optional<UUID> getShooter(UUID projectile);


  /**
   * Logs that a player's paintball has hit a monster. Called every time a paintball has hit a monster.
   * @param shooter The id of the shooter shooting the paintball.
   * @param entityType The type of entity that has been killed.
   */
  void logKill(UUID shooter, EntityType entityType);

  /**
   * Determines if this projectile tracker is tracking the id of the projectile that is being
   * tracked.
   *
   * @param projectile The id of the projectile
   * @return True if this projectile tracker is tracking the provided projectile.
   */
  boolean isTracked(UUID projectile);

  /**
   * Backup the contents of this tracker.
   * @param Wave the wave this game got to.
   */
  void save(int Wave, Set<UUID> players);


  /**
   * Gets the amount of kills the player has.
   * @param player The player.
   * @return The amount of kills.
   */
  int getKills(UUID player);

}
