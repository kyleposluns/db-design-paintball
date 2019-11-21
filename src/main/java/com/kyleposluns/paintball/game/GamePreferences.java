package com.kyleposluns.paintball.game;

public interface GamePreferences {

  Wave getInitialWave();

  long getTimeToUndoBlockPaint();

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
