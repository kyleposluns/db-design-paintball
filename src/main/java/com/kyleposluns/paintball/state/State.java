package com.kyleposluns.paintball.state;

import com.kyleposluns.paintball.PaintballPlugin;
import java.util.Set;
import java.util.UUID;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public abstract class State implements Listener, Runnable {

  private static final long TICKS_PER_SECOND = 20L;

  private static final long TICKS_PER_MINUTE = 20L * 60L;

  protected Set<UUID> players;

  private long counter;

  public State(Set<UUID> players) {
    this.players = players;
    this.counter = 0L;
  }

  public abstract boolean isFinished();

  public abstract State nextState();

  public void onEnter(PaintballPlugin plugin) {
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  public void onExit(PaintballPlugin plugin) {
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
