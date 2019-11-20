package com.kyleposluns.paintball.game;

import java.util.Optional;
import java.util.UUID;
import org.bukkit.entity.EntityType;

public interface ProjectileTracker {

  void logShot(UUID shooter, UUID projectile);

  void logHit(UUID shooter, UUID projectile, EntityType target);

  Optional<UUID> getShooter(UUID projectile);

  boolean isTracked(UUID projectile);

  void save();



}
