package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.command.DifficultyCommand;
import com.kyleposluns.paintball.command.VoteCommand;
import com.kyleposluns.paintball.player.PlayerManager;
import com.kyleposluns.paintball.player.PlayerManagerImpl;

public class PaintballGame implements Runnable {

  private PaintballPlugin plugin;

  private int id;

  private State state;

  public PaintballGame(PaintballPlugin plugin) {
    this.plugin = plugin;
    PlayerManager playerManager = new PlayerManagerImpl();
    this.state = new PregameState(this.plugin, playerManager);
    this.plugin.getCommand("vote").setExecutor(new VoteCommand(this.state));
    this.plugin.getCommand("pbdifficulty").setExecutor(new DifficultyCommand(this.state));
  }

  public void start() {
    this.id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 1L);
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
    if (this.state.isFinished()) {
      this.state.onEnter();
      this.state = this.state.nextState();
      this.state.onExit();
    }

  }
}
