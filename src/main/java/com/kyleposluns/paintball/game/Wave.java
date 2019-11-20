package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.arena.Arena;
import java.util.UUID;
import org.bukkit.Location;

public interface Wave {

  void spawnEntity(Location location);

  int getWaveNumber();

  void onStart(Arena arena);

  void onFinish(Arena arena);

  void kill(UUID entityId);

  boolean isMonsterTracked(UUID entityId);

  double getEntityHealth();

  double getPaintballDamage();

  double getMonsterDamage();

  boolean isWaveOver();

  void purgeMonsters();

  Wave nextWave();

}
