package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.arena.Arena;
import com.kyleposluns.paintball.player.PlayerManager;
import com.kyleposluns.paintball.sql.AddToKillTable;
import com.kyleposluns.paintball.team.PaintballTeam;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class GameLogicState extends AbstractState {

  private static final ItemStack PAINTBALL_GUN = new ItemStack(Material.DIAMOND_HOE, 1);

  private KillHandler projectileTracker;

  private Arena arena;

  private PaintballPlugin plugin;

  private Wave currentWave;

  private GamePreferences preferences;

  private Map<UUID, Long> coolDowns;

  private boolean waitPeriod;


  GameLogicState(PaintballPlugin plugin, PlayerManager players, Arena arena,
      GamePreferences preferences) {
    super(plugin, players);
    this.plugin = plugin;
    this.arena = arena;
    this.projectileTracker = new ProjectileTrackImpl(plugin.getConnection());
    this.preferences = preferences;
    this.coolDowns = new HashMap<>();
    this.currentWave = this.preferences.getInitialWave();
    this.waitPeriod = false;
  }

  private void coverInColoredArmor(Player player) {
    Color color = this.players.getTeam(player.getUniqueId()).getColor();

    ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);

    if (helmet.getItemMeta() != null) {
      LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
      meta.setColor(color);
      helmet.setItemMeta(meta);
    }

    ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);

    if (chestplate.getItemMeta() != null) {
      LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
      meta.setColor(color);
      chestplate.setItemMeta(meta);
    }

    ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);

    if (leggings.getItemMeta() != null) {
      LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
      meta.setColor(color);
      leggings.setItemMeta(meta);
    }

    ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
    if (boots.getItemMeta() != null) {
      LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
      meta.setColor(color);
      boots.setItemMeta(meta);
    }


    player.getInventory().setHelmet(helmet);
    player.getInventory().setChestplate(chestplate);
    player.getInventory().setLeggings(leggings);
    player.getInventory().setBoots(boots);


  }

  @Override
  public void onEnter() {
    super.onEnter();
    List<Location> spawns = this.arena.getSpawns();

    for (UUID paintballPlayer : this.players.getActivePlayers()) {
      Location loc = spawns.get((int) (Math.random() * spawns.size()));
      Player player = Bukkit.getPlayer(paintballPlayer);

      if (player == null) {
        continue;
      }
      player.setGameMode(GameMode.SURVIVAL);
      player.teleport(loc);
      player.setHealth(this.preferences.getInitialPlayerHealth());
      coverInColoredArmor(player);
      ItemStack stack = PAINTBALL_GUN.clone();
      ItemMeta meta = stack.getItemMeta();
      if (meta != null) {
        meta.setDisplayName("Paintball Gun");
        stack.setItemMeta(meta);
      }

      player.getInventory().setItemInMainHand(stack);
    }
    this.currentWave.spawnMonsters(this.plugin, this.arena, this.players.getAllPlayers());
    Bukkit.broadcastMessage(ChatColor.RED + "Good luck...");
    Bukkit.broadcastMessage(ChatColor.DARK_RED + "Round is beginning!");
  }

  @Override
  public void onExit() {
    super.onExit();
    this.projectileTracker.save(this.currentWave.getWaveNumber(), Bukkit.getOnlinePlayers().stream().map(
        Entity::getUniqueId).collect(
        Collectors.toSet()));
  }

  public KillHandler getKillHandler() {
    return this.projectileTracker;
  }

  @Override
  public void eachTick() {
    if (this.currentWave.isWaveOver() && !this.waitPeriod && !this.isFinished()) {
      Bukkit.broadcastMessage(ChatColor.GREEN + "Next round begins in 5 seconds!");
      Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
        if (!GameLogicState.this.isFinished()) {
          this.currentWave = this.currentWave.nextWave();
          this.currentWave
              .spawnMonsters(this.plugin, this.arena, this.players.getActivePlayers().size());
          Bukkit.broadcastMessage(ChatColor.DARK_RED + "Round is beginning!");
          waitPeriod = false;
        }
      }, 100L);
      this.waitPeriod = true;
    }
  }

  @Override
  public void eachSecond() {
    for (UUID playerId : this.players.getActivePlayers()) {
      Player player = Bukkit.getPlayer(playerId);
      if (player != null) {
        player.setLevel(this.currentWave.getMonstersLeft());
      }
    }
  }


  @Override
  public <R> R accept(StateVisitor<R> visitor) {
    return visitor.visitGameLogicState(this);
  }

  @Override
  public boolean isFinished() {
    return this.players.hasWinner() || this.players.getAllPlayers() == 0;
  }

  @Override
  public AbstractState nextState() {
    return new PostgameState(this.plugin, this.players, this.players.getWinner().orElse(null));
  }

  @EventHandler
  public void onCombust(EntityCombustEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    this.players.remove(event.getEntity().getUniqueId());
    event.setDeathMessage(event.getEntity().getName() + ChatColor.RED + " has fallen!");
  }

  @EventHandler
  public void onRespawn(PlayerRespawnEvent event) {
    event.setRespawnLocation(this.plugin.getRespawnLocation());
  }

  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {

    event.getDrops().clear();
    if (this.currentWave.isMonsterTracked(event.getEntity().getUniqueId())
        && event.getEntity().getKiller() != null) {

      AddToKillTable adkt = new AddToKillTable(this.plugin.getConnection(),
            this.currentWave.getWaveNumber(),
              event.getEntity().getKiller().getUniqueId(), event.getEntity().getType().toString());
      try {
        adkt.run();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      this.currentWave
          .kill(event.getEntity().getUniqueId());

      this.projectileTracker
          .logKill(event.getEntity().getKiller().getUniqueId(), event.getEntityType());
    }
  }

  @EventHandler
  public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
    event.setCancelled(true);
  }


  @EventHandler
  public void onEntityDamage(EntityDamageByEntityEvent event) {

    if (event.getEntity() instanceof Player && event.getDamager() instanceof Player
        || event.getEntity() instanceof Player && event.getDamager() instanceof Snowball) {
      event.setCancelled(true);
      return;
    }

    if (event.getDamager() instanceof Snowball && event.getEntity() instanceof Monster) {

      if (this.projectileTracker.isTracked(event.getDamager().getUniqueId())) {
        event.setDamage(this.preferences.getPaintballDamage());
        return;
      }
    }

    if (event.getEntity() instanceof Player && event.getDamager() instanceof Monster) {
      event.setDamage(this.preferences.getMonsterDamage());
    }

  }


  @EventHandler
  public void onFireWeapon(PlayerInteractEvent event) {
    if ((event.getAction() == Action.RIGHT_CLICK_AIR
        || event.getAction() == Action.RIGHT_CLICK_BLOCK)
        && event.getMaterial() == PAINTBALL_GUN.getType()) {
      UUID player = event.getPlayer().getUniqueId();

      if (System.currentTimeMillis() - this.coolDowns.getOrDefault(player, 0L) > 200) {
        Snowball snowball = event.getPlayer().launchProjectile(Snowball.class,
            event.getPlayer().getEyeLocation().getDirection().multiply(3));

        this.projectileTracker.logShot(event.getPlayer().getUniqueId(), snowball.getUniqueId());
        this.coolDowns.put(player, System.currentTimeMillis());

      }

    }
  }

  @EventHandler
  public void onSnowballHit(ProjectileHitEvent event) {
    Optional<UUID> shooter = this.projectileTracker.getShooter(event.getEntity().getUniqueId());
    shooter.ifPresent(shooterId -> {
      PaintballTeam team = this.players.getTeam(shooter.get());

      if (event.getHitBlock() != null && event.getHitBlock().getType() != Material.AIR) {
        BlockState targetBlock = event.getHitBlock().getState();

        event.getHitBlock().setType(team.getBlock());

        this.plugin.getServer().getScheduler()
            .scheduleSyncDelayedTask(this.plugin, () -> targetBlock.update(true, false),
                this.preferences.getTimeToUndoBlockPaint());
      }
    });
  }

  @EventHandler
  public void onLogin(PlayerLoginEvent event) {
    event.disallow(Result.KICK_OTHER, "Game in Progress.");
  }
}
