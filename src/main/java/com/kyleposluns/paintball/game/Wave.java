package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.arena.Arena;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

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
   * Get the amount of monsters that are still alive.
   */
  int getMonstersLeft();

  /**
   * Called when the wave is started, spawns all of the monsters for the wave.
   *
   * @param plugin The plugin.
   * @param arena The current arena.
   * @param players The amount of players in the game.
   */
  void spawnMonsters(PaintballPlugin plugin, Arena arena, int players);

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

  class Builder {

    private List<EntityType> entityTypes;

    private BiFunction<Integer, Integer, Integer> getMonstersToSpawn;

    private Function<Integer, Double> getMonsterSpeed;

    private Function<Integer, Double> getMonsterHealth;

    private int round;

    public Builder(int round) {
      this.round = round;
      this.entityTypes = null;
      this.getMonsterSpeed = null;
      this.getMonsterHealth = null;
      this.getMonstersToSpawn = null;
    }


    public Builder entities(List<EntityType> entityTypes) {
      this.entityTypes = entityTypes;
      return this;
    }

    public Builder round(int round) {
      this.round = round;
      return this;
    }

    public Builder monstersPerRound(int monsters) {
      this.getMonstersToSpawn = (round, players) -> monsters;
      return this;
    }

    public Builder monsterSpeed(double speed) {
      this.getMonsterSpeed = round -> speed;
      return this;
    }

    public Builder monsterHealth(double health) {
      this.getMonsterHealth = round -> health;
      return this;
    }

    public Builder monstersPerRound(BiFunction<Integer, Integer, Integer> function) {
      this.getMonstersToSpawn = function;
      return this;
    }

    public Builder monsterSpeed(Function<Integer, Double> function) {
      this.getMonsterSpeed = function;
      return this;
    }

    public Builder monsterHealth(Function<Integer, Double> function) {
      this.getMonsterHealth = function;
      return this;
    }

    public Wave build() {
      if (this.entityTypes == null || this.getMonstersToSpawn == null
          || this.getMonsterHealth == null
          || this.getMonsterSpeed == null) {
        throw new IllegalArgumentException("cannot create wave with null entity types");
      }

      return new WaveImpl(this.entityTypes, this.round, this.getMonstersToSpawn,
          this.getMonsterHealth, this.getMonsterSpeed);
    }


  }
}

