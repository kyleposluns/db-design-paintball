package com.kyleposluns.paintball;

import com.kyleposluns.paintball.arena.Arena;
import com.kyleposluns.paintball.arena.ArenaImpl;
import com.kyleposluns.paintball.arena.ArenaManager;
import com.kyleposluns.paintball.arena.ArenaManagerImpl;
import com.kyleposluns.paintball.arena.CuboidRegion;
import com.kyleposluns.paintball.arena.Region;
import com.kyleposluns.paintball.game.PaintballGame;
import com.kyleposluns.paintball.sql.Initializer;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PaintballPlugin extends JavaPlugin {

  /**
   * The name of the database we are testing with (this default is installed with MySQL)
   */

  private FileConfiguration config = this.getConfig();

  private PaintballGame game;

  private Connection conn;

  @Override
  public void onEnable() {
    Initializer init = new Initializer();
    try {
      System.out.println("This is a test.");
      this.getLogger().info("The plugin has successfully loaded.");
      this.conn = init.getConnection(config);
      init.setupDatabase();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    this.game = new PaintballGame(this, this.conn);
    this.game.start();
  }


  @Override
  public void onDisable() {
    this.getLogger().info("The plugin has successfully unloaded.");
    this.game.abort();
  }

  private ArenaManager getArenas() throws SQLException {
    Map<Integer, Location> Locations = new HashMap<>();
    Map<String, CuboidRegion> Regions = new HashMap<>();
    ArrayList<Location> spawns = new ArrayList<>();
    ArrayList<Arena> arenas = new ArrayList<Arena>();
    Statement stmt = this.conn.createStatement();
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
      CuboidRegion region = new CuboidRegion(Locations.get(min), Locations.get(max));
      Regions.put(regName, region);
    }
    ResultSet are = stmt.executeQuery("SELECT * FROM Arena;");
    while (are.next()) {
      String name = are.getString("name");
      UUID id = UUID.fromString((are.getString("UUID")));
      CuboidRegion r = Regions.get(are.getString("Reg"));
      int minX = r.min.getBlockX();
      int minZ = r.min.getBlockZ();
      int maxX = r.max.getBlockX();
      int maxZ =  r.max.getBlockZ();
      World w = r.min.getWorld();
      int y =  r.min.getBlockY();
      Random rand = new Random();
      for (int i = 0; i < ((maxX - minX) * (maxZ - minZ)) / 50; i++) {
        spawns.add(new Location(w, rand.nextInt((maxX - minX) + minX), y,
                rand.nextInt((maxZ - minZ) + minZ)));
      }
      arenas.add(new ArenaImpl(name, id, w.getUID(), r, spawns));
    }
    return new ArenaManagerImpl(arenas);

  }



  public int getPregameCountdown() {
    return 60;
  }

  public int getRequiredPlayers() {
    return 1;
  }

  // TODO: pass an arena manager that has read the correct values from the database.
  public ArenaManager getArenaManager() throws SQLException {
    return this.getArenas();
  }


  // TODO: pass in the server respawn location (maybe get from config)
  public Location getRespawnLocation() {
    return new Location(this.getServer().getWorlds().get(0), 100, 100, 100);
  }

}
