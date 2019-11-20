package com.kyleposluns.paintball.game;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.kyleposluns.paintball.arena.Arena;

public class SimpleWave implements Wave{
  private final int roundNumber;
  
  SimpleWave(int roundNumber) {
    this.roundNumber = roundNumber;
  }
  
  // unsure why this exists - there is already spawnEntity method in World type
  @Override
  public void spawnEntity(Location location) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public int getWaveNumber() {
    return roundNumber;
  }

  // spawns zombies at all arena spawn sites
  @Override
  public void onStart(Arena arena) {
    for (int i = 0; i < 5 * this.roundNumber; i++) {
      Location spawnLoc = arena.getSpawns().get(i % arena.getSpawns().size());
      // need worldID to replace empty string
      Bukkit.getWorld("").spawnEntity(spawnLoc, EntityType.ZOMBIE);
    }
    
  }

  @Override
  public void onFinish(Arena arena) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void kill(UUID entityId) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public double getEntityHealth() {
    // these numbers subject to change
    return 10 + 2 * roundNumber;
  }

  @Override
  public double getPaintballDamage() {
    // paintball damage grows at slower rate than enemy health
    return 5 + roundNumber;
  }

  @Override
  public double getMonsterDamage() {
    // monster damage grows at same rate as paintball damage
    return 5 + roundNumber;
  }

  @Override
  public boolean isWaveOver() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Wave nextWave() {
    return new SimpleWave(this.roundNumber + 1);
  }
  
}
