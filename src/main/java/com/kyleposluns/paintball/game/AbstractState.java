package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.player.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerQuitEvent;

public abstract class AbstractState implements State {

  static final long TICKS_PER_SECOND = 20L;

  static final long TICKS_PER_MINUTE = 20L * 60L;

  PaintballPlugin plugin;

  PlayerManager players;

  private long counter;

  AbstractState(PaintballPlugin plugin, PlayerManager players) {
    this.plugin = plugin;
    this.players = players;
    this.counter = 0L;
  }

  long counter() {
    return this.counter;
  }

  @Override
  public void onEnter() {
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }

  @Override
  public void onExit() {
    HandlerList.unregisterAll(this);
  }

  void eachTick() {

  }

  void eachSecond() {

  }

  void eachMinute() {

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
