package com.kyleposluns.paintball.sql;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.Properties;

public class Initializer {

  private Connection conn;

  public Connection getConnection(FileConfiguration customConfigFile) {
    Connection conn = null;
      customConfigFile.getString("username");
      Properties connectionProps = new Properties();
      connectionProps.put("user", customConfigFile.getString("username"));
      connectionProps.put("password", customConfigFile.getString("password"));
    try {
      conn = DriverManager.getConnection("jdbc:mysql://"
                      + customConfigFile.getString("serverName") + ":"
                      + customConfigFile.getString("port") + "/"
                      + customConfigFile.getString("dbName")
                      + "?characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true",
              connectionProps);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    this.conn = conn;
      System.out.println("Incorrect username and/or password. Try again.");
    return conn;
  }

  public void setupDatabase() throws SQLException {
    Statement use;
    use = conn.createStatement();
    try {
      use.execute("USE PaintballGame");
    } catch (SQLSyntaxErrorException e) {
      //The database needs to be set up on this device.
      this.initDatabase();
      use.execute("USE PaintballGame");
      this.initTables();
      this.initProcedures();
    }
  }

  private void initDatabase() throws SQLException {
    Statement beginning;
    beginning = conn.createStatement();
    beginning.execute("DROP DATABASE IF EXISTS PaintballGame");
    beginning.execute("CREATE DATABASE PaintballGame");
  }

  private void initTables() throws SQLException {
    Statement stmt;
    stmt = conn.createStatement();
    stmt.execute("CREATE TABLE IF NOT EXISTS Player (\n" +
            "\tplayerID CHAR(36) NOT NULL,\n" +
            "    name VARCHAR(45) NOT NULL,\n" +
            "    killsOverall INT NOT NULL,\n" +
            "    bestWave INT,\n" +
            "    PRIMARY KEY (playerID));");
    stmt.execute("CREATE TABLE IF NOT EXISTS Arena (\n" +
            "\tname VARCHAR(45) NOT NULL,\n" +
            "    UUID CHAR(36) NOT NULL PRIMARY KEY,\n" +
            "    Region INT NOT NULL,\n" +
            "    constraint fk_region_of_arena\n" +
            "    FOREIGN KEY (Region)\n" +
            "    REFERENCES Region\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE);");

  }

  private void initProcedures() throws SQLException {
    Statement stmt;
    stmt = conn.createStatement();

  }

}
