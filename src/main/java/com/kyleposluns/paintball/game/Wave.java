package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.arena.Arena;
import java.util.UUID;

/**
 * Describes the behavior of the game.
 */
public interface Wave {

  /**
   * Get the number of waves that have been completed, and include this wave.
   *
   * @return The amount of waves before, plus this wave.
   */
  int getWaveNumber();

  /**
   * Called when the wave is started.
   *
   * @param arena The current arena.
   */
  void onStart(Arena arena);

  /**
   * Called when the wave has been completed.
   *
   * @param arena The current arena.
   */
  void onFinish(Arena arena);

  /**
   * Kill a particular entity and log its death.
   *
   * @param entityId The id of the entity.
   */
  void kill(UUID entityId);

  /**
   * Determines if the provided entity has anything to do with the current wave.
   *
   * @param entityId The id of the entity that is being checked.
   * @return True if this wave has spawned the provided entity.
   */
  boolean isMonsterTracked(UUID entityId);

  /**
   * Get the amount of damage each paintball should inflict on the monster for each hit.
   *
   * @return The amount of damage paintballs cause.
   */
  double getPaintballDamage();

  /**
   * Get the amount of damage each monster should inflict on players for each hit.
   *
   * @return The amount of damage monsters cause.
   */
  double getMonsterDamage();

  /**
   * Determines if this wave has reached its end conditions.
   *
   * @return True if the wave is over.
   */
  boolean isWaveOver();
  /**
   * The next wave, with a higher difficulty.
   *
   * @return A more difficult wave.
   */
  Wave nextWave();
}

