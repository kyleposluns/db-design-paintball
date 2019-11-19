package com.kyleposluns.paintball;

import org.bukkit.plugin.java.JavaPlugin;

public class PaintballPlugin extends JavaPlugin {

  public PaintballPlugin() {
    // empty constructor. This class' constructor is called by the server.
  }

  @Override
  public void onEnable() {
    System.out.println("This is a test.");
    this.getLogger().info("The plugin has successfully loaded.");
  }

  @Override
  public void onDisable() {
    this.getLogger().info("The plugin has successfully unloaded.");
  }

}
