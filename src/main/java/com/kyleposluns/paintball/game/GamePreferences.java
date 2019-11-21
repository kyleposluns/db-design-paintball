package com.kyleposluns.paintball.game;

/**
 * Specifies the kind of logic we want games to have.
 */
public interface GamePreferences {

  /**
   * Provides the initial wave and describes what all future waves will look like.
   * @return The initial wave of the game.
   */
  Wave getInitialWave();

  /**
   * Specifies the amount of ticks it takes for blocks to return to their original state.
   * @return The amount of time in ticks.
   */
  long getTimeToUndoBlockPaint();

  /**
   * Get the amount of health that players should start out with when they spawn in.
   * @return The amount of player health.
   */
  double getInitialPlayerHealth();

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


}
