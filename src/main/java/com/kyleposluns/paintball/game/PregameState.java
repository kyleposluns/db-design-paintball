package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.arena.Arena;
import com.kyleposluns.paintball.arena.ArenaManager;
import com.kyleposluns.paintball.team.PaintballTeam;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PregameState extends State {

  private final int countdown;

  private final int requiredPlayers;

  private final VotingManager votingManager;

  private final ArenaManager arenaManager;

  private Arena arenaWithMostVotes;

  public PregameState(PaintballPlugin plugin, ArenaManager arenaManager, int requiredPlayers, int countdown) {
    super(plugin, new ConcurrentHashMap<>());
    this.arenaManager = arenaManager;
    this.requiredPlayers = requiredPlayers;
    this.countdown = countdown;
    this.votingManager = new VotingManager(arenaManager);
    this.arenaWithMostVotes = null;
  }

  @Override
  public void onExit() {
    super.onExit();
    this.votingManager.lockVotes();
    Bukkit.broadcastMessage(ChatColor.AQUA + "Game starting!");
    this.arenaWithMostVotes = this.arenaManager.loadArena(this.votingManager.getWinner());
  }

  @Override
  public void eachSecond() {
    if (this.counter() % (15 * TICKS_PER_SECOND) == 0 || (this.counter() % (this.countdown * TICKS_PER_SECOND)) <= 5) {
      Bukkit.broadcastMessage(ChatColor.GREEN + String
          .format("%s seconds remaining until the game is starting!",
              this.counter() % this.countdown));
    }
  }

  public void addToTeam(UUID playerId, PaintballTeam team) {
    this.players.put(playerId, team);
  }

  public void vote(UUID playerId, String arenaName) {
    votingManager.vote(playerId, arenaManager.getArenaId(arenaName));
  }

  @Override
  <R> R accept(StateVisitor visitor) {
    return visitor.visitPregameState(this);
  }

  @Override
  public boolean isFinished() {
    return this.counter() % this.countdown == 0 && this.players.size() == this.requiredPlayers;
  }

  @Override
  public State nextState() {
    return new GameLogicState(this.plugin, this.players, this.arenaWithMostVotes);
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    this.players.put(event.getPlayer().getUniqueId(), this.randomTeam());
  }

  private PaintballTeam randomTeam() {
    return new PaintballTeam.Builder().build();
  }

}