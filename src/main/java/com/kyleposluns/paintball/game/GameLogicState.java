package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.arena.Arena;
import com.kyleposluns.paintball.team.PaintballTeam;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GameLogicState extends State {

  private static final ItemStack PAINTBALL_GUN = new ItemStack(Material.DIAMOND_HOE, 1);

  private ProjectileTracker projectileTracker;

  private Arena arena;

  private PaintballPlugin plugin;

  private Wave currentWave;

  public GameLogicState(PaintballPlugin plugin, Map<UUID, PaintballTeam> players, Arena arena) {
    super(plugin, players);
    this.plugin = plugin;
    this.arena = arena;
    this.projectileTracker = null;
    this.currentWave = null;
  }

  @Override
  public void onEnter() {
    super.onEnter();
    List<Location> spawns = this.arena.getSpawns();

    for (UUID playerId : this.players.keySet()) {
      Location loc = spawns.get((int) (Math.random() * spawns.size()));
      Player player = Bukkit.getPlayer(playerId);
      if (player == null) {
        continue;
      }

      player.teleport(loc);
      player.getInventory().setItemInMainHand(PAINTBALL_GUN);
    }
    this.currentWave.onStart(this.arena);
  }

  @Override
  public void eachTick() {
    if (this.currentWave.isWaveOver()) {
      this.currentWave.onFinish(this.arena);
      this.currentWave = this.currentWave.nextWave();
    }
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {


  }

  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    if (this.projectileTracker.isTracked(event.getEntity().getUniqueId())) {
      this.currentWave.kill(event.getEntity().getUniqueId());
    }
  }


  @EventHandler
  public void onEntityDamage(EntityDamageByEntityEvent event) {

    if (event.getDamager() instanceof Snowball && event.getEntity() instanceof Monster) {

      if (this.projectileTracker.isTracked(event.getDamager().getUniqueId())) {
        event.setDamage(this.currentWave.getPaintballDamage());
        return;
      }
    }


    if (event.getEntity() instanceof Player && event.getDamager() instanceof Monster) {
      event.setDamage(this.currentWave.getMonsterDamage());
    }


  }



  @EventHandler
  public void onFireWeapon(PlayerInteractEvent event) {
    if ((event.getAction() == Action.RIGHT_CLICK_AIR
        || event.getAction() == Action.RIGHT_CLICK_BLOCK)
        && event.getMaterial() == PAINTBALL_GUN.getType()) {

      Snowball snowball = event.getPlayer().launchProjectile(Snowball.class,
          event.getPlayer().getEyeLocation().getDirection().multiply(3));

      this.projectileTracker.logShot(event.getPlayer().getUniqueId(), snowball.getUniqueId());
    }
  }

  @EventHandler
  public void onSnowballHit(ProjectileHitEvent event) {
    Optional<UUID> shooter = this.projectileTracker.getShooter(event.getEntity().getUniqueId());
    shooter.ifPresent(shooterId -> {
      PaintballTeam team = this.players.get(shooter.get());

      if (event.getHitEntity() != null) {
        this.projectileTracker.logHit(shooterId, event.getEntity().getUniqueId(),
            event.getHitEntity().getType());
      }

      if (event.getHitBlock() != null) {
        BlockState targetBlock = event.getHitBlock().getState();

        event.getHitBlock().setType(team.getBlock());

        this.plugin.getServer().getScheduler()
            .scheduleSyncDelayedTask(this.plugin, () -> targetBlock.update(true, false), 20L);
      }

    });
  }

  @Override
  <R> R accept(StateVisitor visitor) {
    return visitor.visitGameLogicState(this);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public State nextState() {
    return null;
  }
}
