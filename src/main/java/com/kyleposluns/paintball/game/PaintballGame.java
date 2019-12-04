package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.command.DifficultyCommand;
import com.kyleposluns.paintball.command.TeamCommand;
import com.kyleposluns.paintball.command.VoteCommand;
import com.kyleposluns.paintball.player.PlayerManager;
import com.kyleposluns.paintball.player.PlayerManagerImpl;
import java.sql.Connection;

public class PaintballGame implements Runnable {

  private PaintballPlugin plugin;

  private int id;

  private long counter;

  private State state;

  private String difficulty;

  public PaintballGame(PaintballPlugin plugin) {
    this(plugin, null);
  }

  public PaintballGame(PaintballPlugin plugin, Connection conn) {
    this.plugin = plugin;
    PlayerManager playerManager;
    if (conn == null) {
      playerManager = new PlayerManagerImpl();
    } else {
      playerManager = new PlayerManagerImpl(conn);
    }
    this.difficulty = "MEDIUM";

    this.counter = 0L;
    this.state = new PregameState(this.plugin, new DBPreferences(conn, difficulty), playerManager);
    this.plugin.getCommand("vote").setExecutor(new VoteCommand(this.state));
    this.plugin.getCommand("pbdifficulty").setExecutor(new DifficultyCommand(this.state));
    this.plugin.getCommand("team").setExecutor(new TeamCommand(this.state));
  }

  public void start() {
    this.id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 1L);
    this.state.onEnter();
  }

  public void abort() {
    this.state.abort();
    stop();
  }

  public void stop() {
    plugin.getServer().getScheduler().cancelTask(this.id);
  }

  @Override
  public void run() {
    this.state.run();

    if (this.state.isFinished() && this.counter % 20 == 0) {
      System.out.println("FINISHED");
      this.state.onExit();
      this.state = this.state.nextState();
      this.state.onEnter();
    }
    this.counter = this.counter + 1;

  }
}
