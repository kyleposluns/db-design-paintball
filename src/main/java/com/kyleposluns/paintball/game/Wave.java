package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.arena.Arena;
import java.util.UUID;
import org.bukkit.Location;

/**
 * Describes the behavior of the game.
 */
public interface Wave {

  /**
   * Spawn an entity at a given location with starting health described by this wave.
   * @param location
   */
  void spawnEntity(Location location);

  /**
   * Get the number of waves that have been completed, and include this wave.
   * @return The amount of waves before, plus this wave.
   */
  int getWaveNumber();

  /**
   * Called when the wave is started.
   * @param arena The current arena.
   */
  void onStart(Arena arena);

  /**
   * Called when the wave has been completed.
   * @param arena The current arena.
   */
  void onFinish(Arena arena);

  /**
   * Kill a particular entity and log its death.
   * @param entityId The id of the entity.
   * @param killerId The id of the killer.
   */
  void kill(UUID entityId, UUID killerId);

  /**
   * Determines if the provided entity has anything to do with the current wave.
   * @param entityId The id of the entity that is being checked.
   * @return True if this wave has spawned the provided entity.
   */
  boolean isMonsterTracked(UUID entityId);

  /**
   * Get the amount of health each monster should spawn with.
   * @return The amount of health each monster should spawn with.
   */
  double getMonsterHealth();

  /**
   * Get the amount of damage each paintball should inflict on the monster for each hit.
   * @return The amount of damage paintballs cause.
   */
  double getPaintballDamage();

  /**
   * Get the amount of damage each monster should inflict on players for each hit.
   * @return The amount of damage monsters cause.
   */
  double getMonsterDamage();

  /**
   * Determines if this wave has reached its end conditions.
   * @return True if the wave is over.
   */
  boolean isWaveOver();

  /**
   * Kill all of the monsters spawned by this wave.
   */
  void purgeMonsters();

  /**
   * The next wave, with a higher difficulty.
   * @return A more difficult wave.
   */
  Wave nextWave();
}

