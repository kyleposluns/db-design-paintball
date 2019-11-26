package com.kyleposluns.paintball;

import com.kyleposluns.paintball.arena.ArenaManager;
import com.kyleposluns.paintball.arena.ArenaManagerImpl;
import com.kyleposluns.paintball.game.PaintballGame;
import java.awt.SystemColor;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
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
    World world = Bukkit.getServer().getWorlds().get(0);

    world.setDifficulty(Difficulty.HARD);
    world.setMonsterSpawnLimit(1000);
    world.setTicksPerMonsterSpawns(1);

    this.game = new PaintballGame(this);
    this.game.start();
    this.getLogger().info("The plugin has successfully loaded.");
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
    return 15;
  }

  public int getRequiredPlayers() {
    return 2;
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
