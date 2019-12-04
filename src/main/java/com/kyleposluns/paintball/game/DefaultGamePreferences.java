package com.kyleposluns.paintball.game;

import java.util.List;
import org.bukkit.entity.EntityType;

public enum DefaultGamePreferences implements GamePreferences {



  EASY(new Wave.Builder(1)
      .entities(List
          .of(EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.PIG_ZOMBIE))
      .monstersPerRound((round, players) -> players)
      .monsterSpeed(0.5)
      .monsterHealth(20L)
      .build(), 20, 5, 20),

  MEDIUM(new Wave.Builder(1)
      .entities(List
          .of(EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.PIG_ZOMBIE,
              EntityType.SPIDER, EntityType.CAVE_SPIDER))
      .monstersPerRound((round, players) -> round * players)
      .monsterSpeed((round) -> Math.max(2, 1 + (round * .05)))
      .monsterHealth((round) -> Math.max(100.0, 20 + (round * 10.0)))
      .build(), 20, 10, 30),

  HARD(new Wave.Builder(1)
      .entities(List
          .of(EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.PIG_ZOMBIE,
              EntityType.SPIDER, EntityType.CAVE_SPIDER, EntityType.SKELETON))
      .monstersPerRound((round, players) -> (round >= 10
          ? ((3 * round) / 20) + 24 + (players * 6)
          : 5 + players + (round * 2)))
      .monsterHealth((round) -> (20 + (round * 5.0)))
      .monsterSpeed((round) -> Math.max(3.0, 1 + (round * .10)))
      .build(), 20, 10, 40);


  private final Wave initialWave;

  private final double paintballDamage;

  private final double monsterDamage;

  private final double initialPlayerHealth;

  DefaultGamePreferences(Wave initialWave, double paintballDamage, double monsterDamage,
      double initialPlayerHealth) {
    this.initialWave = initialWave;
    this.paintballDamage = paintballDamage;
    this.monsterDamage = monsterDamage;
    this.initialPlayerHealth = initialPlayerHealth;
  }

  @Override
  public Wave getInitialWave() {
    return this.initialWave;
  }

  @Override
  public long getTimeToUndoBlockPaint() {
    return 40L;
  }

  @Override
  public double getInitialPlayerHealth() {
    return this.initialPlayerHealth;
  }

  @Override
  public double getPaintballDamage() {
    return this.paintballDamage;
  }

  @Override
  public double getMonsterDamage() {
    return this.monsterDamage;
  }
}
