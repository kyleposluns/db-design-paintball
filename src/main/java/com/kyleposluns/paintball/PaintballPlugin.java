package com.kyleposluns.paintball;

import com.kyleposluns.paintball.arena.ArenaManager;
import com.kyleposluns.paintball.arena.ArenaManagerImpl;
import com.kyleposluns.paintball.game.PaintballGame;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
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

  private FileConfiguration config = this.getConfig();

  private PaintballGame game;

  @Override
  public void onEnable() {
    /*
    Initializer init = new Initializer();
    try {
      System.out.println("This is a test.");
      this.getLogger().info("The plugin has successfully loaded.");
      //Connection conn = init.getConnection(config);
      init.setupDatabase();
    } catch (SQLException e) {
      e.printStackTrace();
    }*/
    this.game = new PaintballGame(this);
    this.game.start();
  }


  @Override
  public void onDisable() {
    this.getLogger().info("The plugin has successfully unloaded.");

    if (this.game == null) {
      System.out.println("NULL");
    }

    this.game.abort();
  }



  public int getPregameCountdown() {
    return 60;
  }

  public int getRequiredPlayers() {
    return 1;
  }

  // TODO: pass an arena manager that has read the correct values from the database.
  public ArenaManager getArenaManager() {
    return new ArenaManagerImpl(new ArrayList<>());
  }


  // TODO: pass in the server respawn location (maybe get from config)
  public Location getRespawnLocation() {
    return new Location(this.getServer().getWorlds().get(0), 100, 100, 100);
  }

}
