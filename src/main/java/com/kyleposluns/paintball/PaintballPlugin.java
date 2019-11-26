package com.kyleposluns.paintball;

import com.kyleposluns.paintball.arena.Arena;
import com.kyleposluns.paintball.arena.ArenaImpl;
import com.kyleposluns.paintball.arena.ArenaManager;
import com.kyleposluns.paintball.arena.ArenaManagerImpl;
import com.kyleposluns.paintball.arena.CuboidRegion;
import com.kyleposluns.paintball.arena.Region;
import com.kyleposluns.paintball.game.PaintballGame;
import com.kyleposluns.paintball.sql.Initializer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PaintballPlugin extends JavaPlugin {

  /**
   * The name of the database we are testing with (this default is installed with MySQL)
   */

  private FileConfiguration config;

  private PaintballGame game;

  private Connection connection;

  @Override
  public void onEnable() {
    this.saveDefaultConfig();
    this.config = this.getConfig();
    Initializer init = new Initializer(this.config);
    this.connection = init.getConnection();
    try {
      System.out.println("This is a test.");
      this.getLogger().info("The plugin has successfully loaded.");

      init.setupDatabase();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    World world = Bukkit.getServer().getWorlds().get(0);

    world.setDifficulty(Difficulty.HARD);
    world.setMonsterSpawnLimit(1000);
    world.setTicksPerMonsterSpawns(1);

    this.game = new PaintballGame(this, this.connection);
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

  private ArenaManager getArenas() throws SQLException {
    Map<Integer, Location> Locations = new HashMap<>();
    Map<String, Region> Regions = new HashMap<>();
    ArrayList<Arena> arenas = new ArrayList<>();
    Statement stmt = this.connection.createStatement();
    ResultSet loc = stmt.executeQuery("SELECT * FROM Location;");
    while (loc.next()) {
      int id = loc.getInt("LocID");
      int x = loc.getInt("x");
      int y = loc.getInt("y");
      int z = loc.getInt("z");
      World w = Bukkit.getWorld(UUID.fromString(loc.getString("World")));
      Location newL = new Location(w, x, y, z);
      Locations.put(id, newL);
    }
    ResultSet reg = stmt.executeQuery("SELECT * FROM Region;");
    while (reg.next()) {
      String regName = reg.getString("Reg");
      int min = reg.getInt("Location1");
      int max = reg.getInt("Location2");
      Region region = new CuboidRegion(Locations.get(min), Locations.get(max));
      Regions.put(regName, region);
    }
    ResultSet are = stmt.executeQuery("SELECT * FROM Arena;");
    while (are.next()) {
      String name = are.getString("name");
      UUID id = UUID.fromString((are.getString("UUID")));

      Region r = Regions.get(are.getString("Region"));

      World w = r.findRandomLocation().getWorld();

      arenas.add(new ArenaImpl(name, id, w.getUID(), r, 5));
    }
    return new ArenaManagerImpl(arenas);

  }

  public int getPregameCountdown() {
    return 15;
  }

  public int getRequiredPlayers() {
    return 2;
  }

  // TODO: pass an arena manager that has read the correct values from the database.
  public ArenaManager getArenaManager() {
    try {
      return this.getArenas();
    } catch (SQLException sql) {
      sql.printStackTrace();
      throw new IllegalArgumentException("Arena Manager could not be set up correctly");
    }
  }

  public Connection getConnection() {
    return this.connection;
  }


  // TODO: pass in the server respawn location (maybe get from config)
  public Location getRespawnLocation() {
    return new Location(this.getServer().getWorlds().get(0), 100, 100, 100);
  }

}
