package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.arena.Arena;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Spider;

public class WaveImpl implements Wave {

  private BiFunction<Integer, Integer, Integer> monsters;

  private Function<Integer, Double> monsterHealth;

  private Function<Integer, Double> monsterSpeed;

  private List<EntityType> availableEntities;

  private Set<UUID> spawnedEntities;

  private int round;

  private boolean started;

  WaveImpl(List<EntityType> entities, int round,
      BiFunction<Integer, Integer, Integer> monsters,
      Function<Integer, Double> monsterHealth, Function<Integer, Double> monsterSpeed) {
    this.availableEntities = entities;
    this.round = round;
    this.monsters = monsters;
    this.monsterHealth = monsterHealth;
    this.monsterSpeed = monsterSpeed;
    this.spawnedEntities = new HashSet<>();
  }

  private void spawnEntity(Location location) {
    if (location.getWorld() != null) {
      LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location,
          this.availableEntities.get((int) (Math.random() * this.availableEntities.size())));
      System.out.println(entity.getLocation().toString());
      Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED))
          .setBaseValue(this.monsterSpeed.apply(this.round));

      if (entity instanceof PigZombie) {
        PigZombie pigZombie = (PigZombie) entity;
        pigZombie.setAngry(true);
      }

      entity.setHealth(this.monsterHealth.apply(this.round));
      spawnedEntities.add(entity.getUniqueId());
    }
  }

  @Override
  public int getWaveNumber() {
    return this.round;
  }

  @Override
  public int getMonstersLeft() {
    return this.spawnedEntities.size();
  }

  // spawns zombies at all arena spawn sites
  @Override
  public void spawnMonsters(PaintballPlugin plugin, Arena arena, int players) {

    int monsters = this.monsters.apply(this.round, players);
    for (int i = 0; i < monsters; i++) {
      final int count = i;
      Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
        Location spawnLoc = arena.getSpawns().get((int) (Math.random() * arena.getSpawns().size()));
        WaveImpl.this.spawnEntity(spawnLoc);
        started = count + 1 == monsters;
      }, 5L);
    }

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
  public boolean isWaveOver() {
    return this.spawnedEntities.size() == 0 && this.started;
  }

  @Override
  public Wave nextWave() {
    return new WaveImpl(this.availableEntities, this.round + 1, this.monsters,
        this.monsterHealth, this.monsterSpeed);
  }


}
