package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.arena.Arena;
import com.kyleposluns.paintball.arena.ArenaManager;
import com.kyleposluns.paintball.player.PlayerManager;
import com.kyleposluns.paintball.team.PaintballTeam;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PregameState extends AbstractState {

  private final int countdown;

  private final int requiredPlayers;

  private final VotingManager votingManager;

  private final ArenaManager arenaManager;

  private Arena arenaWithMostVotes;

  private GamePreferences gamePreferences;

  PregameState(PaintballPlugin plugin, PlayerManager players) {
    super(plugin, players);
    this.requiredPlayers = this.plugin.getRequiredPlayers();
    this.arenaManager = this.plugin.getArenaManager();
    this.countdown = this.plugin.getPregameCountdown();
    this.votingManager = new VotingManager(this.arenaManager);
    this.gamePreferences = DefaultGamePreferences.EASY;
    this.arenaWithMostVotes = null;
  }

  @Override
  public void onExit() {
    super.onExit();
    this.votingManager.lockVotes();
    Bukkit.broadcastMessage(ChatColor.AQUA + "Game starting!");
    this.arenaWithMostVotes = this.arenaManager.getArena(this.votingManager.getWinner());
  }

  @Override
  public void eachSecond() {
    if (this.counter() % (15 * TICKS_PER_SECOND) == 0
        || (this.counter() % (this.countdown * TICKS_PER_SECOND)) <= 5) {
      Bukkit.broadcastMessage(ChatColor.GREEN + String
          .format("%s seconds remaining until the game is starting!",
              this.counter() % this.countdown));
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
    return this.counter() % (this.countdown * TICKS_PER_SECOND) == 0
        && this.players.getAllPlayers() == this.requiredPlayers;
  }

  @Override
  public AbstractState nextState() {
    return new GameLogicState(this.plugin, this.players, this.arenaWithMostVotes, this.gamePreferences);
  }

  @Override
  public <R> R accept(StateVisitor<R> visitor) {
    return visitor.visitPregameState(this);
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    this.players.addPlayer(event.getPlayer().getUniqueId(), this.randomTeam());
  }

  private PaintballTeam randomTeam() {
    return new PaintballTeam.Builder().build();
  }

}
