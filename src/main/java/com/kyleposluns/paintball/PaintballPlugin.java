package com.kyleposluns.paintball;

<<<<<<< HEAD
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
=======
import com.kyleposluns.paintball.arena.ArenaManager;
import org.bukkit.Location;
>>>>>>> ec5c054061ad9a205b3b8bf40e5a01a047bc4f78
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class PaintballPlugin extends JavaPlugin {

  private String userName;

  private String password;

  private String serverName;

  private int portNumber;

  private Scanner s;

  /** The name of the database we are testing with (this default is installed with MySQL) */
  private final String dbName = "lotrfinalKolczynskiS";

  private File customConfigFile;
  private FileConfiguration customConfig;

  public PaintballPlugin() {
    // empty constructor. This class' constructor is called by the server.
    this.createCustomConfig();
  }

  @Override
  public void onEnable() {
    this.getLogger().info("The plugin has successfully loaded.");
    Connection conn = this.getConnection();
    try {
      this.initTables(conn);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onDisable() {
    this.getLogger().info("The plugin has successfully unloaded.");
  }

<<<<<<< HEAD

  private Connection getConnection() {
    Connection conn = null;
    try {
      Scanner s = new Scanner(this.customConfigFile);
      Properties connectionProps = new Properties();
      connectionProps.load(new FileReader(customConfigFile));
      connectionProps.put("user", connectionProps.getProperty("username"));
      connectionProps.put("password", connectionProps.getProperty("password"));
      conn = DriverManager.getConnection("jdbc:mysql://"
                      + connectionProps.getProperty("serverName")  + ":"
                      + connectionProps.getProperty("port") + "/"
                      + connectionProps.getProperty("dbName")
                      + "?characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true",
              connectionProps);
    } catch (SQLException | FileNotFoundException sql) {
      System.out.println("Incorrect username and/or password. Try again.");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return conn;
  }

  private void createCustomConfig() {
    customConfigFile = new File(getDataFolder(), "custom.yml");
    if (!customConfigFile.exists()) {
      customConfigFile.getParentFile().mkdirs();
      saveResource("custom.yml", false);
    }

    customConfig= new YamlConfiguration();
    try {
      customConfig.load(customConfigFile);
    } catch (IOException | InvalidConfigurationException e) {
      e.printStackTrace();
    }
  }

  private void initTables(Connection conn) throws SQLException {
    Statement beingTable;
    Statement playerTable;

    beingTable = conn.createStatement();
=======
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
>>>>>>> ec5c054061ad9a205b3b8bf40e5a01a047bc4f78
  }

}
