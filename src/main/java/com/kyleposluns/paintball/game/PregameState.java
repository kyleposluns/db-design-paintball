package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.arena.Arena;
import com.kyleposluns.paintball.arena.ArenaManager;
import com.kyleposluns.paintball.player.PlayerManager;
import com.kyleposluns.paintball.sql.AddPlayer;
import com.kyleposluns.paintball.team.PaintballTeam;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PregameState extends AbstractState {

  private final int countdown;

  private final int requiredPlayers;

  private final VotingManager votingManager;

  private final ArenaManager arenaManager;

  private Arena arenaWithMostVotes;

  private GamePreferences gamePreferences;

  PregameState(PaintballPlugin plugin, GamePreferences preferences, PlayerManager players) {
    super(plugin, players);
    this.requiredPlayers = this.plugin.getRequiredPlayers();
    this.arenaManager = this.plugin.getArenaManager();
    this.countdown = this.plugin.getPregameCountdown();
    this.votingManager = new VotingManager(this.arenaManager);
    this.gamePreferences = preferences;
    this.arenaWithMostVotes = null;
  }

  @Override
  public void onEnter() {
    super.onEnter();

    for (Player player : Bukkit.getOnlinePlayers()) {
      if (!this.players.isInGame(player.getUniqueId())) {
        this.players.addPlayer(player.getUniqueId(), this.randomTeam());
      }
    }

  }

  @Override
  public void onExit() {
    super.onExit();
    this.votingManager.lockVotes();
    Bukkit.broadcastMessage(ChatColor.AQUA + "Game starting!");
    this.arenaWithMostVotes = this.arenaManager.getArena(this.votingManager.getWinner());
    World world = Bukkit.getWorld(this.arenaWithMostVotes.getWorldId());
    if (world != null) {
      for (Entity entity : world.getEntities()) {
        if (entity instanceof Monster) {
          Monster monster = (Monster) entity;
          monster.setHealth(0.0);
        }
      }
    }
  }

  @Override
  public void eachSecond() {
    long secondsLeft = this.countdown - (this.seconds() % this.countdown);

    if (secondsLeft != 0 && (secondsLeft % 15 == 0
        || secondsLeft <= 5)) {
      Bukkit.broadcastMessage(ChatColor.GREEN + String
          .format("%s seconds remaining until the game is starting!",
              secondsLeft));
    }

    if (secondsLeft == 1 && !isFinished()) {
      Bukkit.broadcastMessage(ChatColor.RED + "Not enough players to start the game! Restarting counter.");
    }
  }

  public void difficulty(GamePreferences preferences) {
    this.gamePreferences = preferences;
  }

  public void addToTeam(UUID playerId, PaintballTeam team) {
    this.players.addPlayer(playerId, team);
  }

  public boolean vote(UUID playerId, String arenaName) {
    UUID arenaId = this.arenaManager.getArenaId(arenaName);
    if (arenaId == null) {
      return false;
    }

    votingManager.vote(playerId, arenaId);
    return true;
  }


  @Override
  public boolean isFinished() {
    long secondsLeft = this.countdown - (this.seconds() % this.countdown);
    return secondsLeft == 1
        && this.players.getActivePlayers().size() >= this.requiredPlayers;
  }

  @Override
  public AbstractState nextState() {
    return new GameLogicState(this.plugin, this.players, this.arenaWithMostVotes,
        this.gamePreferences);
  }


  @Override
  public <R> R accept(StateVisitor<R> visitor) {
    return visitor.visitPregameState(this);
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    UUID id = event.getPlayer().getUniqueId();
    this.players.addPlayer(id, this.randomTeam());
  }

  private PaintballTeam randomTeam() {
    return new PaintballTeam.Builder().build();
  }

}
