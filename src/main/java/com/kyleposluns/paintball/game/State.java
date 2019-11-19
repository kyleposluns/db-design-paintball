package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.team.PaintballTeam;
import java.util.Map;
import java.util.UUID;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public abstract class State implements Listener, Runnable {

  static final long TICKS_PER_SECOND = 20L;

  static final long TICKS_PER_MINUTE = 20L * 60L;

  PaintballPlugin plugin;

  Map<UUID, PaintballTeam> players;

  private long counter;

  public State(PaintballPlugin plugin, Map<UUID, PaintballTeam> players) {
    this.plugin = plugin;
    this.players = players;
    this.counter = 0L;
  }

  abstract <R> R accept(StateVisitor visitor);

  public abstract boolean isFinished();

  public abstract State nextState();

  long counter() {
    return this.counter;
  }

  public void onEnter() {
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }

  public void onExit() {
    HandlerList.unregisterAll(this);
  }

  public void eachTick() {

  }

  public void eachSecond() {

  }

  public void eachMinute() {

  }

  @Override
  public void run() {
    eachTick();
    if (this.counter % TICKS_PER_SECOND == 0) {
      this.eachSecond();
    }

    if (this.counter % TICKS_PER_MINUTE == 0) {
      this.eachMinute();
    }
    this.counter = this.counter + 1;
  }

  @EventHandler
  public void onPlayerLeave(PlayerQuitEvent event) {
    this.players.remove(event.getPlayer().getUniqueId());
  }


}
