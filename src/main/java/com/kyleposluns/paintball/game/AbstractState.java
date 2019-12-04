package com.kyleposluns.paintball.game;

import com.kyleposluns.paintball.PaintballPlugin;
import com.kyleposluns.paintball.player.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Provides the basic functionality for all implementations of State.
 */
public abstract class AbstractState implements State {

  static final long TICKS_PER_SECOND = 20L;

  static final long TICKS_PER_MINUTE = 20L * 60L;

  PaintballPlugin plugin;

  PlayerManager players;

  private long ticks;

  private long seconds;

  private boolean stop;

  AbstractState(PaintballPlugin plugin, PlayerManager players) {
    this.plugin = plugin;
    this.players = players;
    this.stop = false;
    this.seconds = 0L;
    this.ticks = 0L;
  }

  long counter() {
    return this.ticks;
  }

  long seconds() {
    return this.seconds;
  }

  @Override
  public void onEnter() {
    this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
  }

  @Override
  public void onExit() {
    HandlerList.unregisterAll(this);
  }

  /**
   * Convenience method that is called every tick.
   */
  protected void eachTick() {

  }

  /**
   * Convenience method that is called every second.
   */
  protected void eachSecond() {

  }

  /**
   * Convenience method that is called every minute.
   */
  protected void eachMinute() {

  }

  @Override
  public void abort() {
    this.stop = true;
    HandlerList.unregisterAll(this);
  }

  @Override
  public final void run() {
    if (!this.stop) {
      eachTick();
      if (this.ticks % TICKS_PER_SECOND == 0) {
        this.seconds = this.seconds + 1;
        this.eachSecond();
      }

      if (this.ticks % TICKS_PER_MINUTE == 0) {
        this.eachMinute();
      }
      this.ticks = this.ticks + 1;
    }
  }


  @EventHandler
  public void onChat(AsyncPlayerChatEvent event) {

    event.setFormat(players.getTeam(event.getPlayer().getUniqueId()).getChatColor() + event.getPlayer().getName() + ": " + ChatColor.GRAY + event.getMessage());
  }

  @EventHandler
  public void onPlayerLeave(PlayerQuitEvent event) {
    this.players.remove(event.getPlayer().getUniqueId());
  }

  @EventHandler
  public void onPlayerGetHungry(FoodLevelChangeEvent event) {
    event.setFoodLevel(20);
  }

  @EventHandler
  public void onEntityExplode(EntityExplodeEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onLeavesDecay(LeavesDecayEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    if (!event.getPlayer().isOp()) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onBlockSpread(BlockSpreadEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (!event.getPlayer().isOp()) {
      event.setCancelled(true);
    }

  }

  @EventHandler
  public void onBlockBurn(BlockBurnEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onBlockDamage(BlockDamageEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onBlockExplode(BlockExplodeEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onBlockFade(BlockFadeEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onBlockFertilize(BlockFertilizeEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onBlockForm(BlockFormEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onBlockGrow(BlockGrowEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onBlockIgnite(BlockIgniteEvent event) {
    event.setCancelled(true);
  }

}
