package com.kyleposluns.paintball;

import com.kyleposluns.paintball.arena.ArenaManager;
import com.kyleposluns.paintball.sql.Initializer;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PaintballPlugin extends JavaPlugin {

  /**
   * The name of the database we are testing with (this default is installed with MySQL)
   */
  private final String dbName = "lotrfinalKolczynskiS";
  private String userName;
  private String password;
  private String serverName;
  private int portNumber;
  private Scanner s;
  private File customConfigFile;
  private FileConfiguration customConfig;

  public PaintballPlugin() {
    // empty constructor. This class' constructor is called by the server.
    this.createCustomConfig();
  }

  @Override
  public void onEnable() {
    Initializer init = new Initializer();
    try {
      System.out.println("This is a test.");
      this.getLogger().info("The plugin has successfully loaded.");
      Connection conn = init.getConnection(this.customConfigFile);
      init.setupDatabase();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }



  @Override
  public void onDisable() {
    this.getLogger().info("The plugin has successfully unloaded.");
  }


  private void createCustomConfig() {
    customConfigFile = new File(getDataFolder(), "custom.yml");
    if (!customConfigFile.exists()) {
      customConfigFile.getParentFile().mkdirs();
      saveResource("custom.yml", false);
    }

    customConfig = new YamlConfiguration();
    try {
      customConfig.load(customConfigFile);
    } catch (IOException | InvalidConfigurationException e) {
      e.printStackTrace();
    }
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
