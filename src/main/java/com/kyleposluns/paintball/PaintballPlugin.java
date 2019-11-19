package com.kyleposluns.paintball;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PaintballPlugin extends JavaPlugin implements Listener{

  public PaintballPlugin() {
    // empty constructor. This class' constructor is called by the server.
  }

  @Override
  public void onEnable() {
    System.out.println("This is a test.");
    this.getLogger().info("The plugin has successfully loaded.");
    this.getServer().getPluginManager().registerEvents(this, this);
  }

  @Override
  public void onDisable() {
    this.getLogger().info("The plugin has successfully unloaded.");
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Bukkit.broadcastMessage("Welcome the server");
  }
  
  @EventHandler
  public void onMove(PlayerMoveEvent event) {
    Bukkit.broadcastMessage("you moved");
  }
  
}
