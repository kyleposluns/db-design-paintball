package com.kyleposluns.paintball;

import com.kyleposluns.paintball.arena.ArenaManager;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class PaintballPlugin extends JavaPlugin {

  public PaintballPlugin() {
    // empty constructor. This class' constructor is called by the server.
  }

  @Override
  public void onEnable() {
    this.getLogger().info("The plugin has successfully loaded.");
  }

  @Override
  public void onDisable() {
    this.getLogger().info("The plugin has successfully unloaded.");
  }

  public int getPregameCountdown() {
    return 0;
  }

  public int getRequiredPlayers() {
    return 0;
  }

  public ArenaManager getArenaManager() {
    return null;
  }


  public Location getRespawnLocation() {
    return null;
  }

}
