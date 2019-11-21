package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.arena.Arena;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class WaveImpl implements Wave {

  private final int numberOfPlayers;

  private final int roundNumber;

  private final int monstersToSpawn;

  private final double monsterDamage;

  private final double monsterHealth;

  private final double speed;

  private final List<EntityType> availableEntities;

  private final Set<UUID> spawnedEntities;

  WaveImpl(List<EntityType> availableEntities, int roundNumber, int numberOfPlayers,
      double monsterDamage) {
    this.numberOfPlayers = numberOfPlayers;
    this.roundNumber = roundNumber;
    this.monstersToSpawn = (roundNumber >= 10
        ? ((3 * this.roundNumber) / 20) + 24 + (this.numberOfPlayers * 6)
        : 5 + this.numberOfPlayers + (this.roundNumber * 2));
    this.speed = Math.max(2, 1 + (this.roundNumber * .05));
    this.monsterHealth = 20 + this.roundNumber * 5;
    this.availableEntities = availableEntities;
    this.monsterDamage = monsterDamage;
    this.spawnedEntities = new HashSet<>();
  }

  private void spawnEntity(Location location) {
    if (location.getWorld() != null) {
      LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location,
          this.availableEntities.get((int) (Math.random() * this.availableEntities.size())));
      Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED))
          .setBaseValue(this.speed);
      entity.setHealth(this.monsterHealth);
      spawnedEntities.add(entity.getUniqueId());
    }
  }

  @Override
  public int getWaveNumber() {
    return this.roundNumber;
  }

  // spawns zombies at all arena spawn sites
  @Override
  public void onStart(Arena arena) {
    World world = Bukkit.getWorld(arena.getWorldId());
    if (world != null) {
      for (int i = 0; i < this.monstersToSpawn; i++) {
        Location spawnLoc = arena.getSpawns().get((int) (Math.random() * arena.getSpawns().size()));
        this.spawnEntity(spawnLoc);
      }
    }
  }

  @Override
  public void onFinish(Arena arena) {

  }

  @Override
  public void kill(UUID entityId) {
    this.spawnedEntities.remove(entityId);
  }

  @Override
  public boolean isMonsterTracked(UUID entityId) {
    return this.spawnedEntities.contains(entityId);
  }

  @Override
  public double getPaintballDamage() {
    return 20;
  }

  @Override
  public double getMonsterDamage() {
    return this.monsterDamage;
  }

  @Override
  public boolean isWaveOver() {
    return this.spawnedEntities.size() == 0;
  }

  @Override
  public Wave nextWave() {
    return new WaveImpl(this.availableEntities, this.roundNumber + 1, this.numberOfPlayers,
        this.monsterDamage);
  }

}
